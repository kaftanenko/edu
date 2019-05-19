#include <Arduino.h>
#include <ArduinoJson.h>

const String DEFAULT_WLAN_CONFIG = R"(
{
	"ssid": "...",
	"password": "..."
})";

struct WlanConfig
{
	char ssid[32];
	char password[64];
};

void _jsonString_InTo_WlanConfig(WlanConfig &wlanConfig, const String dataAsJsonString);
void _jsonObject_InTo_WlanConfig(WlanConfig &wlanConfig, const JsonObject wlanConfigData);

void loadWlanConfig(WlanConfig &wlanConfig)
{
	_jsonString_InTo_WlanConfig(wlanConfig, DEFAULT_WLAN_CONFIG);
}

// ... helper methods

void _jsonString_InTo_WlanConfig(WlanConfig &config, const String dataAsJsonString)
{

	StaticJsonDocument<1024> configData;
	DeserializationError error = deserializeJson(configData, dataAsJsonString);
	if (error)
	{
		Serial.println(F("Failed to read file, using default configuration"));
	}
	else
	{
		_jsonObject_InTo_WlanConfig(config, configData.as<JsonObject>());
	}
}

void _jsonObject_InTo_WlanConfig( //
	WlanConfig &config,			  //
	const JsonObject configData   //
)
{
	strncpy(config.ssid,
			configData["ssid"],
			sizeof(config.ssid));

	strncpy(config.password,
			configData["password"],
			sizeof(config.password));
}
