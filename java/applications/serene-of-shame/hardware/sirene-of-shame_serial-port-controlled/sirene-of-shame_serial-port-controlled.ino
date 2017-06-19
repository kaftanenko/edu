#include "Arduino.h"
#include "SoftwareSerial.h"
#include "DFRobotDFPlayerMini.h"

#include "Timer.h"

// ... constants

const String COMMAND_GET_STATE = "GET_STATE";

const String COMMAND_SET_STATE_TO_RED = "SET_STATE_TO_RED";
const String COMMAND_SET_STATE_TO_YELLOW = "SET_STATE_TO_YELLOW";
const String COMMAND_SET_STATE_TO_GREENBLUE = "SET_STATE_TO_GREENBLUE";

const String COMMAND_PING = "PING";
const String COMMAND_PING_RESPONSE_SUCCEEDED = "SUCCEEDED";

const String CONFIG_GREETING_MESSAGE = "Wellcome to the \"Sirene Of Shame\"!";
const String CONFIG_COMMANDS_MESSAGE = "I understand following commands: '"
                                       + COMMAND_SET_STATE_TO_RED + "', '"
                                       + COMMAND_SET_STATE_TO_YELLOW + "', '"
                                       + COMMAND_SET_STATE_TO_GREENBLUE + "', '"
                                       + COMMAND_GET_STATE
                                       + "'.";

const int CONFIG_SERIAL_PORT_BAUD_RATE__9600 = 9600;
const int CONFIG_CHANNEL_LIGHTS_TAKT_DURATION_IN_MS__400 = 400;

enum LightState {

  ON,
  OFF
};

enum Channel {

  RED,
  YELLOW,
  GREENBLUE
};

class ChannelConfig {

  public:

    Channel _channel;
    String _channelName;
    int _lightControlPin1;
    int _lightControlPin2;
    long _rememberingTaktInMs;

    ChannelConfig(Channel channel, String channelName, int lightControlPin1, int lightControlPin2, long rememberingTaktInSec);
};

ChannelConfig::ChannelConfig(Channel channel, String channelName, int lightControlPin1, int lightControlPin2, long rememberingTaktInSec) {

  _channel = channel;
  _channelName = channelName;
  _lightControlPin1 = lightControlPin1;
  _lightControlPin2 = lightControlPin2;
  _rememberingTaktInMs = rememberingTaktInSec * 1000;
};

ChannelConfig channelConfigs[] = {

  ChannelConfig(RED, "RED", 2, 3, 180),
  ChannelConfig(YELLOW, "YELLOW", 4, 5, 90),
  ChannelConfig(GREENBLUE, "GREENBLUE", 6, 7, 120)
};

// ... properties

String lastCommand = "";

ChannelConfig *currentChannelConfig = &(channelConfigs[GREENBLUE]);
LightState currentChannel_LightsState = OFF;
Timer currentChannel_LightsState_Timer;
Timer currentChannel_Remember_Timer;

SoftwareSerial mySoftwareSerial(10, 11); // RX, TX
DFRobotDFPlayerMini myDFPlayer;
void printDFPlayerDetails(uint8_t type, int value);

// ... business methods

