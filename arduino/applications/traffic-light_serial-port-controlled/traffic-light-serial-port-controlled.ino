
const int greenPin = 11;
const int yellowPin = 12;
const int redPin = 13;

String comdata = "";
int lastLength = 0;

void setup()
{
  pinMode(greenPin, OUTPUT); //initialize the greenPin as output
  pinMode(yellowPin, OUTPUT);  //initialize the yellowPin as output
  pinMode(redPin, OUTPUT);  //initialize the redPin as output
  Serial.begin(9600);  // start serial port at 9600 bps:
  Serial.print("Please input any color of LED:");  //print message on serial monitor
}

void loop()
{

  if (Serial.available() > 0) // if we get a valid byte, read analog ins:
  {
    comdata = "";
    while (Serial.available() > 0)
    {
      comdata += char(Serial.read());
      delay(2);
    }
    Serial.println(comdata);
  }

  if (comdata == "red")
  {
    Serial.println("command: red");
    digitalWrite(redPin, HIGH);//turn the red led on
    digitalWrite(greenPin, LOW);//turn the green led off
    digitalWrite(yellowPin, LOW);//turn the yellow led off
  }
  else if (comdata == "yellow")
  {
    Serial.println("command: yellow");
    digitalWrite(redPin, LOW);//turn the red led off
    digitalWrite(greenPin, LOW);//turn the green led off
    digitalWrite(yellowPin, HIGH);//turn the yellow led on
  }
  else if (comdata == "green")
  {
    Serial.println("command: green");
    digitalWrite(redPin, LOW);//turn the red led off
    digitalWrite(greenPin, HIGH);//turn the green led on
    digitalWrite(yellowPin, LOW);//turn the yellow led off
  }
  else
  {
    Serial.println("command: reset");
    digitalWrite(redPin, LOW);//turn the red led off
    digitalWrite(greenPin, LOW);//turn the green led off
    digitalWrite(yellowPin, LOW);//turn the yellow led off
  }
}
