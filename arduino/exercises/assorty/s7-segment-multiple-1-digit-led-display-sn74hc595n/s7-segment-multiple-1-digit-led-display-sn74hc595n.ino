
#include <ShiftRegister74HC595.h>

int numberOfShiftRegisters = 2; // number of shift registers attached in series
int serialDataPin = D0; // DS
int clockPin = D2; // SHCP
int latchPin = D1; // STCP
ShiftRegister74HC595 sr (numberOfShiftRegisters, serialDataPin, clockPin, latchPin);

const uint8_t _1 = B00000110;
const uint8_t _2 = B01011011;
const uint8_t _3 = B01001111;
const uint8_t _4 = B01100110;
const uint8_t _5 = B01101101;
const uint8_t _6 = B01111101;
const uint8_t _7 = B00000111;
const uint8_t _8 = B01111111;
const uint8_t _9 = B01101111;
const uint8_t _0 = B00111111;

const uint8_t digits[] = { _0, _1, _2, _3, _4, _5, _6, _7, _8, _9 };

byte currentClickValue = 0;
byte currentDisplayValue = 0;

void setup() {

  Serial.begin(9600);
  
  pinMode(D3, INPUT_PULLUP);
  pinMode(D4, INPUT_PULLUP);

  attachInterrupt(digitalPinToInterrupt(D3), incCallback, FALLING);
  attachInterrupt(digitalPinToInterrupt(D4), decCallback, FALLING);
  
  showNumber(currentDisplayValue);
}

void loop() {

  // currentClickValue = (currentClickValue + 1) % 100;
  if (currentDisplayValue != currentClickValue) {

    //noInterrupts();
    currentDisplayValue = currentClickValue;
    showNumber(currentDisplayValue);
    //interrupts();
  }
  delay(100);
}

void showNumber(byte value) {

    uint8_t pinValues[] = { digits[value / 10], digits[value % 10] };
    sr.setAll(pinValues);
}

void incCallback() {
  
  currentClickValue = (currentClickValue + 1) % 100;
  Serial.print("++ ");
  Serial.println(currentClickValue);
}

void decCallback() {

  currentClickValue = (100 + currentClickValue - 1) % 100;
  Serial.print("-- ");
  Serial.println(currentClickValue);
}

