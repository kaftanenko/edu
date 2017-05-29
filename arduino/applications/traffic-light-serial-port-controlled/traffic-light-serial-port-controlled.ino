
// ... constants

const String COMMAND_RED = "red";
const String COMMAND_YELLOW = "yellow";
const String COMMAND_GREENBLUE = "greenblue";

const int redChannel_PinsSet[]       = { 2, 3 };
const int yellowChannel_PinsSet[]    = { 4, 5 };
const int greenBlueChannel_PinsSet[] = { 6, 7 };
const int zeroChannel_PinsSet[]      = { 8, 9 };

const int DELAY_BETWEEN_STATE_SWITCHING_IN_MS = 400;

// ... properties

String lastCommand = "";

bool currentChannel_Takt = false;
int currentChannel_PinsSet[] = { 8, 9 };

// ... business methods

void setup()
{
  
  Serial.begin(9600);
  println_SerialPort_Message("Please input one of following commands: '" + COMMAND_RED + "', '" + COMMAND_YELLOW + "', '" + COMMAND_GREENBLUE + "'.");
  
  initChannel_PinsSet(redChannel_PinsSet);
  initChannel_PinsSet(yellowChannel_PinsSet);
  initChannel_PinsSet(greenBlueChannel_PinsSet);
}

void loop()
{

  if (isAvailable_SerialPort_Message()) {
    
    lastCommand = read_SerialPort_Message();
    println_SerialPort_Message("Command received: '" + lastCommand + "'.");

    if (lastCommand == COMMAND_RED) {    
      copyChannel_PinsSet(redChannel_PinsSet, currentChannel_PinsSet);
    } else if (lastCommand == COMMAND_YELLOW) {
      copyChannel_PinsSet(yellowChannel_PinsSet, currentChannel_PinsSet);
    } else if (lastCommand == COMMAND_GREENBLUE) {
      copyChannel_PinsSet(greenBlueChannel_PinsSet, currentChannel_PinsSet);
    } else {
      copyChannel_PinsSet(zeroChannel_PinsSet, currentChannel_PinsSet);
      println_SerialPort_Message("Command '" + lastCommand + "' is not supported.");
    }
  }

  switchChannel_PinsSet_Off(redChannel_PinsSet);
  switchChannel_PinsSet_Off(yellowChannel_PinsSet);
  switchChannel_PinsSet_Off(greenBlueChannel_PinsSet);
  
  currentChannel_Takt = not currentChannel_Takt;
  toggleChannel_PinsSet_State(currentChannel_PinsSet, currentChannel_Takt);
  
  delay(DELAY_BETWEEN_STATE_SWITCHING_IN_MS);
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

// ...

void println_SerialPort_Message(String message) {

  Serial.println(message);
}

void copyChannel_PinsSet(int sourcePinsSet[], int targetPinsSet[]) {

  targetPinsSet[0] = sourcePinsSet[0];
  targetPinsSet[1] = sourcePinsSet[1];
}

void initChannel_PinsSet(int pinsSet[]) {

  pinMode(pinsSet[0],    OUTPUT);
  pinMode(pinsSet[1],    OUTPUT);
}

void switchChannel_PinsSet_Off(int pinsSet[]) {

    digitalWrite(pinsSet[0], LOW);
    digitalWrite(pinsSet[1], LOW);
}

void toggleChannel_PinsSet_State(int pinsSet[], bool takt) {

    digitalWrite(pinsSet[0], takt ? HIGH : LOW);
    digitalWrite(pinsSet[1], takt ? HIGH : LOW);
}

