
static const bool debugMode    = false;

static const int sensorPin     =  3;  // digital pin for PIR-Sensor
static const int controllerPin = 13;  // digital pin for LED or a controller

unsigned int sensorPinState    = LOW; // last read value of the sensorPin

void setup() {

  if (debugMode) {
    Serial.begin(9600);
  }

  pinMode(sensorPin, INPUT);
  pinMode(controllerPin, OUTPUT);

  delay(15000); // ... wait a while till sensor is warmed up and get into working mode.
}


void loop() {

  sensorPinState = digitalRead(sensorPin);

  digitalWrite(controllerPin, sensorPinState);

  if (debugMode) {

    if (sensorPinState == HIGH) {

      Serial.println("Alarm! Somthing has been moved ahead me!");
    } else {
      Serial.println("... i'm in the watching mode. Nothing has been happened since a while.");
    }
  }

  delay(300); // ... wait a while before the next reading of the new sensor value.
}
