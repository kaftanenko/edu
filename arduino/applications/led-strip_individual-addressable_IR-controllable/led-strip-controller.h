#ifndef LED_STRIP_CONTROLLER_H
#define LED_STRIP_CONTROLLER_H

#include "FastLED.h"

#define LED_STRIP_NUM_LEDS 150
CRGB leds[LED_STRIP_NUM_LEDS];

const CRGB COLOR_BLACK = CRGB(0, 0, 0);
const CRGB COLOR_WHITE = CRGB(255, 255, 255);

#define LED_STRIP_SIGNAL_PIN 11

#define LED_STRIP_STEP_DELAY 0         // скорость движения радуги

const uint8_t BRIGHTNESS_MIN = 0;
const uint8_t BRIGHTNESS_MID = 50;
const uint8_t BRIGHTNESS_MAX = 255;
const uint8_t BRIGHTNESS_INCDEC_STEP = 20;

const int RAINBOW_WAVES_COUNT = 3;
const int RAINBOW_COLOR_GRADES_PER_LED = RAINBOW_WAVES_COUNT * 256 / LED_STRIP_NUM_LEDS;

byte counter;

void LED_Strip_Setup() {

  FastLED.addLeds<WS2811, LED_STRIP_SIGNAL_PIN, GRB>(leds, LED_STRIP_NUM_LEDS).setCorrection( TypicalLEDStrip );
  FastLED.setBrightness(BRIGHTNESS_MID);
  pinMode(13, OUTPUT);
}

void LED_Strip_Delay_Before_NextStep() {

  delay(LED_STRIP_STEP_DELAY);
}

uint8_t LED_Strip_Get_Brightness() {

  return FastLED.getBrightness();
}
void LED_Strip_Reset_Brightness() {

  FastLED.setBrightness(BRIGHTNESS_MID);
  FastLED.show();
}
void LED_Strip_Increment_Brightness() {

  FastLED.setBrightness((FastLED.getBrightness() + BRIGHTNESS_INCDEC_STEP) % BRIGHTNESS_MAX);
  FastLED.show();
}
void LED_Strip_Decrement_Brightness() {

  FastLED.setBrightness(max(0, FastLED.getBrightness() - BRIGHTNESS_INCDEC_STEP));
  FastLED.show();
}

void LED_Strip_Play_Effect_PowerOff() {

  FastLED.clear();
  FastLED.show();
}

void LED_Strip_Play_Effect_Snow_NextStep() {

  byte strip_length = 2;

  for (int i = 0; i < (LED_STRIP_NUM_LEDS - 1); i++ ) {

    leds[i] = leds[i + 1];
  }
  if (strip_length < 2 || (counter % strip_length == 0)) {

    leds[LED_STRIP_NUM_LEDS - 1] = random(0, 99) % 25 > 10 ? COLOR_WHITE : COLOR_BLACK;
  }
  counter++;        // counter меняется от 0 до 255 (тип данных byte)

  FastLED.show();

  LED_Strip_Delay_Before_NextStep();
}

void LED_Strip_Play_Effect_Rainbow_NextStep() {

  for (int i = 0; i < LED_STRIP_NUM_LEDS; i++ ) {

    leds[i] = CHSV(counter + i * RAINBOW_COLOR_GRADES_PER_LED, 255, 255);  // HSV. Увеличивать HUE (цвет)
    // умножение i уменьшает шаг радуги
  }
  counter += 8;        // counter меняется от 0 до 255 (тип данных byte)

  FastLED.show();

  LED_Strip_Delay_Before_NextStep();
}

#endif LED_STRIP_CONTROLLER_H
