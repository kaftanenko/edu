#ifndef LOGGING_OVER_SERIAL_H
#define LOGGING_OVER_SERIAL_H

const bool DEBUG_MODE = true;

void debug(String message) {

  if (DEBUG_MODE) {
    Serial.println(message);
  }
}

#endif LOGGING_OVER_SERIAL_H
