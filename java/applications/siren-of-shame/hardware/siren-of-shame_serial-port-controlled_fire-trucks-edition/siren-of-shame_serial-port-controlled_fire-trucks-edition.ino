#include "Arduino.h"
#include "SoftwareSerial.h"
#include "DFRobotDFPlayerMini.h"

#include "Timer.h"

// ... constants

const String COMMAND_GET_CURRENT_ALARM_LEVEL = "GET_CURRENT_ALARM_LEVEL";

const String COMMAND_SET_ALARM_LEVEL_TO_RED = "SET_ALARM_LEVEL_TO RED";
const String COMMAND_SET_ALARM_LEVEL_TO_RED_EXPECTING_UPDATE = "SET_ALARM_LEVEL_TO RED_EXPECTING_UPDATE";
const String COMMAND_SET_ALARM_LEVEL_TO_YELLOW = "SET_ALARM_LEVEL_TO YELLOW";
const String COMMAND_SET_ALARM_LEVEL_TO_YELLOW_EXPECTING_UPDATE = "SET_ALARM_LEVEL_TO YELLOW_EXPECTING_UPDATE";
const String COMMAND_SET_ALARM_LEVEL_TO_GREENBLUE = "SET_ALARM_LEVEL_TO GREENBLUE";
const String COMMAND_SET_ALARM_LEVEL_TO_GREENBLUE_EXPECTING_UPDATE = "SET_ALARM_LEVEL_TO GREENBLUE_EXPECTING_UPDATE";

const String COMMAND_PING = "PING";
const String COMMAND_PING_RESPONSE_SUCCEEDED = "SUCCEEDED";

const String CONFIG_GREETING_MESSAGE = "Wellcome to the \"Siren Of Shame\"!";
const String CONFIG_COMMANDS_MESSAGE = "I understand following commands: '"
                                       + COMMAND_SET_ALARM_LEVEL_TO_RED + "', '"
                                       + COMMAND_SET_ALARM_LEVEL_TO_RED_EXPECTING_UPDATE + "', '"
                                       + COMMAND_SET_ALARM_LEVEL_TO_YELLOW + "', '"
                                       + COMMAND_SET_ALARM_LEVEL_TO_YELLOW_EXPECTING_UPDATE + "', '"
                                       + COMMAND_SET_ALARM_LEVEL_TO_GREENBLUE + "', '"
                                       + COMMAND_SET_ALARM_LEVEL_TO_GREENBLUE_EXPECTING_UPDATE + "', '"
                                       + COMMAND_GET_CURRENT_ALARM_LEVEL
                                       + "'.";

const int CONFIG_SERIAL_PORT_BAUD_RATE__9600 = 9600;
const int CONFIG_CHANNEL_LIGHTS_TAKT_DURATION_IN_MS__600 = 600;

enum AlarmLevel {

  RED,
  YELLOW,
  GREENBLUE,

  RED_EXPECTING_UPDATE,
  YELLOW_EXPECTING_UPDATE,
  GREENBLUE_EXPECTING_UPDATE,
};

enum LightState {

  LIGHT_ON,
  LIGHT_OFF,
};

enum LightEffectsState {

  LIGHT_EFFECTS_ON,
  LIGHT_EFFECTS_OFF,
};

enum SoundEffectsState {

  SOUND_EFFECTS_ON,
  SOUND_EFFECTS_OFF,
};

// ... configuration

class ControlPinState {

  public:

    int _number;
    int _state;

    ControlPinState(int pinNumber, int pinState);
};

ControlPinState::ControlPinState(int pinNumber, int pinState) {

  _number = pinNumber;
  _state = pinState;
};

// ...

class AlarmLevelConfig {

  public:

    AlarmLevel _alarmLevel;
    String _alarmLevelName;

    ControlPinState *_controlPinsState;

    LightEffectsState _lightEffectsState;

    long _rememberingTaktInMs;
    SoundEffectsState _rememberingSoundEffectsState;

    AlarmLevelConfig(AlarmLevel alarmLevel, String alarmLevelName, ControlPinState *controlPinsState, LightEffectsState lightEffectsState, SoundEffectsState rememberingSoundEffectsState, long rememberingTaktInSec);
};

