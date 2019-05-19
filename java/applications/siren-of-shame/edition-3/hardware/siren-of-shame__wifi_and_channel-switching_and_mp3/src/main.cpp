#include <Arduino.h>

#include "arduino-extension.h"

#include "data_alarm-level.h"

#include "setup_mcp.h"
#include "setup_channel_display.h"
#include "setup_channel_switch.h"
#include "setup_inout_serial.h"
#include "setup_input_wifi.h"
#include "setup_output_mp3-player.h"
#include "setup_output_traffic-light.h"

// ... constants

const String COMMAND_GET_CURRENT_ALARM_LEVEL = "GET_CURRENT_ALARM_LEVEL";

const String COMMAND_SET_ALARM_LEVEL_TO_RED = "SET_ALARM_LEVEL_TO RED";
const String COMMAND_SET_ALARM_LEVEL_TO_RED_EXPECTING_UPDATE = "SET_ALARM_LEVEL_TO RED_EXPECTING_UPDATE";
const String COMMAND_SET_ALARM_LEVEL_TO_YELLOW = "SET_ALARM_LEVEL_TO YELLOW";
const String COMMAND_SET_ALARM_LEVEL_TO_YELLOW_EXPECTING_UPDATE = "SET_ALARM_LEVEL_TO YELLOW_EXPECTING_UPDATE";
const String COMMAND_SET_ALARM_LEVEL_TO_GREENBLUE = "SET_ALARM_LEVEL_TO GREENBLUE";
const String COMMAND_SET_ALARM_LEVEL_TO_GREENBLUE_EXPECTING_UPDATE = "SET_ALARM_LEVEL_TO GREENBLUE_EXPECTING_UPDATE";

const String COMMAND_PING = "PING";
const String COMMAND_PING_RESPONSE_SUCCEEDED = "SUCCEEDED";

const String CONFIG_GREETING_MESSAGE = "Wellcome to the \"Siren Of Shame\"!";
const String CONFIG_COMMANDS_MESSAGE = "I understand following commands: '"
                                       + COMMAND_SET_ALARM_LEVEL_TO_RED + "', '"
                                       + COMMAND_SET_ALARM_LEVEL_TO_RED_EXPECTING_UPDATE + "', '"
                                       + COMMAND_SET_ALARM_LEVEL_TO_YELLOW + "', '"
                                       + COMMAND_SET_ALARM_LEVEL_TO_YELLOW_EXPECTING_UPDATE + "', '"
                                       + COMMAND_SET_ALARM_LEVEL_TO_GREENBLUE + "', '"
                                       + COMMAND_SET_ALARM_LEVEL_TO_GREENBLUE_EXPECTING_UPDATE + "', '"
                                       + COMMAND_GET_CURRENT_ALARM_LEVEL
                                       + "'.";

const int CONFIG_SERIAL_PORT_BAUD_RATE__9600 = 9600;
const int CONFIG_CHANNEL_LIGHTS_TAKT_DURATION_IN_MS__600 = 600;

enum LightEffectsState {

  LIGHT_EFFECTS_ON,
  LIGHT_EFFECTS_OFF,
};

enum SoundEffectsState {

  SOUND_EFFECTS_ON,
  SOUND_EFFECTS_OFF,
};

class AlarmLevelConfig {

  public:

    AlarmLevel _alarmLevel;
    String _alarmLevelName;

    LightEffectsState _lightEffectsState;

    long _rememberingTaktInMs;
    SoundEffectsState _rememberingSoundEffectsState;

    AlarmLevelConfig(
      AlarmLevel alarmLevel, 
      String alarmLevelName, 
      LightEffectsState lightEffectsState, 
      SoundEffectsState rememberingSoundEffectsState, 
      long rememberingTaktInSec
    );
};

AlarmLevelConfig::AlarmLevelConfig( 
  AlarmLevel alarmLevel, 
  String alarmLevelName, 
  LightEffectsState lightEffectsState, 
  SoundEffectsState rememberingSoundEffectsState, 
  long rememberingTaktInSec
) {

  _alarmLevel = alarmLevel;
  _alarmLevelName = alarmLevelName;

  _lightEffectsState = lightEffectsState;

  _rememberingTaktInMs = rememberingTaktInSec * 1000;
  _rememberingSoundEffectsState = rememberingSoundEffectsState;
};

// ...

AlarmLevelConfig alarmLevelConfigs[] = {

  AlarmLevelConfig(
    ALARM_LEVEL_RED, "RED", 
    LIGHT_EFFECTS_ON, SOUND_EFFECTS_ON, 120
  ),
  AlarmLevelConfig(
    ALARM_LEVEL_YELLOW, "YELLOW", 
    LIGHT_EFFECTS_ON, SOUND_EFFECTS_ON, 180
  ),
  AlarmLevelConfig(
    ALARM_LEVEL_GREENBLUE,  "GREENBLUE", 
    LIGHT_EFFECTS_OFF, SOUND_EFFECTS_ON, 300
  ),

  AlarmLevelConfig(
    ALARM_LEVEL_RED_EXPECTING_UPDATE,  "RED_EXPECTING_UPDATE", 
    LIGHT_EFFECTS_ON, SOUND_EFFECTS_OFF, 300
  ),
  AlarmLevelConfig(
    ALARM_LEVEL_YELLOW_EXPECTING_UPDATE, "YELLOW_EXPECTING_UPDATE", 
    LIGHT_EFFECTS_ON, SOUND_EFFECTS_OFF, 300
  ),
  AlarmLevelConfig(
    ALARM_LEVEL_GREENBLUE_EXPECTING_UPDATE, "GREENBLUE_EXPECTING_UPDATE", 
    LIGHT_EFFECTS_ON, SOUND_EFFECTS_OFF, 300
  ),
};

