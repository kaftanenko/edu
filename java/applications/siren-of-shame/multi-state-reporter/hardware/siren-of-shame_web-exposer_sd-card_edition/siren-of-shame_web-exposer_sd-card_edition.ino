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

const String COMMAND_DELETE = "DELETE";
const String COMMAND_GET = "GET";
const String COMMAND_POST = "POST";
const String COMMAND_PUT = "PUT";
const String COMMAND_PING = "PING";

const char COMMAND_LINE_END_SIGN = '\n';

const String COMMAND_RESULT_RESPONSE_FAILED = "FAILED";
const String COMMAND_RESULT_RESPONSE_SUCCEEDED = "SUCCEEDED";

const String CONFIG_GREETING_MESSAGE = "Wellcome to the \"Siren Of Shame\"!";
const String CONFIG_COMMANDS_MESSAGE = "I understand following commands: '"
                                       + COMMAND_DELETE + "', '"
                                       + COMMAND_GET + "', '"
                                       + COMMAND_PING + "', '"
                                       + COMMAND_POST + "', '"
                                       + COMMAND_PUT
                                       + "'.";

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
WlanConfig wlanConfig;
File uploadFile;

static bool hasSD = false;

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

  println_SerialPort_Message(CONFIG_GREETING_MESSAGE);
  println_SerialPort_Message(CONFIG_COMMANDS_MESSAGE);
  println_SerialPort_Message("");

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
  if (loadConfiguration(CONFIG_FILE_PATH, wlanConfig)) {

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

    server.on("/list", HTTP_GET, printDirectory);
    server.on("/edit", HTTP_DELETE, handleDelete);
    server.on("/edit", HTTP_PUT, handleCreate);
    server.on("/edit", HTTP_POST, []() {
      returnOK();
    }, handleFileUpload);
    server.onNotFound(handleNotFound);

    server.begin();
    DBG_OUTPUT_PORT.println("HTTP server: ... started.");

    Serial.setTimeout(2000);
  }
}

// ... workflow cycle method

void loop()
{

  if (isAvailable_SerialPort_Message()) {

    String commandLine = read_SerialPort_Message();

    if (commandLine == COMMAND_PING) {

      println_SerialPort_Message(commandLine + ": " + COMMAND_RESULT_RESPONSE_SUCCEEDED);
    } else if (commandLine.startsWith(COMMAND_DELETE)) {

      String resourcePath = extractResourcePathArg(commandLine, COMMAND_DELETE);
      int handledBytes = handleDeleteResource_Over_SerialPort(resourcePath);

      println_SerialPort_Message(commandLine + ": (deleted " + handledBytes + " Bytes)");
    } else if (commandLine.startsWith(COMMAND_GET)) {

      String resourcePath = extractResourcePathArg(commandLine, COMMAND_GET);
      int handledBytes = handleUploadResource_Over_SerialPort(resourcePath);

      println_SerialPort_Message(commandLine + ": (uploaded " + handledBytes + " bytes)");
    } else if (commandLine.startsWith(COMMAND_POST)) {

      String resourcePath = extractResourcePathArg(commandLine, COMMAND_POST);
      delay(50);
      int handledBytes = handleDownloadResource_Over_SerialPort(resourcePath);

      println_SerialPort_Message(commandLine + ": (downloaded " + handledBytes + " bytes)");
    } else if (commandLine.startsWith(COMMAND_PUT)) {

      String resourcePath = extractResourcePathArg(commandLine, COMMAND_PUT);
      int handledBytes = handleCreateResource_Over_SerialPort(resourcePath);

      println_SerialPort_Message(commandLine + ": (created " + handledBytes + " bytes)");
    } else {

      println_SerialPort_Message(commandLine);
    }
  }

  server.handleClient();
}

// ... business methods: Serial Port

String extractResourcePathArg(String commandLine, String command) {

  return commandLine.substring(command.length() + 1);
}

int handleCreateResource_Over_SerialPort(String resourcePath) {

  int handledBytes = 0;

  if (isResourceRootDir(resourcePath) || isResourceExists(resourcePath)) {

    // ... nothing to do.
    return handledBytes;
  } else {
    createResource(resourcePath);
    return handledBytes;
  }
}

