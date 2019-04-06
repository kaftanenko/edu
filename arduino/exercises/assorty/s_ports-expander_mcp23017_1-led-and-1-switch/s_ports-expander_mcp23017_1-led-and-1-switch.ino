#include "Arduino.h"

//#include <Wire.h>
#include "Adafruit_MCP23017.h"

Adafruit_MCP23017 mcp;

void setup() {

  mcp.begin(0);
  mcp.pinMode(7, INPUT);
  mcp.pullUp(7, HIGH);
  mcp.pinMode(8, OUTPUT);
}

void loop() {

  if (mcp.digitalRead(7) == LOW) {

    mcp.digitalWrite(8, HIGH);  
  } else {
    mcp.digitalWrite(8, LOW);  
  }
}
