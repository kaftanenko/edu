#include <Arduino.h>
#include <Adafruit_MCP23017.h>

#include "arduino-extension.h"

#include "data_alarm-level.hpp"

#include "config/config_alarm-levels-control.hpp"
#include "config/config_channels-state-resource.hpp"
#include "config/config_wlan.hpp"

#include "setup_channel-number-display.hpp"
#include "setup_channel-state-exposer_embeded.hpp"
#include "setup_channel-state-exposer_external.hpp"
#include "setup_channel-state-exposer_mp3.hpp"
#include "setup_channel-state-observer_wifi.hpp"
#include "setup_channel_switch.hpp"
#include "setup_inout_serial.hpp"

// ... constants

const String CONFIG_WELLCOME_MESSAGE = "Wellcome to the \"Siren Of Shame\"!";

const int CONFIG_SERIAL_PORT_BAUD_RATE__9600 = 9600;

// ... configuration

AlarmLevelsConfig alarmLevelsConfig;
ChannelsConfig channelsConfig;
WlanConfig wlanConfig;

AlarmLevelConfig *getAlarmLevelConfigBy(AlarmLevel alarmLevel)
{
  for (int i = 0; i < 7; i++)
  {
    AlarmLevelConfig *alarmLevelConfig = &(alarmLevelsConfig.alarmLevels[i]);
    if (alarmLevel == alarmLevelConfig->alarmLevel)
    {
      return alarmLevelConfig;
    }
  }
}

AlarmLevelConfig *currentAlarmLevelConfig = getAlarmLevelConfigBy(ALARM_LEVEL_NONE);

// ... installed devices

Adafruit_MCP23017 mcp;

ChannelNumberDisplay channelNumberDisplay(&mcp, 2);

ChannelStateExposerEmbeded channelStateExposer_Embeded(&mcp);
ChannelStateExposerExternal channelStateExposer_External;
ChannelStateExposerMp3 channelStateExposer_Mp3;

ChannelsStateObserverWifi channelsStateWifiObserver;

// ... business methods

void setup()
{
  // ... setup serial port

  serialPort_Setup(CONFIG_SERIAL_PORT_BAUD_RATE__9600);
  serialPort_Println(CONFIG_WELLCOME_MESSAGE);

  // ... setup configuration

  loadAlarmLevelsConfig(alarmLevelsConfig);
  loadChannelsConfig(channelsConfig);
  loadWlanConfig(wlanConfig);

  // ... setup installed devices

  mcp.begin();

  channelNumberDisplay.setup();

  channelSwitch_Setup(           //
      channelsConfig.bounds.min, //
      channelsConfig.bounds.max, //
      channelsConfig.bounds.step //
  );

  channelStateExposer_Embeded.setup();
  channelStateExposer_External.setup();

  channelStateExposer_Mp3.setup();
  channelStateExposer_Mp3.playWellcomeMelody();

  channelsStateWifiObserver.setup(        //
      wlanConfig.ssid,                    //
      wlanConfig.password,                //
      channelsConfig.host,                //
      channelsConfig.path,                //
      channelsConfig.fingerprint,         //
      channelsConfig.headerAuthorization, //
      15                                  // sec.
  );
  channelsStateWifiObserver.tryToUpdateChannelsStateData();
}

void doChangeChannelNumberTo(uint8_t newChannelNumber);

void doSetChannelStateTo(AlarmLevel newAlarmLevel);
void doChangeChannelStateTo(AlarmLevel newAlarmLevel);

void doPlayChannelStateRememberingEffects();

void loop()
{
  channelsStateWifiObserver.playChannelsStateUpdate();

  const uint8_t updatedChannelNumber = channelSwitch_GetCurrentValue();
  doChangeChannelNumberTo(updatedChannelNumber);

  const AlarmLevel updatedAlarmLevel = channelsStateWifiObserver.getAlarmLevel(updatedChannelNumber);
  doChangeChannelStateTo(updatedAlarmLevel);

  doPlayChannelStateRememberingEffects();
}

// ... helper methods

void doChangeChannelNumberTo(const uint8_t newChannelNumber)
{
  uint8_t currentChannelNumber = channelNumberDisplay.getDisplayValue();
  if (newChannelNumber != currentChannelNumber)
  {
    Serial.printf("Channel number changed from '%d' to '%d'.\n",
                  currentChannelNumber, newChannelNumber);

    channelNumberDisplay.displayValue(newChannelNumber);

    const AlarmLevel updatedAlarmLevel = channelsStateWifiObserver.getAlarmLevel(newChannelNumber);
    doSetChannelStateTo(updatedAlarmLevel);
  }
}

void doSetChannelStateTo(const AlarmLevel newAlarmLevel)
{
  currentAlarmLevelConfig = getAlarmLevelConfigBy(newAlarmLevel);

  Serial.printf("AlarmLevel set to '%s'.\n",
                currentAlarmLevelConfig->alarmLevelName);

  channelStateExposer_Embeded.exposeAlarmLevel(
      newAlarmLevel,
      currentAlarmLevelConfig->lightEffectsTaktRateInMs);

  channelStateExposer_External.exposeAlarmLevel(
      newAlarmLevel,
      currentAlarmLevelConfig->lightEffectsTaktRateInMs);
}

void doChangeChannelStateTo(const AlarmLevel newAlarmLevel)
{
  const AlarmLevel currentAlarmLevel = currentAlarmLevelConfig->alarmLevel;
  if (newAlarmLevel != currentAlarmLevel)
  {
    currentAlarmLevelConfig = getAlarmLevelConfigBy(newAlarmLevel);

    Serial.printf("AlarmLevel changed from '%d' to '%s'.\n",
                  currentAlarmLevel,
                  currentAlarmLevelConfig->alarmLevelName);

    channelStateExposer_Embeded.exposeAlarmLevelChange(
        currentAlarmLevel,
        newAlarmLevel,
        currentAlarmLevelConfig->lightEffectsTaktRateInMs);

    channelStateExposer_External.exposeAlarmLevelChange(
        currentAlarmLevel,
        newAlarmLevel,
        currentAlarmLevelConfig->lightEffectsTaktRateInMs);

    channelStateExposer_Mp3.exposeAlarmLevelChange(
        currentAlarmLevel,
        newAlarmLevel,
        currentAlarmLevelConfig->soundEffectsTaktRateInSec);
  }
}

void doPlayChannelStateRememberingEffects()
{
  channelStateExposer_Embeded.playEffects();
  channelStateExposer_External.playEffects();
  channelStateExposer_Mp3.playEffects();
}
