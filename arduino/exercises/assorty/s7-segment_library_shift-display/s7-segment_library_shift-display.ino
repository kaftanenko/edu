#include <ShiftDisplay.h>

byte sectionValues[] = { 0xF };
ShiftDisplay display(D1, D2, D0, COMMON_CATHODE, 1);

void setup() {
  
  // ...
}

void loop() {

  display.set("55"); // store "GO"
  display.show(); // show stored "GO" while in loop
}
