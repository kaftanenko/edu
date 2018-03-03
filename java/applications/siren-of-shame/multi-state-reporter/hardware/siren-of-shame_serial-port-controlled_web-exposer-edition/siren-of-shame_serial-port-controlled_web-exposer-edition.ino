#include "Arduino.h"
#include "SoftwareSerial.h"

#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>

#include "Timer.h"

// ... constants

const String COMMAND_CLEAR = "CLEAR";
const String COMMAND_RESPONSE_SUCCEEDED = "SUCCEEDED";

const String CONFIG_GREETING_MESSAGE = "Wellcome to the \"Siren Of Shame\"!";
const String CONFIG_COMMANDS_MESSAGE = "I understand following commands: '"
                                       + COMMAND_CLEAR
                                       + "'.";
                                       
const int CONFIG_SERIAL_PORT_BAUD_RATE__9600 = 9600;

const char* CONFIG_WLAN_SSID = "...";
const char* CONFIG_WLAN_PASSWORD = "...";

// ... properties

String content = "";

ESP8266WebServer server(80);

// ... business methods

void setup()
{

  Serial.begin(CONFIG_SERIAL_PORT_BAUD_RATE__9600);

  // ... wellcome message

  println_SerialPort_Message(CONFIG_GREETING_MESSAGE);
  println_SerialPort_Message(CONFIG_COMMANDS_MESSAGE);

  // ... init web server

  WiFi.begin(CONFIG_WLAN_SSID, CONFIG_WLAN_PASSWORD);
  Serial.println("");

  // Wait for connection
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(CONFIG_WLAN_SSID);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());

  if (MDNS.begin("esp8266")) {
    Serial.println("MDNS responder started");
  }

  server.on("/", handleRoot);

  server.on("/inline", [](){
    server.send(200, "text/plain", "this works as well");
  });
  
  server.on("/content", [](){

    if (content == "") {
      handleNotFound();
    } else {
      server.send(200, "application/json", content);
    }
  });

  server.onNotFound(handleNotFound);

  server.begin();
  Serial.println("HTTP server started");
}

void loop()
{

  if (isAvailable_SerialPort_Message()) {

    String message = read_SerialPort_Message();

    if (message == COMMAND_CLEAR) {
      
      content = "";
      println_SerialPort_Message(COMMAND_RESPONSE_SUCCEEDED);
    } else {
      content = message;
      println_SerialPort_Message(COMMAND_RESPONSE_SUCCEEDED);
    }
  }

  server.handleClient();
     
  delay(1);
}

// ... helper methods

bool isAvailable_SerialPort_Message() {

  return Serial.available() > 0;
}

String read_SerialPort_Message() {

  String message = "";
  while (Serial.available() > 0) {
    message += char(Serial.read());
    delay(10);
  }
  return message;
}

void print_SerialPort_Message(String message) {

  Serial.print(message);
}

void println_SerialPort_Message(String message) {

  Serial.println(message);
  Serial.flush();
}

// ...


void handleRoot() {
  server.send(200, "text/plain", "hello from esp8266!");
}

void handleNotFound(){
  String message = "File Not Found\n\n";
  message += "URI: ";
  message += server.uri();
  message += "\nMethod: ";
  message += (server.method() == HTTP_GET)?"GET":"POST";
  message += "\nArguments: ";
  message += server.args();
  message += "\n";
  for (uint8_t i=0; i<server.args(); i++){
    message += " " + server.argName(i) + ": " + server.arg(i) + "\n";
  }
  server.send(404, "text/plain", message);
}

