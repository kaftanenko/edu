/* SevSeg Counter Example
  Edited by Haneef Puttur to match SMA420564 (www.haneefputtur.com)
  Copyright 2014 Dean Reading

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.


  This example demonstrates a very simple use of the SevSeg library with a 4
  digit display. It displays a counter that counts up, showing 10 deci-seconds.
*/

#include "SevSeg.h"

SevSeg sevseg; //Instantiate a seven segment controller object

void setup() {
  byte numDigits = 4;
  byte digitPins[] = {10, 11, 12, 13};
  byte segmentPins[] = {2, 3, 4, 5, 6, 7, 8, 9};

  sevseg.begin(COMMON_CATHODE, numDigits, digitPins, segmentPins);
  // If your display is common anode type, please edit comment above line and uncomment below line
  // sevseg.begin(COMMON_ANODE, numDigits, digitPins, segmentPins);
  sevseg.setBrightness(90);
}

void loop() {
  static unsigned long timer = millis();
  static int deciSeconds = 0;

  if (millis() >= timer) {
    deciSeconds++; // 1000 milliSeconds is equal to 10 deciSecond
    timer += 1000;
    if (deciSeconds == 10000) { // Reset to 0 after counting for 1000 seconds.
      deciSeconds = 0;
    }
    sevseg.setNumber(deciSeconds, 1);
  }

  sevseg.refreshDisplay(); // Must run repeatedly
}

/// END ///
