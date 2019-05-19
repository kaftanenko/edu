#include "Arduino.h"

// ... business methods

void serialPort_Setup(const uint baudRate) {

  // ... init serial
  Serial.begin(baudRate);
  //Serial.setDebugOutput(true);

  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
}

// ...

bool serialPort_IsAvailable() {

  return Serial && Serial.available() > 0;
}

String serialPort_ReadString() {

  String message = "";
  while (Serial.available() > 0) {
    message += char(Serial.read());
    delay(10);
  }
  return message;
}

void serialPort_Print(String message) {

  Serial.print(message);
}

void serialPort_Println(String message) {

  Serial.println(message);
  Serial.flush();
}