void setup()
{

  Serial.begin(CONFIG_SERIAL_PORT_BAUD_RATE__9600);

  // ... init light channel

  println_SerialPort_Message(CONFIG_GREETING_MESSAGE);
  println_SerialPort_Message(CONFIG_COMMANDS_MESSAGE);

  initChannel_PinsSet(channelConfigs[RED]);
  initChannel_PinsSet(channelConfigs[YELLOW]);
  initChannel_PinsSet(channelConfigs[GREENBLUE]);

  // ... init sound channel

  mySoftwareSerial.begin(9600);

  println_SerialPort_Message("");
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

    if (lastCommand == COMMAND_GET_STATE) {
      println_SerialPort_Message(currentChannelConfig->_channelName);
    } else if (lastCommand == COMMAND_PING) {
      println_SerialPort_Message(COMMAND_PING_RESPONSE_SUCCEEDED);
    } else if (lastCommand == COMMAND_SET_STATE_TO_RED) {
      doSetState(RED);
    } else if (lastCommand == COMMAND_SET_STATE_TO_YELLOW) {
      doSetState(YELLOW);
    } else if (lastCommand == COMMAND_SET_STATE_TO_GREENBLUE) {
      doSetState(GREENBLUE);
    } else {
      println_SerialPort_Message("Command '" + lastCommand + "' is not supported.");
    }
  }

  if (currentChannel_LightsState_Timer.isOver(CONFIG_CHANNEL_LIGHTS_TAKT_DURATION_IN_MS__400)) {

    currentChannel_LightsState_Timer.reset();
    currentChannel_LightsState = currentChannel_LightsState == ON ? OFF : ON;

    switchChannel_Lights_Off(channelConfigs[RED]);
    switchChannel_Lights_Off(channelConfigs[YELLOW]);
    switchChannel_Lights_Off(channelConfigs[GREENBLUE]);

    switchChannel_Lights_To(*currentChannelConfig, currentChannel_LightsState);
  }

  if (currentChannel_Remember_Timer.isOver(currentChannelConfig->_rememberingTaktInMs)) {

    currentChannel_Remember_Timer.reset();
    println_SerialPort_Message("Remember about current state: " + currentChannelConfig->_channelName + " (each " + currentChannelConfig->_rememberingTaktInMs + " ms)");

    playSoundEffect_On_Remember(currentChannelConfig->_channel);
  }

  delay(1);
}

// ... helper methods

void doSetState(Channel newChannel) {

  if (newChannel != currentChannelConfig->_channel) {

    playSoundEffect_On_ChannelChange(currentChannelConfig->_channel, newChannel);

    currentChannel_LightsState_Timer.reset();
    currentChannel_LightsState = OFF;

    currentChannel_Remember_Timer.reset();

    currentChannelConfig = &(channelConfigs[newChannel]);

    println_SerialPort_Message("Channel changed to: " + currentChannelConfig->_channelName);
  } else {
    println_SerialPort_Message("Channel is still the following: " + currentChannelConfig->_channelName);
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
    delay(2);
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

void initChannel_PinsSet(ChannelConfig channelConfig) {

  pinMode(channelConfig._lightControlPin1, OUTPUT);
  pinMode(channelConfig._lightControlPin2, OUTPUT);
}

void switchChannel_Lights_Off(ChannelConfig channelConfig) {

  switchChannel_Lights_To(channelConfig, OFF);
}

void switchChannel_Lights_To(ChannelConfig channelConfig, LightState toLightState) {

  digitalWrite(channelConfig._lightControlPin1, toLightState == ON ? HIGH : LOW);
  digitalWrite(channelConfig._lightControlPin2, toLightState == ON ? HIGH : LOW);
}

// ...

void playSoundEffect_On_Remember(Channel channel) {

  int folderName = channel + 1;
  playSoundEffect_Any_Within_Folder(folderName);
}

void playSoundEffect_On_ChannelChange(Channel fromChannel, Channel toChannel) {

  int folderName = (fromChannel + 1) * 10 + toChannel + 1;
  playSoundEffect_Any_Within_Folder(folderName);
}

void playSoundEffect_Any_Within_Folder(int folderName) {

  int soundEffectsCount = myDFPlayer.readFileCountsInFolder(folderName);
  int soundEffectToPlay = random(soundEffectsCount - 2) / 2 + 1;

  playSoundEffect_Within_Folder(folderName, soundEffectToPlay);
}

void playSoundEffect_Within_Folder(int folderName, int soundEffectToPlay) {

  myDFPlayer.playFolder(folderName, soundEffectToPlay);

  //print_SerialPort_Message("Playing a sound effect " + soundEffectToPlay);
  //print_SerialPort_Message(" within the folder " + folderName);
  //println_SerialPort_Message(".");
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
