#include "simple-wifi-client.h"

void SimpleWiFiClient::begin( //
    const char *ssid,         //
    const char *psk           //
)
{
  _wiFiSSID = ssid;
  _wiFiPSK = psk;
}

bool SimpleWiFiClient::connectWiFi()
{
  Serial.println(F("Try to connect WLAN."));

  WiFi.mode(WIFI_STA);
  WiFi.begin(_wiFiSSID, _wiFiPSK);

  if (isConnectedWiFi(_wiFiConnectiontimeoutInSec))
  {
    Serial.println(F(": WLAN connected."));
    Serial.print("IP address: ");
    Serial.println(WiFi.localIP());
    return true;
  }
  else
  {
    Serial.println(F(": WLAN could't be connected."));
    return false;
  }
}

bool SimpleWiFiClient::isConnectedWiFi(long timeOutSec)
{
  long timeOutMs = timeOutSec * 1000;
  int z = 0;
  while (WiFi.status() != WL_CONNECTED)
  {
    delay(200);
    Serial.print(".");

    if (z == timeOutMs / 200)
    {
      return false;
    }
    z++;
  }
  return true;
}

String SimpleWiFiClient::get( //
    const char *host,         //
    const char *resource      //
)
{
  WiFiClient client;
  client.setTimeout(_httpRequestTimeoutInMs);

  Serial.printf("[simple-wifi-client] Connecting to '%s'.\n", host);

  if (client.connect(host, 80))
  {
    Serial.println("[simple-wifi-client] ... connection succeeded.");
  }
  else
  {
    Serial.println("[simple-wifi-client] ... connection failed.");
    return "";
  }

  Serial.printf("[simple-wifi-client] Requesting URL: '%s'...\n", resource);

  client.print( //
      String("GET ") + resource + " HTTP/1.1\r\n" +
      "Host: " + host + "\r\n" +
      "User-Agent: SimpeWifiClient\r\n" +
      "Connection: close\r\n\r\n");

  Serial.println("[simple-wifi-client] ... request sent.");
  while (client.connected())
  {
    String line = client.readStringUntil('\n');
    if (line == "\r")
    {
      Serial.println("[simple-wifi-client] ... headers received.");
      break;
    }
  }
  String result = client.readString();

  return result;
}

String SimpleWiFiClient::gets(      //
    const char *host,               //
    const char *resource,           //
    const char *fingerprint,        //
    const char *headerAuthorization //
)
{
  WiFiClientSecure client;
  client.setTimeout(_httpRequestTimeoutInMs);

  Serial.printf("[simple-wifi-client] Connecting to '%s'.\n", host);

  Serial.printf("[simple-wifi-client] ... using fingerprint '%s'.\n", fingerprint);
  client.setFingerprint(fingerprint);

  if (client.connect(host, 443))
    Serial.println("[simple-wifi-client] ... connection succeeded.");
  else
  {
    Serial.println("[simple-wifi-client] ... connection failed.");
    return "";
  }

  Serial.printf("[simple-wifi-client] Requesting URL: '%s'...\n", resource);

  client.print( //
      String("GET ") + resource + " HTTP/1.1\r\n" +
      "Host: " + host + "\r\n" +
      "Authorization: " + headerAuthorization + "\r\n" +
      "User-Agent: SimpeWifiClient\r\n" +
      "Connection: close\r\n\r\n");

  Serial.println("[simple-wifi-client] ... request sent.");
  while (client.connected())
  {
    String line = client.readStringUntil('\n');
    if (line == "\r")
    {
      Serial.println("[simple-wifi-client] ... headers received.");
      break;
    }
  }
  String result = client.readString();

  return result;
}
