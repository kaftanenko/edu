#ifndef simple_timer_h
#define simple_timer_h

#include "Arduino.h"

class SimpleTimer {

  public:
    bool isOver(long timeoutInMs);
    void reset();
	
  private:
    long _timerStartTime;

};

#endif
