
const bool isDebug = true;

const int  sensorPin   = A2;
const int  controlPin  =  3;

const int min_time_between_sounds_in_ms =  300;
const int max_time_between_sounds_in_ms = 1000;
const int min_volume_of_switching_sound =  768;

bool control_CurrentState_IsOn =  false;

long lastSoundTimeStamp = 0;
long lastSilenceTimeStamp = 0;

void setup() {

  pinMode(controlPin, OUTPUT);
  switchControlOff();

  if (isDebug) {
    Serial.begin(9600);
  }
}

void loop() {

  int sensorValue =  analogRead(sensorPin);

  long currentTimeInMs = millis();

  if (isSwitchingSoundVolume(sensorValue)) {

    long msSinceLastSwitchingSound = currentTimeInMs - lastSoundTimeStamp;

    int s1 = min_time_between_sounds_in_ms < msSinceLastSwitchingSound && msSinceLastSwitchingSound < max_time_between_sounds_in_ms;
    if (s1) {

      toggleControl();

      lastSoundTimeStamp = 0;
      lastSilenceTimeStamp = 0;
    } else {

      lastSoundTimeStamp = currentTimeInMs;
    }
  } else {
    lastSilenceTimeStamp = currentTimeInMs;
  }

  if (isDebug) {
    Serial.println(sensorValue,  DEC);
  }

  delay(50);
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

