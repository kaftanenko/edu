#include <Arduino.h>

#include "data_alarm-level.hpp"

#include "simple-timer.h"
#include "simple-wifi-client.h"

// ... configuration

class ChannelsStateObserverWifi
{
private:
  char *_host = nullptr;
  char *_path = nullptr;
  char *_fingerprint = nullptr;
  char *_headerAuthorization = nullptr;

  long _channelsStateUpdate_Takt_InMs = 0;

  SimpleWiFiClient _simpleWiFiClient;

  SimpleTimer _channelsStateUpdate_Timer;
  bool _channelsStateDataAvailable = false;

  StaticJsonDocument<1024> _channelsStateData;

public:
  void setup(                 //
      char *ssid,             //
      char *password,         //
      char *host,             //
      char *path,             //
      uint8_t updateTaktInSec //
  );
  void setup(                    //
      char *ssid,                //
      char *password,            //
      char *host,                //
      char *path,                //
      char *fingerprint,         //
      char *headerAuthorization, //
      uint8_t updateTaktInSec    //
  );

  AlarmLevel getAlarmLevel(const int channel);

  void playChannelsStateUpdate();
  void tryToUpdateChannelsStateData();

private:
  void _applyChannelsStateData(const String dataAsJsonString);
};

void ChannelsStateObserverWifi::setup( //
    char *ssid,                        //
    char *password,                    //
    char *host,                        //
    char *path,                        //
    uint8_t updateTaktInSec            //
)
{
  _simpleWiFiClient.begin(ssid, password);
  _simpleWiFiClient.connectWiFi();

  _host = host;
  _path = path;
  _channelsStateUpdate_Takt_InMs = updateTaktInSec * 1000;
}

void ChannelsStateObserverWifi::setup( //
    char *ssid,                        //
    char *password,                    //
    char *host,                        //
    char *path,                        //
    char *fingerprint,                 //
    char *headerAuthorization,         //
    uint8_t updateTaktInSec            //
)
{
  setup(
      ssid,           //
      password,       //
      host,           //
      path,           //
      updateTaktInSec //
  );

  _fingerprint = fingerprint;
  _headerAuthorization = headerAuthorization;
}

AlarmLevel ChannelsStateObserverWifi::getAlarmLevel(const int channel)
{
  if (_channelsStateDataAvailable)
  {
    const String channelAsString(channel);

    char alarmLevelName[64];
    strlcpy(alarmLevelName, _channelsStateData[channelAsString.c_str()], sizeof(alarmLevelName));

    return _getAlarmLevelByName(alarmLevelName);
  }
  else
  {
    return ALARM_LEVEL_NONE;
  }
}

void ChannelsStateObserverWifi::playChannelsStateUpdate()
{
  if (_channelsStateUpdate_Takt_InMs > 0 //
      && _channelsStateUpdate_Timer.isOver(_channelsStateUpdate_Takt_InMs))
  {
    tryToUpdateChannelsStateData();
    _channelsStateUpdate_Timer.reset();
  }
}

void ChannelsStateObserverWifi::tryToUpdateChannelsStateData()
{
  const String result =       //
      _fingerprint == nullptr || strlen(_fingerprint) == 0 //
          ? _simpleWiFiClient.get(_host, _path)
          : _simpleWiFiClient.gets(_host, _path, _fingerprint, _headerAuthorization);
  if (result.length() > 0)
  {
    _applyChannelsStateData(result);
  }
  else
  {
    Serial.println("Failed to retrieve channels state file.");
  }
}

// ... helper methods

void ChannelsStateObserverWifi::_applyChannelsStateData(const String dataAsJsonString)
{
  DeserializationError error = deserializeJson(_channelsStateData, dataAsJsonString);
  if (error)
  {
    Serial.println(F("Failed to parse channels state file."));
  }
  else
  {
    _channelsStateDataAvailable = true;
  }
}
