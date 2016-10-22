
int pos = 0;

void setup() {
  pinMode(9, OUTPUT);
}

void loop() {
  for (pos = 0; pos <= 255; pos+=5)
  {

    analogWrite(9, pos);
    delay(25);
  }
  for (pos = 255; pos >= 0; pos-=5)
  {
    analogWrite(9, pos);
    delay(25);
  }
}
