#include "simple-wifi-client.h"

bool SimpleWiFiClient::connectWiFi() {

  WiFi.mode(WIFI_STA);
  WiFi.begin(_wiFiSSID, _wiFiPSK);
    
  if (isConnectedWiFi(_wiFiConnectiontimeoutInSec)) {
        
    _logDebug(F("WLAN läuft"));
    return true;
  } else {
    _logDebug(F("WLAN läuft nicht"));
    return false;
  }
}

bool SimpleWiFiClient::isConnectedWiFi(long timeOutSec) {

  timeOutSec = timeOutSec * 1000;
  int z = 0;
  while (WiFi.status() != WL_CONNECTED) {
    delay(200);
    Serial.print(".");

    if (z == timeOutSec / 200) { return false; }
    z++;
  }
  return true;
}

String SimpleWiFiClient::get(const char* host, const char* resource) {

  if (_connectHost(host)) {

    _logDebug("GET ");
    Serial.println(resource);

    _client.print("GET ");
    _client.print(resource);
    _client.println(" HTTP/1.0");
    _client.print("Host: ");
    _client.println(host);
    _client.println("Connection: close");
    _client.println();

    _skipHostResponseHeaders();

    const String result = _client.readString();
    _logDebug(result);

    _disconnectHost();
    
    return result;
  }

  return "...";
}

// ...

bool SimpleWiFiClient::_connectHost(const char* hostName) {

  _logDebug("Connect to ");
  Serial.println(hostName);

  bool ok = _client.connect(hostName, 80);

  Serial.println(ok ? "Connected" : "Connection Failed!");
  return ok;
}


void SimpleWiFiClient::_disconnectHost() {
  _logDebug("Disconnect");
  _client.stop();
}

bool SimpleWiFiClient::_skipHostResponseHeaders() {
  
  char endOfHeaders[] = "\r\n\r\n";

  _client.setTimeout(_httpRequestTimeoutInMs);
  bool ok = _client.find(endOfHeaders);

  if (!ok) {

    Serial.println("No response or invalid response!");
  }

  return ok;
}

// ... helper methods

void SimpleWiFiClient::_logDebug(const String &value) {

  if (_debugModeOn) {
    Serial.println(value);
  }
}