AlarmLevelConfig::AlarmLevelConfig(AlarmLevel alarmLevel, String alarmLevelName, ControlPinState *controlPinsState, LightEffectsState lightEffectsState, SoundEffectsState rememberingSoundEffectsState, long rememberingTaktInSec) {

  _alarmLevel = alarmLevel;
  _alarmLevelName = alarmLevelName;

  _controlPinsState = controlPinsState;

  _lightEffectsState = lightEffectsState;

  _rememberingTaktInMs = rememberingTaktInSec * 1000;
  _rememberingSoundEffectsState = rememberingSoundEffectsState;
};

const int CONTROL_PINS[] = { 2, 3, 4, 5, 6, 7 };
const int CONTROL_PINS_COUNT = 6;

const ControlPinState controlPinsState_For_Red[] = {

  ControlPinState(2, LOW),
  ControlPinState(3, LOW),
  ControlPinState(4, LOW),
  ControlPinState(5, LOW),
  ControlPinState(6, HIGH),
  ControlPinState(7, HIGH),
};

const ControlPinState controlPinsState_For_Yellow[] = {

  ControlPinState(2, LOW),
  ControlPinState(3, LOW),
  ControlPinState(4, HIGH),
  ControlPinState(5, HIGH),
  ControlPinState(6, LOW),
  ControlPinState(7, LOW),
};

const ControlPinState controlPinsState_For_GreenBlue[] = {

  ControlPinState(2, HIGH),
  ControlPinState(3, HIGH),
  ControlPinState(4, LOW),
  ControlPinState(5, LOW),
  ControlPinState(6, LOW),
  ControlPinState(7, LOW),
};

AlarmLevelConfig alarmLevelConfigs[] = {

  AlarmLevelConfig(RED, "RED", controlPinsState_For_Red, LIGHT_EFFECTS_ON, SOUND_EFFECTS_ON, 120),
  AlarmLevelConfig(YELLOW, "YELLOW", controlPinsState_For_Yellow, LIGHT_EFFECTS_ON, SOUND_EFFECTS_ON, 120),
  AlarmLevelConfig(GREENBLUE, "GREENBLUE", controlPinsState_For_GreenBlue, LIGHT_EFFECTS_OFF, SOUND_EFFECTS_OFF, 180),

  AlarmLevelConfig(RED_EXPECTING_UPDATE, "RED_EXPECTING_UPDATE", controlPinsState_For_Red, LIGHT_EFFECTS_ON, SOUND_EFFECTS_OFF, 120),
  AlarmLevelConfig(YELLOW_EXPECTING_UPDATE, "YELLOW_EXPECTING_UPDATE", controlPinsState_For_Yellow, LIGHT_EFFECTS_ON, SOUND_EFFECTS_OFF, 120),
  AlarmLevelConfig(GREENBLUE_EXPECTING_UPDATE, "GREENBLUE_EXPECTING_UPDATE", controlPinsState_For_GreenBlue, LIGHT_EFFECTS_OFF, SOUND_EFFECTS_ON, 180),
};

// ... properties

String lastCommand = "";

AlarmLevelConfig *currentAlarmLevelConfig = &(alarmLevelConfigs[GREENBLUE]);
LightState currentAlarmLevel_LightsState = LIGHT_OFF;
Timer currentAlarmLevel_LightsState_Timer;
Timer currentAlarmLevel_Remembering_Timer;

SoftwareSerial mySoftwareSerial(10, 11); // RX, TX
DFRobotDFPlayerMini myDFPlayer;
void printDFPlayerDetails(uint8_t type, int value);

// ... business methods

void setup()
{

  Serial.begin(CONFIG_SERIAL_PORT_BAUD_RATE__9600);

  // ... init light alarmLevel

  println_SerialPort_Message(CONFIG_GREETING_MESSAGE);
  println_SerialPort_Message(CONFIG_COMMANDS_MESSAGE);

  initAlarmLevel_ControlPinsSet();

  // ... init sound alarmLevel

  mySoftwareSerial.begin(9600);

  // println_SerialPort_Message("");
  // println_SerialPort_Message("DFPlayer initializing ... (May take 3~5 seconds)");

  if (!myDFPlayer.begin(mySoftwareSerial)) { // Use softwareSerial to communicate with mp3.
    println_SerialPort_Message("Unable to begin:");
    println_SerialPort_Message("1.Please recheck the connection!");
    println_SerialPort_Message("2.Please insert the SD card!");
    // while (true);
  }
  // println_SerialPort_Message("DFPlayer Mini is online.");

  myDFPlayer.volume(30);  // ... 0 to 30
  // playSoundEffect_Any_Within_Folder(7);
}