int handleDeleteResource_Over_SerialPort(String resourcePath) {

  int handledBytes = 0;

  if (isResourceRootDir(resourcePath) || !isResourceExists(resourcePath) || isConfigFile(resourcePath)) {

    return handledBytes;
  } else {

    File resourceFile = SD.open(resourcePath.c_str());
    handledBytes = resourceFile.size();
    resourceFile.close();

    deleteRecursive(resourcePath);

    return handledBytes;
  }
}

int handleDownloadResource_Over_SerialPort(String targetPath) {

  int handledBytes = 0;

  handleDeleteResource_Over_SerialPort(targetPath);

  File targetFile = SD.open(targetPath.c_str(), FILE_WRITE);
  while (Serial.available()) {
    String content = Serial.readString();
    targetFile.print(content);
    handledBytes += content.length();
    //   handledBytes += targetFile.write(Serial.read());
  }
  targetFile.close();

  return handledBytes;
}

int handleUploadResource_Over_SerialPort(String resourcePath) {

  int handledBytes = 0;

  if (!isResourceExists(resourcePath) || isConfigFile(resourcePath)) {

    return handledBytes;
  } else {

    File sourceFile = SD.open(resourcePath.c_str());
    if (sourceFile.isDirectory()) {

      handleUploadResource_DirContent_Over_SerialPort(sourceFile);
    } else {
      handleUploadResource_FileContent_Over_SerialPort(sourceFile);
    }
    sourceFile.close();

    return handledBytes;
  }
}

int handleUploadResource_DirContent_Over_SerialPort(File resourceDir) {

  int handledBytes = -1;

  Serial.print("[");
  resourceDir.rewindDirectory();
  for (int index = 0; true; ++index) {

    File entryFile = resourceDir.openNextFile();
    if (!entryFile) {
      break;
    }

    String output;
    if (index > 0) {
      output = ',';
    }
    output += buildFileDescription_In_JSON(entryFile);

    Serial.print(output);

    entryFile.close();
  }
  Serial.print("]");

  return handledBytes;
}

int handleUploadResource_FileContent_Over_SerialPort(File resourceFile) {

  int handledBytes = 0;

  while (resourceFile.available()) {
    handledBytes += Serial.write(resourceFile.read());
  }

  return handledBytes;
}

// ... business methods: HTTP server

void returnOK() {
  server.send(200, "text/plain", "");
}

void returnFail(String msg) {
  server.send(500, "text/plain", msg + "\r\n");
}

bool loadFromSdCard(String path) {
  String dataType = "text/plain";
  if (path.endsWith("/")) path += "index.htm";

  if (path.endsWith(".src")) path = path.substring(0, path.lastIndexOf("."));
  else if (path.endsWith(".htm")) dataType = "text/html";
  else if (path.endsWith(".css")) dataType = "text/css";
  else if (path.endsWith(".js")) dataType = "application/javascript";
  else if (path.endsWith(".png")) dataType = "image/png";
  else if (path.endsWith(".gif")) dataType = "image/gif";
  else if (path.endsWith(".jpg")) dataType = "image/jpeg";
  else if (path.endsWith(".ico")) dataType = "image/x-icon";
  else if (path.endsWith(".xml")) dataType = "text/xml";
  else if (path.endsWith(".pdf")) dataType = "application/pdf";
  else if (path.endsWith(".zip")) dataType = "application/zip";
  else if (path.endsWith(".jsn") || path.endsWith(".json")) dataType = "application/json";

  File dataFile = SD.open(path.c_str());
  if (dataFile.isDirectory()) {
    path += "/index.htm";
    dataType = "text/html";
    dataFile = SD.open(path.c_str());
  }

  if (!dataFile)
    return false;

  if (server.hasArg("download")) dataType = "application/octet-stream";

  if (server.streamFile(dataFile, dataType) != dataFile.size()) {
    DBG_OUTPUT_PORT.println("Sent less data than expected!");
  }

  dataFile.close();
  return true;
}