// ...

AlarmLevelConfig *currentAlarmLevelConfig = &(alarmLevelConfigs[ALARM_LEVEL_RED]); // RED, YELLOW, GREENBLUE

String lastCommand = "";

// ... business methods

void setup()
{

  // ... setup subsystems
  serialPort_Setup(CONFIG_SERIAL_PORT_BAUD_RATE__9600);
  
  mcp_Setup();

  channelDisplay_Setup();
  channelSwitch_Setup();

  inputWiFi_Setup();

  outputTrafficLight_Setup();

  outputMp3Player_Setup();

  // ... play wellcome melody
  outputMp3Player.playAnyMp3WithinFolder(31);

  // ... send wellcome message
  serialPort_Println(CONFIG_GREETING_MESSAGE);
  serialPort_Println("");

  serialPort_Println(CONFIG_COMMANDS_MESSAGE);
  serialPort_Println("");
}

void doSetState(AlarmLevel newAlarmLevel);
void verify_Condition_SoundEffects_And_Replay_IfNeeded();
void verify_Condition_LightEffects_And_Replay_IfNeeded();

void loop()
{
  uint channelSwitchValue = channelSwitch_GetCurrentValue();

  uint channelDisplayValue = channelDisplay_GetCurrentValue();
  if (channelDisplayValue != channelSwitchValue) {

    channelDisplay_DisplayValue(channelSwitchValue);
  }
  
  AlarmLevel newAlarmLevel = inputWiFi_GetAlarmLevel(channelSwitchValue);
  doSetState(newAlarmLevel);

  if (serialPort_IsAvailable()) {

    lastCommand = serialPort_ReadString();
    // Serial.println("Command received: '" + lastCommand + "'.");

    if (lastCommand == COMMAND_GET_CURRENT_ALARM_LEVEL) {
      Serial.println(currentAlarmLevelConfig->_alarmLevelName);
    } else if (lastCommand == COMMAND_PING) {
      Serial.println(COMMAND_PING_RESPONSE_SUCCEEDED);
    } else if (lastCommand == COMMAND_SET_ALARM_LEVEL_TO_RED) {
      doSetState(ALARM_LEVEL_RED);
    } else if (lastCommand == COMMAND_SET_ALARM_LEVEL_TO_YELLOW) {
      doSetState(ALARM_LEVEL_YELLOW);
    } else if (lastCommand == COMMAND_SET_ALARM_LEVEL_TO_GREENBLUE) {
      doSetState(ALARM_LEVEL_GREENBLUE);
    } else if (lastCommand == COMMAND_SET_ALARM_LEVEL_TO_RED_EXPECTING_UPDATE) {
      doSetState(ALARM_LEVEL_RED_EXPECTING_UPDATE);
    } else if (lastCommand == COMMAND_SET_ALARM_LEVEL_TO_YELLOW_EXPECTING_UPDATE) {
      doSetState(ALARM_LEVEL_YELLOW_EXPECTING_UPDATE);
    } else if (lastCommand == COMMAND_SET_ALARM_LEVEL_TO_GREENBLUE_EXPECTING_UPDATE) {
      doSetState(ALARM_LEVEL_GREENBLUE_EXPECTING_UPDATE);
    } else {
      // serialPort_Println("Command '" + lastCommand + "' is not supported.");
    }
  }

  inputWiFi_PlayStateUpdate();

  verify_Condition_SoundEffects_And_Replay_IfNeeded();
  verify_Condition_LightEffects_And_Replay_IfNeeded();

  delay(1);
}

void doSetState(AlarmLevel newAlarmLevel) {

  AlarmLevel oldAlarmLevel = currentAlarmLevelConfig->_alarmLevel;
  if (newAlarmLevel != oldAlarmLevel) {

    currentAlarmLevel_Remembering_Timer.reset();
    currentAlarmLevelConfig = &(alarmLevelConfigs[newAlarmLevel]);

    outputTrafficLight_OnChange_AlarmLevevel(
      oldAlarmLevel, 
      newAlarmLevel,
      currentAlarmLevelConfig->_lightEffectsState == LIGHT_EFFECTS_ON
    );

    outputMp3Player_OnChange_AlarmLevevel(
      oldAlarmLevel, 
      newAlarmLevel
    );

    // Serial.println("AlarmLevel changed to: " + currentAlarmLevelConfig->_alarmLevelName);
  } else {
    // Serial.println("AlarmLevel is still the following: " + currentAlarmLevelConfig->_alarmLevelName);
  }
}

void verify_Condition_LightEffects_And_Replay_IfNeeded() {

  if (currentAlarmLevelConfig->_lightEffectsState == LIGHT_EFFECTS_ON) {

    outputTrafficLight_PlayEffects();
  }
}

void verify_Condition_SoundEffects_And_Replay_IfNeeded() {
  
  if (currentAlarmLevelConfig->_rememberingSoundEffectsState == SOUND_EFFECTS_ON) {

    outputMp3Player_PlayEffects(currentAlarmLevelConfig->_alarmLevel, currentAlarmLevelConfig->_rememberingTaktInMs);
  }
}
