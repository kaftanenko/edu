#ifndef simple_wifi_client_h
#define simple_wifi_client_h

#include <Arduino.h>
#include <ArduinoJson.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <WiFiUdp.h>
#include <SPI.h>

class SimpleWiFiClient
{
  const char *_wiFiSSID;
  const char *_wiFiPSK;

  int _wiFiConnectiontimeoutInSec = 30;
  int _httpRequestTimeoutInMs = 3000;

public:
  SimpleWiFiClient(
      const char *wiFiSSID,    //
      const char *wiFiPSK      //
      ) : _wiFiSSID(wiFiSSID), //
          _wiFiPSK(wiFiPSK)    //
          {};
  SimpleWiFiClient(                          //
      ) : SimpleWiFiClient(nullptr, nullptr) //
          {};

  void begin(const char *ssid, const char *password);

  bool connectWiFi();
  bool isConnectedWiFi(const long timeOutSec);

  String get(              //
      const char *host,    //
      const char *resource //
  );

  String gets(                        //
      const char *host,               //
      const char *resource,           //
      const char *fingerprint,        //
      const char *headerAuthorization //
  );
};

#endif
