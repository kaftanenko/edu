#ifndef Timer_h
#define Timer_h

#include "Arduino.h"

class Timer {

  public:
    bool isOver(long timeoutInMs);
    void reset();
	
  private:
    long _timerStartTime;

};

#endif
