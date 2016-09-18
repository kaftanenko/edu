/************************************
  name:Photoresistor
  function: if you shine the photoresistor with a certain light intensity, you will see several LEDs light up.
  If you increase the light intensity, you will see more LEDs light up.
  When you place it in dark environment, all the LEDs will go out.
*********************************************/
//Email: support@sunfounder.com
//Website: www.sunfounder.com

const int NbrLEDs = 8;//8 leds
const int ledPins[] = {5};//8 leds attach to pin 5-12 respectively
const int photocellPin = A0; //photoresistor attach to A0

int sensorValue = 0; // value read from the sensor
int ledLevel = 0; // sensor value converted into LED 'bars'

void setup()
{
  for (int led = 0; led < NbrLEDs; led++)
  {
    pinMode(ledPins[0], OUTPUT);// make all the LED pins outputs
  }
  Serial.begin(9600);
}

void loop()
{
  sensorValue = analogRead(photocellPin); //read the value of A0
  ledLevel = map(sensorValue, 300, 1023, 0, NbrLEDs); // map to the number of LEDs
  int ledValue = map(sensorValue, 0, 900, 0, 255);
  ledValue = (ledValue / 32 + 1) * 32 - 1;
  Serial.print("SEN: ");
  Serial.println(sensorValue);
  Serial.print("LED: ");
  Serial.println(ledValue);
  Serial.print("NUM: ");
  Serial.println(ledLevel);
  analogWrite(ledPins[0], ledValue);
  delay(100);
  for (int led = 0; led < NbrLEDs; led++)//
  {
    if (led < ledLevel ) //When led is smaller than ledLevel, run the following code.
    {
      //digitalWrite(ledPins[led], HIGH); // turn on pins less than the level
    }
    else
    {
      //digitalWrite(ledPins[led], LOW); // turn off pins higher than
    }
  }
}
