#include <Arduino.h>
#include <ArduinoJson.h>

const String DEFAULT_CHANNELS_CONFIG_LOCAL = R"(
{
	"host": "192.168.2.3",
	"path": "/jenkins/features.jsn",
	"fingerprint": "",
	"headerAuthorization": "",
	"bounds": {
		"min": 1,
		"max": 20,
		"step": 1
	}
})";

const String DEFAULT_CHANNELS_CONFIG = R"(
{
	"host": "wiki.impaq.de",
	"path": "/download/attachments/41779231/features_sample.jsn?api=current",
	"fingerprint": "04 F5 21 53 CB A0 08 7B 2C 8F 67 A2 68 2C E1 F2 5E 09 6F 63",
	"headerAuthorization": "Basic YWthZnRhbmVua286SmEldHV0MDRp",
	"bounds": {
		"min": 1,
		"max": 20,
		"step": 1
	}
})";

struct ChannelsConfig
{
	char host[64];
	char path[256];
	char fingerprint[64] = "";		   // optional
	char headerAuthorization[36] = ""; // optional
	struct
	{
		uint8_t min;
		uint8_t max;
		uint8_t step;
	} bounds;
};

void _jsonString_InTo_ChannelsConfig(ChannelsConfig &config, const String dataAsJsonString);
void _jsonObject_InTo_ChannelsConfig(ChannelsConfig &config, const JsonObject configData);

void loadChannelsConfig(ChannelsConfig &config)
{
	_jsonString_InTo_ChannelsConfig(config, DEFAULT_CHANNELS_CONFIG);
}

// ... helper methods

void _jsonString_InTo_ChannelsConfig(ChannelsConfig &config, const String dataAsJsonString)
{
	StaticJsonDocument<1024> configData;
	DeserializationError error = deserializeJson(configData, dataAsJsonString);
	if (error)
	{
		Serial.println(F("Failed to read file, using default configuration"));
	}
	else
	{
		_jsonObject_InTo_ChannelsConfig(config, configData.as<JsonObject>());
	}
}

void _jsonObject_InTo_ChannelsConfig(ChannelsConfig &config, const JsonObject configData)
{
	strncpy(config.host,
			configData["host"],
			sizeof(config.host));

	strncpy(config.path,
			configData["path"],
			sizeof(config.path));

	strncpy(config.fingerprint,
			configData["fingerprint"],
			sizeof(config.fingerprint));

	strncpy(config.headerAuthorization,
			configData["headerAuthorization"],
			sizeof(config.headerAuthorization));

	const JsonObject channelBoundsData = //
		configData["bounds"].as<JsonObject>();

	config.bounds.min = channelBoundsData["min"].as<uint8_t>();
	config.bounds.max = channelBoundsData["max"].as<uint8_t>();
	config.bounds.step = channelBoundsData["step"].as<uint8_t>();
}
