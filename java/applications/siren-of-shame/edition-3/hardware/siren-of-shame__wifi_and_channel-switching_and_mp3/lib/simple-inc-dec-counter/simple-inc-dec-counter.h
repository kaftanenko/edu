#ifndef simple_inc_dec_counter_h
#define simple_inc_dec_counter_h

#include "Arduino.h"
#include "simple-timer.h"

// ... constants

#define DEFAULT_COUNTER_MIN_VALUE__0  0
#define DEFAULT_COUNTER_MAX_VALUE__9  9
#define DEFAULT_COUNTER_STEP_VALUE__1 1

#define DEFAULT_STATE_CHANGE_DELAY_IN_MS__200 200

// ... 

class SimplePlusMinusCounter {

  bool _debugModeOn = false;

  uint8_t _minValue;
  uint8_t _maxValue; 
  uint8_t _stepValue;

  uint8_t _minStateChangeDelayInMs;

  uint8_t _currentValue;

  volatile bool _updateStop = false;
  unsigned long _lastStateChangeOnInMs = 0;

  public:

    SimplePlusMinusCounter(uint8_t min, uint8_t max, uint8_t step): 
      _minValue(min), 
      _maxValue(max), 
      _stepValue(step),
      _minStateChangeDelayInMs(DEFAULT_STATE_CHANGE_DELAY_IN_MS__200),
      _currentValue(min)
      {}
    SimplePlusMinusCounter() :
      SimplePlusMinusCounter(
        DEFAULT_COUNTER_MIN_VALUE__0,
        DEFAULT_COUNTER_MAX_VALUE__9,
        DEFAULT_COUNTER_STEP_VALUE__1
      ) {}

    void incValue();
    void decValue();

    uint8_t getValue();
    void setValue(uint8_t value);

    void setRange(uint8_t min, uint8_t max, uint8_t step);
    void setStateChangeDelayInMs(uint8_t delayInMs);

    void setDebugMode(bool doDebugModeOn) {
      _debugModeOn = doDebugModeOn;
    }

  private:
    int _normalize(uint8_t value);
    void _logDebug(const String &value);
};

#endif
