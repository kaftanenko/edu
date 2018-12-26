
#include <Arduino.h>
#include <IRremote.h>

#include "ir-controller-codes.h"
#include "logging-over-serial.h"
#include "led-strip-controller.h"

const int RECV_PIN = 2;
IRrecv irrecv(RECV_PIN);
decode_results results;

enum LED_STRIP_MODES {

  OFF,
  SNOW,
  RAINBOW
};
const LED_STRIP_MODES DEFAULT_LED_STRIP_MODE = SNOW;

LED_STRIP_MODES currentLedStripMode;
LED_STRIP_MODES lastActiveLedStripMode;

void setup() {

  Serial.begin(9600);

  LED_Strip_Setup();
  LED_Strip_Play_Effect_PowerOff();
  
  currentLedStripMode = DEFAULT_LED_STRIP_MODE;
  lastActiveLedStripMode = DEFAULT_LED_STRIP_MODE;

  attachInterrupt(0, CHECK_IR, CHANGE);
  irrecv.enableIRIn(); // Start the receiver
}

void loop() {

  switch (currentLedStripMode) {
    case OFF:
      // ... nothing to do
      LED_Strip_Play_Effect_PowerOff();
      break;
    case SNOW:
      LED_Strip_Play_Effect_Snow_NextStep();
      break;
    case RAINBOW:
      LED_Strip_Play_Effect_Rainbow_NextStep();
      break;
    default:
      debug("Unsupported LED strip mode: ...");
  }

  delay(150);
}

void toggleOnOff() {

  currentLedStripMode = currentLedStripMode == OFF ? lastActiveLedStripMode : OFF;
  debug(currentLedStripMode == OFF ? "... power off." : "... power on.");
}

void switchMode(LED_STRIP_MODES ledStripMode) {

  if (currentLedStripMode != ledStripMode) {
    
    LED_Strip_Play_Effect_PowerOff();
  
    currentLedStripMode = ledStripMode;
    lastActiveLedStripMode = ledStripMode;
  }
}

void CHECK_IR() {

  while (irrecv.decode(&results)) {

    Serial.println(results.value, DEC);

    switch (results.value) {
      case IR_CODES_POWER:
        debug("... <power> recognized");
        toggleOnOff();
        break;
      case IR_CODES_EQ:
        debug("... <eq> recognized");
        LED_Strip_Reset_Brightness();
        break;
      case IR_CODES_MINUS:
        debug("... <-> recognized");
        LED_Strip_Decrement_Brightness();
        break;
      case IR_CODES_PLUS:
        debug("... <+> recognized");
        LED_Strip_Increment_Brightness();
        break;
      case IR_CODES_1:
        debug("... <1> recognized");
        switchMode(SNOW);
        break;
      case IR_CODES_2:
        debug("... <2> recognized");
        switchMode(RAINBOW);
        break;
      default:
        debug("Unsupported code");
    }

    irrecv.resume();
  }
}
