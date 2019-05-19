#include "data_alarm-level.h"

#include "simple-timer.h"
#include "simple-wifi-client.h"

// ... configuration

const char *WiFiSSID = "<your_wifi_ssid>";
const char *WiFiPSK = "<your_wifi_password>>";

const char *server = "<your_resources_host>";
const char *resource = "<your_resource_path>";

const String MOCK_DATA = "{ '1': 'RED', '2': 'YELLOW', '3': 'GREENBLUE', '4': 'RED_EXPECTING_UPDATE', '5': 'YELLOW_EXPECTING_UPDATE', '6': 'GREENBLUE_EXPECTING_UPDATE', '7': 'RED', '8': 'YELLOW', '9': 'GREENBLUE', '10': 'RED_EXPECTING_UPDATE', '11': 'YELLOW_EXPECTING_UPDATE', '12': 'GREENBLUE_EXPECTING_UPDATE', '13': 'RED', '14': 'YELLOW', '15': 'GREENBLUE', '16': 'RED_EXPECTING_UPDATE', '17': 'YELLOW_EXPECTING_UPDATE', '18': 'GREENBLUE_EXPECTING_UPDATE', '19': 'RED', '20': 'YELLOW' }";

StaticJsonDocument<1024> channelsStateData;

SimpleTimer _stateUpdate_Timer;
long _stateUpdate_TickRate_InSec = 15 * 1000;

// ... business methods

SimpleWiFiClient simpleWiFiClient(WiFiSSID, WiFiPSK);

String inputWiFi_GetFile()
{

  return simpleWiFiClient.get(server, resource);
}

void _inputWiFi_MockChannelsStateData();
void _inputWiFi_SetChannelsStateData(const String dataAsJsonString);
void _inputWiFi_UpdateChannelsStateData();

void inputWiFi_Setup()
{

  simpleWiFiClient.setDebugMode(true);
  simpleWiFiClient.connectWiFi();

  //_inputWiFi_MockChannelsStateData();
  _inputWiFi_UpdateChannelsStateData();
}

void inputWiFi_PlayStateUpdate()
{

  if (_stateUpdate_Timer.isOver(_stateUpdate_TickRate_InSec))
  {
    _inputWiFi_UpdateChannelsStateData();
    _stateUpdate_Timer.reset();
  }
}

AlarmLevel inputWiFi_GetAlarmLevel(const int channel)
{

  const String channelAsString(channel);

  char alarmLevelAsString[64];
  strlcpy(alarmLevelAsString, channelsStateData[channelAsString.c_str()], sizeof(alarmLevelAsString));

  if (strcmp(alarmLevelAsString, "RED") == 0)
    return ALARM_LEVEL_RED;
  if (strcmp(alarmLevelAsString, "YELLOW") == 0)
    return ALARM_LEVEL_YELLOW;
  if (strcmp(alarmLevelAsString, "GREENBLUE") == 0)
    return ALARM_LEVEL_GREENBLUE;
  if (strcmp(alarmLevelAsString, "RED_EXPECTING_UPDATE") == 0)
    return ALARM_LEVEL_RED_EXPECTING_UPDATE;
  if (strcmp(alarmLevelAsString, "YELLOW_EXPECTING_UPDATE") == 0)
    return ALARM_LEVEL_YELLOW_EXPECTING_UPDATE;
  if (strcmp(alarmLevelAsString, "GREENBLUE_EXPECTING_UPDATE") == 0)
    return ALARM_LEVEL_GREENBLUE_EXPECTING_UPDATE;

  Serial.println("No value match for :" + String(alarmLevelAsString));

  return ALARM_LEVEL_RED;
}

// ...

void _inputWiFi_MockChannelsStateData()
{

  _inputWiFi_SetChannelsStateData(MOCK_DATA);
};

void _inputWiFi_UpdateChannelsStateData()
{

  const String result = inputWiFi_GetFile();
  _inputWiFi_SetChannelsStateData(result);
}

void _inputWiFi_SetChannelsStateData(const String dataAsJsonString)
{

  DeserializationError error = deserializeJson(channelsStateData, dataAsJsonString);
  if (error)
  {

    Serial.println(F("Failed to read file, using default configuration"));
  }
}
