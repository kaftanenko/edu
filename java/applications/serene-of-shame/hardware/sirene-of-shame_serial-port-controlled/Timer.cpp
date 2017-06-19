#include "Arduino.h"
#include "Timer.h"

bool Timer::isOver(long timeoutInMs) {

  long currentTime = millis();
  return (currentTime - _timerStartTime) > timeoutInMs;
}

void Timer::reset() {

  _timerStartTime = millis();
}
