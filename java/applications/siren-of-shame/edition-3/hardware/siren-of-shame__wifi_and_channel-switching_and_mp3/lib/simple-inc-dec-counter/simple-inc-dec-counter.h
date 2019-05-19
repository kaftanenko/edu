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

  uint _minValue;
  uint _maxValue; 
  uint _stepValue;

  unsigned long _minStateChangeDelayInMs;

  uint _currentValue;

  volatile bool _updateStop = false;
  unsigned long _lastStateChangeOnInMs = 0;

  public:

    SimplePlusMinusCounter(uint min, uint max, uint step): 
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

    uint getValue();
    void setValue(uint value);

    void setDebugMode(bool doDebugModeOn) {
      _debugModeOn = doDebugModeOn;
    }

  private:
    int _normalize(uint value);
    void _logDebug(const String &value);
};

#endif