void handleFileUpload() {
  if (server.uri() != "/edit") return;
  HTTPUpload& upload = server.upload();
  if (upload.status == UPLOAD_FILE_START) {
    if (SD.exists((char *)upload.filename.c_str())) SD.remove((char *)upload.filename.c_str());
    uploadFile = SD.open(upload.filename.c_str(), FILE_WRITE);
    DBG_OUTPUT_PORT.print("Upload: START, filename: "); DBG_OUTPUT_PORT.println(upload.filename);
  } else if (upload.status == UPLOAD_FILE_WRITE) {
    if (uploadFile) uploadFile.write(upload.buf, upload.currentSize);
    DBG_OUTPUT_PORT.print("Upload: WRITE, Bytes: "); DBG_OUTPUT_PORT.println(upload.currentSize);
  } else if (upload.status == UPLOAD_FILE_END) {
    if (uploadFile) uploadFile.close();
    DBG_OUTPUT_PORT.print("Upload: END, Size: "); DBG_OUTPUT_PORT.println(upload.totalSize);
  }
}

void handleDelete() {
  if (server.args() == 0) return returnFail("BAD ARGS");
  String path = server.arg(0);
  if (path == "/" || !SD.exists((char *)path.c_str())) {
    returnFail("BAD PATH");
    return;
  }
  deleteRecursive(path);
  returnOK();
}

void deleteRecursive(String path) {
  File file = SD.open((char *)path.c_str());
  if (!file.isDirectory()) {
    file.close();
    SD.remove((char *)path.c_str());
    return;
  }

  file.rewindDirectory();
  while (true) {
    File entry = file.openNextFile();
    if (!entry) break;
    String entryPath = path + "/" + entry.name();
    if (entry.isDirectory()) {
      entry.close();
      deleteRecursive(entryPath);
    } else {
      entry.close();
      SD.remove((char *)entryPath.c_str());
    }
    yield();
  }

  SD.rmdir((char *)path.c_str());
  file.close();
}

void handleCreate() {

  if (server.args() == 0) return returnFail("BAD ARGS");

  String path = server.arg(0);
  if (path == "/" || SD.exists((char *)path.c_str())) {

    returnFail("BAD PATH");
  } else {
    createResource(path);
    returnOK();
  }
}

void createResource(String targetPath) {

  bool isFilePath = targetPath.indexOf('.') > 0;
  if (isFilePath) {

    File file = SD.open((char *)targetPath.c_str(), FILE_WRITE);
    if (file) {
      file.write((const char *)0);
      file.close();
    }
  } else {
    SD.mkdir((char *)targetPath.c_str());
  }
}

void printDirectory() {
  if (!server.hasArg("dir")) return returnFail("BAD ARGS");
  String path = server.arg("dir");
  if (path != "/" && !SD.exists((char *)path.c_str())) return returnFail("BAD PATH");
  File dir = SD.open((char *)path.c_str());
  path = String();
  if (!dir.isDirectory()) {
    dir.close();
    return returnFail("NOT DIR");
  }
  dir.rewindDirectory();
  server.setContentLength(CONTENT_LENGTH_UNKNOWN);
  server.send(200, "text/json", "");
  WiFiClient client = server.client();

  server.sendContent("[");
  for (int cnt = 0; true; ++cnt) {
    File entry = dir.openNextFile();
    if (!entry)
      break;

    String output;
    if (cnt > 0) {
      output = ',';
    }
    output += buildFileDescription_In_JSON(entry);

    server.sendContent(output);
    entry.close();
  }
  server.sendContent("]");
  dir.close();
}

void handleNotFound() {
  if (hasSD && loadFromSdCard(server.uri())) return;
  String message = "SDCARD Not Detected\n\n";
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

// ... helper methods: config

bool isConfigFile(String resourcePath) {

  return resourcePath.indexOf(CONFIG_FILE_PATH) >= 0;
}

bool isResourceExists(String resourcePath) {

  return SD.exists((char *)resourcePath.c_str());
}

bool isResourceRootDir(String resourcePath) {

  return resourcePath == "/";
}

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

// ... helper methods: content

String buildFileDescription_In_JSON(File sourceFile) {

  String result;

  result += "{\"type\":\"";
  result += (sourceFile.isDirectory()) ? "dir" : "file";
  result += "\",\"name\":\"";
  result += sourceFile.name();
  result += "\"";
  result += "}";

  return result;
}

// ... helper methods: serial port

bool isAvailable_SerialPort_Message() {

  return Serial.available() > 0;
}

String read_SerialPort_Message() {

  return Serial.readStringUntil(COMMAND_LINE_END_SIGN);
}

void print_SerialPort_Message(String message) {

  Serial.print(message);
}

void println_SerialPort_Message(String message) {

  Serial.println(message);
  Serial.flush();
}

