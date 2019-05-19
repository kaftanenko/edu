#ifndef simple_wifi_client_h
#define simple_wifi_client_h

#include <Arduino.h>
#include <ArduinoJson.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <WiFiUdp.h>
#include <SPI.h>

class SimpleWiFiClient {

  bool _debugModeOn = false;

  const char* _wiFiSSID;
  const char* _wiFiPSK;

  int _wiFiConnectiontimeoutInSec = 30;

  int _httpRequestTimeoutInMs = 1000;

  WiFiClient _client;

  public:

    SimpleWiFiClient(
      const char* wiFiSSID,
      const char* wiFiPSK
    ) : _wiFiSSID(wiFiSSID), _wiFiPSK(wiFiPSK) {};

    bool connectWiFi();
    bool isConnectedWiFi(long timeOutSec);

    String get(const char* host, const char* resource);
	
    void setDebugMode(bool doDebugModeOn) {
      _debugModeOn = doDebugModeOn;
    }

  private:
    bool _connectHost(const char* hostName);
    void _disconnectHost();
    bool _skipHostResponseHeaders();

    void _logDebug(const String &value);
};

#endif
