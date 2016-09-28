
const int  sensorPin   = A2;
const int  controlPin  =  3;

const int min_time_between_sounds_in_ms =  200;
const int max_time_between_sounds_in_ms = 1000;
const int min_volume_of_switching_sound =  768;

bool control_CurrentState_IsOn =  false;
long lastSoundTimeStamp = 0;

void setup() {

  pinMode(controlPin, OUTPUT);
  switchControlOff();

  Serial.begin(9600);
}

void loop() {

  int sensorValue =  analogRead(sensorPin);

  if (isSwitchingSoundVolume(sensorValue)) {

    long currentTimeInMs = millis();
    long msSinceLastSwitchingSound = currentTimeInMs - lastSoundTimeStamp;

    if ( min_time_between_sounds_in_ms < msSinceLastSwitchingSound && msSinceLastSwitchingSound < max_time_between_sounds_in_ms) {

      toggleControl();
    }

    lastSoundTimeStamp = currentTimeInMs;
  }

  if (sensorValue > 500) {
    Serial.println(sensorValue,  DEC);
  }
}

bool isSwitchingSoundVolume(int soundVolume) {

  return soundVolume > min_volume_of_switching_sound;
}

// ... controller switch functionality

bool isControlOn() {
  return control_CurrentState_IsOn;
}

void switchControlOn() {
  control_CurrentState_IsOn = true;
  digitalWrite(controlPin, HIGH);
}

void switchControlOff() {
  control_CurrentState_IsOn = false;
  digitalWrite(controlPin, LOW);
}

void toggleControl() {

  if (isControlOn()) {
    switchControlOff();
  } else {
    switchControlOn();
  }
}

