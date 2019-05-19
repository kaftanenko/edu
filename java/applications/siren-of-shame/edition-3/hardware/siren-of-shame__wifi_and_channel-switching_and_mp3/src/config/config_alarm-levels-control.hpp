#include <Arduino.h>
#include <ArduinoJson.h>

#include "data_alarm-level.hpp"

String DEFAULT_ALARM_LEVEL_CONFIG = R"(
[
	{
		"alarmLevelName": "NONE",
		"lightEffectsTaktRateInMs": 0,
		"soundEffectsTaktRateInSec": 0
	},
	{
		"alarmLevelName": "RED",
		"lightEffectsTaktRateInMs": 600,
		"soundEffectsTaktRateInSec": 120
	},
	{
		"alarmLevelName": "YELLOW",
		"lightEffectsTaktRateInMs": 600,
		"soundEffectsTaktRateInSec": 180
	},
	{
		"alarmLevelName": "GREENBLUE",
		"lightEffectsTaktRateInMs": 0,
		"soundEffectsTaktRateInSec": 300
	},
	{
		"alarmLevelName": "RED_EXPECTING_UPDATE",
		"lightEffectsTaktRateInMs": 600,
		"soundEffectsTaktRateInSec": 0
	},
	{
		"alarmLevelName": "YELLOW_EXPECTING_UPDATE",
		"lightEffectsTaktRateInMs": 600,
		"soundEffectsTaktRateInSec": 0
	},
	{
		"alarmLevelName": "GREENBLUE_EXPECTING_UPDATE",
		"lightEffectsTaktRateInMs": 600,
		"soundEffectsTaktRateInSec": 0
	}
])";

struct AlarmLevelConfig
{
	AlarmLevel alarmLevel;
	char alarmLevelName[32];
	uint16_t lightEffectsTaktRateInMs;  // 0 ~ OFF
	uint16_t soundEffectsTaktRateInSec; // 0 ~ OFF
};

struct AlarmLevelsConfig
{
	AlarmLevelConfig alarmLevels[7];
};

void _jsonString_InTo_AlarmLevelConfig(AlarmLevelsConfig &config, const String dataAsJsonString);
void _jsonArray_InTo_AlarmLevelConfig(AlarmLevelsConfig &config, const JsonArray alarmLevelsConfigData);
void _jsonObject_InTo_AlarmLevelConfig(AlarmLevelConfig &config, const JsonObject alarmLevelConfigData);

void loadAlarmLevelsConfig(AlarmLevelsConfig &alarmLevelsConfig)
{
	_jsonString_InTo_AlarmLevelConfig(alarmLevelsConfig, DEFAULT_ALARM_LEVEL_CONFIG);
}

// ... helper methods

void _jsonString_InTo_AlarmLevelConfig(AlarmLevelsConfig &config, const String dataAsJsonString)
{
	StaticJsonDocument<2048> configData;
	DeserializationError error = deserializeJson(configData, dataAsJsonString);
	if (error)
	{
		Serial.println(F("Failed to read config file."));
	}
	else
	{
		_jsonArray_InTo_AlarmLevelConfig(config, configData.as<JsonArray>());
	}
}

void _jsonArray_InTo_AlarmLevelConfig(AlarmLevelsConfig &config, const JsonArray configData)
{
	int i = 0;
	for (JsonVariant v : configData)
	{
		_jsonObject_InTo_AlarmLevelConfig(config.alarmLevels[i], v.as<JsonObject>());
		i++;
	}
}

void _jsonObject_InTo_AlarmLevelConfig(AlarmLevelConfig &config, const JsonObject configData)
{
	strncpy(config.alarmLevelName,
			configData["alarmLevelName"],
			sizeof(config.alarmLevelName));

	config.alarmLevel =
		_getAlarmLevelByName(config.alarmLevelName);

	config.lightEffectsTaktRateInMs =
		configData["lightEffectsTaktRateInMs"].as<uint16_t>();

	config.soundEffectsTaktRateInSec =
		configData["soundEffectsTaktRateInSec"].as<uint16_t>();
}
