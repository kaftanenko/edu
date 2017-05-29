#ifndef Timer_h
#define Timer_h

#include "Arduino.h"

class Timer {

  public:
    bool isOver(int timeoutInMs);
    void reset();
	
  private:
    int _timerStartTime;

};

#endif
