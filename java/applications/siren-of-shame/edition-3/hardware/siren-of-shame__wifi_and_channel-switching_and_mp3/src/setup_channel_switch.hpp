#include <Arduino.h>

#include "simple-inc-dec-counter.h"

// ... configuration: PLUS_MINUS_COUNTER

#define PLUS_MINUS_COUNTER__MIN_VALUE___1 1
#define PLUS_MINUS_COUNTER__MAX_VALUE__20 20
#define PLUS_MINUS_COUNTER__STEP_VALUE__1 1

SimplePlusMinusCounter simplePlusMinusCounter(

    PLUS_MINUS_COUNTER__MIN_VALUE___1,
    PLUS_MINUS_COUNTER__MAX_VALUE__20,
    PLUS_MINUS_COUNTER__STEP_VALUE__1);

// ... configuration: PLUS_MINUS_SWITCH

#define PLUS_MINUS_SWITCH__INC__INT_PIN__D5 D5
#define PLUS_MINUS_SWITCH__DEC__INT_PIN__D6 D6

void incCallback()
{
  simplePlusMinusCounter.incValue();
}

void decCallback()
{
  simplePlusMinusCounter.decValue();
}

// ... business methods

void channelSwitch_Setup()
{
  //pinMode(PLUS_MINUS_SWITCH__INC__INT_PIN__D5, INPUT_PULLUP);
  //pinMode(PLUS_MINUS_SWITCH__DEC__INT_PIN__D6, INPUT_PULLUP);

  attachInterrupt(
      digitalPinToInterrupt(PLUS_MINUS_SWITCH__DEC__INT_PIN__D6),
      decCallback,
      FALLING);
  attachInterrupt(
      digitalPinToInterrupt(PLUS_MINUS_SWITCH__INC__INT_PIN__D5),
      incCallback,
      FALLING);
}

void channelSwitch_Setup(uint8_t min, uint8_t max, uint8_t step)
{
  simplePlusMinusCounter.setRange(min, max, step);
  channelSwitch_Setup();
}

// ...

uint8_t channelSwitch_GetCurrentValue()
{
  return simplePlusMinusCounter.getValue();
}