void loop()
{

  if (isAvailable_SerialPort_Message()) {

    lastCommand = read_SerialPort_Message();
    // println_SerialPort_Message("Command received: '" + lastCommand + "'.");

    if (lastCommand == COMMAND_GET_CURRENT_ALARM_LEVEL) {
      println_SerialPort_Message(currentAlarmLevelConfig->_alarmLevelName);
    } else if (lastCommand == COMMAND_PING) {
      println_SerialPort_Message(COMMAND_PING_RESPONSE_SUCCEEDED);
    } else if (lastCommand == COMMAND_SET_ALARM_LEVEL_TO_RED) {
      doSetState(RED);
    } else if (lastCommand == COMMAND_SET_ALARM_LEVEL_TO_YELLOW) {
      doSetState(YELLOW);
    } else if (lastCommand == COMMAND_SET_ALARM_LEVEL_TO_GREENBLUE) {
      doSetState(GREENBLUE);
    } else if (lastCommand == COMMAND_SET_ALARM_LEVEL_TO_RED_EXPECTING_UPDATE) {
      doSetState(RED_EXPECTING_UPDATE);
    } else if (lastCommand == COMMAND_SET_ALARM_LEVEL_TO_YELLOW_EXPECTING_UPDATE) {
      doSetState(YELLOW_EXPECTING_UPDATE);
    } else if (lastCommand == COMMAND_SET_ALARM_LEVEL_TO_GREENBLUE_EXPECTING_UPDATE) {
      doSetState(GREENBLUE_EXPECTING_UPDATE);
    } else {
      // println_SerialPort_Message("Command '" + lastCommand + "' is not supported.");
    }
  }

  if (currentAlarmLevel_LightsState_Timer.isOver(CONFIG_CHANNEL_LIGHTS_TAKT_DURATION_IN_MS__600)) {

    currentAlarmLevel_LightsState_Timer.reset();
    
    if (currentAlarmLevelConfig->_lightEffectsState == LIGHT_EFFECTS_OFF) {
      currentAlarmLevel_LightsState = LIGHT_ON;
    } else {
      currentAlarmLevel_LightsState = currentAlarmLevel_LightsState == LIGHT_ON ? LIGHT_OFF : LIGHT_ON;
    }

    for (int i = 0; i < CONTROL_PINS_COUNT; i++) {

      int controlPinNumber = currentAlarmLevelConfig->_controlPinsState[i]._number;
      int controlPinState = currentAlarmLevelConfig->_controlPinsState[i]._state;

      if (controlPinState == LOW) {
        digitalWrite(controlPinNumber, LOW);
      } else {
        digitalWrite(controlPinNumber, currentAlarmLevel_LightsState == LIGHT_ON ? HIGH : LOW);
      }
    }
  }

  if (currentAlarmLevel_Remembering_Timer.isOver(currentAlarmLevelConfig->_rememberingTaktInMs)) {

    currentAlarmLevel_Remembering_Timer.reset();
    // println_SerialPort_Message("Remember about current state: " + currentAlarmLevelConfig->_alarmLevelName + " (each " + currentAlarmLevelConfig->_rememberingTaktInMs + " ms)");

    if (currentAlarmLevelConfig->_rememberingSoundEffectsState == SOUND_EFFECTS_ON) {
      playSoundEffect_On_RememberAbout(currentAlarmLevelConfig->_alarmLevel);
    }
  }

  delay(1);
}

// ... helper methods

