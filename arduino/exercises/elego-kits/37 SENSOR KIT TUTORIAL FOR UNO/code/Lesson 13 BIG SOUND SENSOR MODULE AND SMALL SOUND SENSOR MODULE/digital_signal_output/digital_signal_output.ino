
int Led = 13; //define LED port
int buttonpin = 3; //define switch port

int  val; //define digital variable val

void setup() {

  pinMode(Led, OUTPUT); //define LED as a output port
  pinMode(buttonpin, INPUT); //define switch as a output port
}

void loop() {

  val = digitalRead(buttonpin);

  if (val == HIGH) {
    digitalWrite(Led, HIGH);
  } else {
    digitalWrite(Led, LOW);
  }
}

