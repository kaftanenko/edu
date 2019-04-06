#include "Arduino.h"
#include "SoftwareSerial.h"

#include <ArduinoJson.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>
#include <SPI.h>
#include <SD.h>

// ... constants

const String CONFIG_GREETING_MESSAGE = "Wellcome to the \"Siren Of Shame\"!";
const String CONFIG_COMMANDS_MESSAGE = "I understand only binary input in JSON format.";

const String COMMAND_RESULT_RESPONSE_FAILED = "FAILED";
const String COMMAND_RESULT_RESPONSE_SUCCEEDED = "SUCCEEDED";

const String CONFIG_FILE_PATH = "/config.jsn"; // Format: {"ssid":"...","password":"...","hostname":"..."}
const int CONFIG_SERIAL_PORT_BAUD_RATE__115200 = 115200;
const int CONFIG_SD_CHIP_SELECT_PIN__4 = 4;

// ... type definitions

#define DBG_OUTPUT_PORT Serial

struct WlanConfig {
  char ssid[24];
  char password[32];
  char hostname[64];
};

// ... properties

ESP8266WebServer server(80);

static bool hasSD = false;

WlanConfig wlanConfig;
String jsonFileContent = "{ \"message\": \"Hello world!\" }";

// ... business methods

void setup()
{

  // ... init: serial port

  DBG_OUTPUT_PORT.begin(CONFIG_SERIAL_PORT_BAUD_RATE__115200);
  //DBG_OUTPUT_PORT.setDebugOutput(true);
  while (!DBG_OUTPUT_PORT) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  // ... init: wellcome message

  Serial.println(CONFIG_GREETING_MESSAGE);
  Serial.println(CONFIG_COMMANDS_MESSAGE);
  Serial.println("");

  // ... init: SD card

  DBG_OUTPUT_PORT.print("Initializing SD card: ... ");

  if (SD.begin(CONFIG_SD_CHIP_SELECT_PIN__4)) {

    DBG_OUTPUT_PORT.println("succeeded.");
    hasSD = true;
  } else {
    DBG_OUTPUT_PORT.println("failed.");
    hasSD = false;
  }

  // ... init: configuration

  DBG_OUTPUT_PORT.print("Loading configuration from '" + CONFIG_FILE_PATH + "': ... ");
  if (hasSD && loadConfiguration(CONFIG_FILE_PATH, wlanConfig)) {

    DBG_OUTPUT_PORT.println("succeeded.");
  } else {
    DBG_OUTPUT_PORT.println("failed.");
  }

  // ... init: HTTP server

  WiFi.begin(wlanConfig.ssid, wlanConfig.password);
  DBG_OUTPUT_PORT.print("Connecting to ");
  DBG_OUTPUT_PORT.print(wlanConfig.ssid);
  DBG_OUTPUT_PORT.print(": ... ");

  uint8_t i = 0;
  while (WiFi.status() != WL_CONNECTED && i++ < 20) { //wait 10 seconds
    delay(500);
  }

  if (WiFi.status() != WL_CONNECTED) {

    DBG_OUTPUT_PORT.println("failed.");
  } else {
    DBG_OUTPUT_PORT.print("succeeded. Assigned IP: ");
    DBG_OUTPUT_PORT.print(WiFi.localIP());
    DBG_OUTPUT_PORT.println(".");

    DBG_OUTPUT_PORT.print("MDNS responder: ... ");
    if (MDNS.begin(wlanConfig.hostname)) {
      MDNS.addService("http", "tcp", 80);
      DBG_OUTPUT_PORT.print("started. You can now connect to http://");
      DBG_OUTPUT_PORT.print(wlanConfig.hostname);
      DBG_OUTPUT_PORT.println(".local.");
    } else {
      DBG_OUTPUT_PORT.println("failed.");
    }

    server.onNotFound(handleNotFound);

    server.begin();
    DBG_OUTPUT_PORT.println("HTTP server: ... started.");

    Serial.setTimeout(2000);
  }
}

// ... workflow cycle method

void loop()
{

  if (Serial.available() > 0) {

    jsonFileContent = Serial.readString();
    Serial.println(COMMAND_RESULT_RESPONSE_SUCCEEDED);
  }

  server.handleClient();
}

// ... business methods: HTTP server

bool handleDefaultContent(String path) {
  
  if (jsonFileContent.length() > 0) {

    String dataType = "application/json";
    server.send(200, dataType, jsonFileContent);
    
    return true;
  } else {
    return false;
  }
}

void handleNotFound() {
  
  if (handleDefaultContent(server.uri())) return;
  
  String message = "";
  message += "URI: ";
  message += server.uri();
  message += "\nMethod: ";
  message += (server.method() == HTTP_GET) ? "GET" : "POST";
  message += "\nArguments: ";
  message += server.args();
  message += "\n";
  for (uint8_t i = 0; i < server.args(); i++) {
    message += " NAME:" + server.argName(i) + "\n VALUE:" + server.arg(i) + "\n";
  }
  server.send(404, "text/plain", message);
  
  DBG_OUTPUT_PORT.print(message);
}

// ... helper methods: configuration

bool loadConfiguration(const String configFilePath, WlanConfig &config) {

  bool result;

  StaticJsonBuffer<512> jsonBuffer;

  File configFile = SD.open((char *)configFilePath.c_str());
  JsonObject &root = jsonBuffer.parseObject(configFile);
  configFile.close();

  if (!root.success()) {

    result = false;
  } else {

    strlcpy(config.ssid, root["ssid"], sizeof(config.ssid));
    strlcpy(config.password, root["password"], sizeof(config.password));
    strlcpy(config.hostname, root["hostname"], sizeof(config.hostname));

    result = true;
  }

  return result;
}

