#include "simple-timer.h"

bool SimpleTimer::isOver(long timeoutInMs) {

  long currentTime = millis();
  return (currentTime - _timerStartTime) > timeoutInMs;
}

void SimpleTimer::reset() {

  _timerStartTime = millis();
}
