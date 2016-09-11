
static const bool debugMode    = false;

static const int sensorPin     =  3;  // Digital Pin für PIR-Sensor
static const int controllerPin = 13;  // Digital Pin für LED bzw. eine Steuerungseinheit

unsigned int sensorPinState    = LOW; // Wert des sensorPin

void setup() {

  if (debugMode) {
    Serial.begin(9600);
  }

  pinMode(sensorPin, INPUT);
  pinMode(controllerPin, OUTPUT);

  delay(5000); // ... warte ab, bis der Sensor in Betriebsmodus geht.
}


void loop() {

  sensorPinState = digitalRead(sensorPin);

  digitalWrite(controllerPin, sensorPinState);

  if (debugMode) {

    if (sensorPinState == HIGH) {

      Serial.println("Alarm! Irgendwas hat sich kürzlich bewegt!");
    } else {
      Serial.println("... bin im Überwachungsmodus. Nichts auffäliges passiert.");
    }
  }

  delay(300); // ... warte kurz ab, bevor die Sensordaten erneut abgelesen werden.
}