void doSetState(AlarmLevel newAlarmLevel) {

  if (newAlarmLevel != currentAlarmLevelConfig->_alarmLevel) {

    playSoundEffect_On_ChangeTo(currentAlarmLevelConfig->_alarmLevel, newAlarmLevel);

    currentAlarmLevelConfig = &(alarmLevelConfigs[newAlarmLevel]);

    currentAlarmLevel_LightsState_Timer.reset();
    currentAlarmLevel_LightsState = LIGHT_OFF;

    currentAlarmLevel_Remembering_Timer.reset();

    // println_SerialPort_Message("AlarmLevel changed to: " + currentAlarmLevelConfig->_alarmLevelName);
  } else {
    // println_SerialPort_Message("AlarmLevel is still the following: " + currentAlarmLevelConfig->_alarmLevelName);
  }
}

// ...

bool isAvailable_SerialPort_Message() {

  return Serial.available() > 0;
}

String read_SerialPort_Message() {

  String message = "";
  while (Serial.available() > 0) {
    message += char(Serial.read());
    delay(10);
  }
  return message;
}

void print_SerialPort_Message(String message) {

  Serial.print(message);
}

void println_SerialPort_Message(String message) {

  Serial.println(message);
  Serial.flush();
}

// ...

void initAlarmLevel_ControlPinsSet() {

  for (int i = 0; i < CONTROL_PINS_COUNT; i++) {

    int controlPinNumber = CONTROL_PINS[i];
    pinMode(controlPinNumber, OUTPUT);
  }
}

// ...

int getFolderIndexFor(AlarmLevel alarmLevel) {

  int folderName = alarmLevel % 3 + 1;
  return folderName;
}

void playSoundEffect_On_ChangeTo(AlarmLevel fromAlarmLevel, AlarmLevel toAlarmLevel) {

  int folderName = getFolderIndexFor(fromAlarmLevel) * 10 + getFolderIndexFor(toAlarmLevel);
  playSoundEffect_Any_Within_Folder(folderName);
}

void playSoundEffect_On_RememberAbout(AlarmLevel alarmLevel) {

  int folderName = getFolderIndexFor(alarmLevel);
  playSoundEffect_Any_Within_Folder(folderName);
}

void playSoundEffect_Any_Within_Folder(int folderName) {

  int soundEffectsCount = myDFPlayer.readFileCountsInFolder(folderName);

  int soundEffectToPlay;
  if (soundEffectsCount <= 2) {
    soundEffectToPlay = 1;
  } else {
    soundEffectToPlay = random(soundEffectsCount - 2) / 2 + 1;
  }

  playSoundEffect_Within_Folder(folderName, soundEffectToPlay);
}

void playSoundEffect_Within_Folder(int folderName, int soundEffectToPlay) {

  myDFPlayer.playFolder(folderName, soundEffectToPlay);
}

void printDFPlayerDetails(uint8_t type, int value) {

  switch (type) {
    case TimeOut:
      println_SerialPort_Message(F("Time Out!"));
      break;
    case WrongStack:
      println_SerialPort_Message(F("Stack Wrong!"));
      break;
    case DFPlayerCardInserted:
      println_SerialPort_Message(F("Card Inserted!"));
      break;
    case DFPlayerCardRemoved:
      println_SerialPort_Message(F("Card Removed!"));
      break;
    case DFPlayerCardOnline:
      println_SerialPort_Message(F("Card Online!"));
      break;
    case DFPlayerPlayFinished:
      print_SerialPort_Message(F("Number:"));
      print_SerialPort_Message("" + value);
      println_SerialPort_Message(F(" Play Finished!"));
      break;
    case DFPlayerError:
      print_SerialPort_Message(F("DFPlayerError:"));
      switch (value) {
        case Busy:
          println_SerialPort_Message(F("Card not found"));
          break;
        case Sleeping:
          println_SerialPort_Message(F("Sleeping"));
          break;
        case SerialWrongStack:
          println_SerialPort_Message(F("Get Wrong Stack"));
          break;
        case CheckSumNotMatch:
          println_SerialPort_Message(F("Check Sum Not Match"));
          break;
        case FileIndexOut:
          println_SerialPort_Message(F("File Index Out of Bound"));
          break;
        case FileMismatch:
          println_SerialPort_Message(F("Cannot Find File"));
          break;
        case Advertise:
          println_SerialPort_Message(F("In Advertise"));
          break;
        default:
          break;
      }
      break;
    default:
      break;
  }
}
