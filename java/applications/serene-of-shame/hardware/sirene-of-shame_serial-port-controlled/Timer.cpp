#include "Arduino.h"
#include "Timer.h"

bool Timer::isOver(int timeoutInMs) {

  int currentTime = millis();
  return (currentTime - _timerStartTime) > timeoutInMs;
}

void Timer::reset() {

  _timerStartTime = millis();
}
