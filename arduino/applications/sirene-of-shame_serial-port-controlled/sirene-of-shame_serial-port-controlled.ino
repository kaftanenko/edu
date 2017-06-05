#include "Timer.h"

// ... constants

const String COMMAND_GET_STATE = "GET_STATE";
const String COMMAND_SET_STATE_TO_RED = "SET_STATE_TO_RED";
const String COMMAND_SET_STATE_TO_YELLOW = "SET_STATE_TO_YELLOW";
const String COMMAND_SET_STATE_TO_GREENBLUE = "SET_STATE_TO_GREENBLUE";

const String CONFIG_GREETING_MESSAGE = "Wellcome to the \"Sirene Of Shame\"!";
const String CONFIG_COMMANDS_MESSAGE = "I understand following commands: '"
                                       + COMMAND_SET_STATE_TO_RED + "', '"
                                       + COMMAND_SET_STATE_TO_YELLOW + "', '"
                                       + COMMAND_SET_STATE_TO_GREENBLUE + "', '"
                                       + COMMAND_GET_STATE
                                       + "'.";

const int CONFIG_SERIAL_PORT_BAUD_RATE__9600 = 9600;
const int CONFIG_CHANNEL_TAKT_DURATION_IN_MS__400 = 400;

const int redChannel_PinsSet[]       = { 2, 3 };
const int yellowChannel_PinsSet[]    = { 4, 5 };
const int greenBlueChannel_PinsSet[] = { 6, 7 };

// ... properties

String lastCommand = "";

String currentChannelName;
bool currentChannel_Takt;
Timer currentChannel_Takt_Timer;
int currentChannel_PinsSet[] = { 8, 9 };

// ... business methods

void setup()
{

  Serial.begin(CONFIG_SERIAL_PORT_BAUD_RATE__9600);

  println_SerialPort_Message(CONFIG_GREETING_MESSAGE);
  println_SerialPort_Message(CONFIG_COMMANDS_MESSAGE);

  currentChannelName = "GREENBLUE";
  currentChannel_Takt = false;

  initChannel_PinsSet(redChannel_PinsSet);
  initChannel_PinsSet(yellowChannel_PinsSet);
  initChannel_PinsSet(greenBlueChannel_PinsSet);
}

void loop()
{

  if (isAvailable_SerialPort_Message()) {

    lastCommand = read_SerialPort_Message();
    // println_SerialPort_Message("Command received: '" + lastCommand + "'.");

    currentChannel_Takt = false;

    if (lastCommand == COMMAND_GET_STATE) {
      println_SerialPort_Message(currentChannelName);
    } else if (lastCommand == COMMAND_SET_STATE_TO_RED) {
      currentChannelName = "RED";
      copyChannel_PinsSet(redChannel_PinsSet, currentChannel_PinsSet);
    } else if (lastCommand == COMMAND_SET_STATE_TO_YELLOW) {
      currentChannelName = "YELLOW";
      copyChannel_PinsSet(yellowChannel_PinsSet, currentChannel_PinsSet);
    } else if (lastCommand == COMMAND_SET_STATE_TO_GREENBLUE) {
      currentChannelName = "GREENBLUE";
      copyChannel_PinsSet(greenBlueChannel_PinsSet, currentChannel_PinsSet);
    } else {
      println_SerialPort_Message("Command '" + lastCommand + "' is not supported. Controller has been reset.");
    }

  }

  if (currentChannel_Takt_Timer.isOver(CONFIG_CHANNEL_TAKT_DURATION_IN_MS__400)) {

    currentChannel_Takt_Timer.reset();

    currentChannel_Takt = not currentChannel_Takt;

    switchChannel_PinsSet_Off(redChannel_PinsSet);
    switchChannel_PinsSet_Off(yellowChannel_PinsSet);
    switchChannel_PinsSet_Off(greenBlueChannel_PinsSet);

    toggleChannel_PinsSet_State(currentChannel_PinsSet, currentChannel_Takt);
  }

  delay(10);
}

// ... helper methods

bool isAvailable_SerialPort_Message() {

  return Serial.available() > 0;
}

String read_SerialPort_Message() {

  String message = "";
  while (Serial.available() > 0) {
    message += char(Serial.read());
    delay(2);
  }
  return message;
}

void println_SerialPort_Message(String message) {

  Serial.println(message);
}

// ...

void copyChannel_PinsSet(int sourcePinsSet[], int targetPinsSet[]) {

  targetPinsSet[0] = sourcePinsSet[0];
  targetPinsSet[1] = sourcePinsSet[1];
}

void initChannel_PinsSet(int pinsSet[]) {

  pinMode(pinsSet[0], OUTPUT);
  pinMode(pinsSet[1], OUTPUT);
}

void switchChannel_PinsSet_Off(int pinsSet[]) {

  digitalWrite(pinsSet[0], LOW);
  digitalWrite(pinsSet[1], LOW);
}

void toggleChannel_PinsSet_State(int pinsSet[], bool takt) {

  digitalWrite(pinsSet[0], takt ? HIGH : LOW);
  digitalWrite(pinsSet[1], takt ? HIGH : LOW);
}

