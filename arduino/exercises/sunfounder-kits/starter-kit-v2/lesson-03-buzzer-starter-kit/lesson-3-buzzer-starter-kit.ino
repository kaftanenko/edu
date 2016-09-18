/*********************************
  name:buzzer
  function: you should hear the buzzer make sounds.
*************************************/
//Email: support@sunfounder.com
//Website: www.sunfounder.com
/************************************/

int buzzerPin = 4;//the pin of the active buzzer

void setup()
{
  pinMode(buzzerPin, OUTPUT); //initialize the buzzer pin as an output
}

void loop()
{
  unsigned char i;
  while (1)
  {
    //output an frequency
    for (i = 0; i < 80; i++)
    {
      digitalWrite(buzzerPin, HIGH);
      delay(1);//wait for 1ms
      digitalWrite(buzzerPin, LOW);
      delay(1);//wait for 1ms
    }
    //output another frequency
    for (i = 0; i < 100; i++)
    {
      digitalWrite(buzzerPin, HIGH);
      delay(2);//wait for 2ms
      digitalWrite(buzzerPin, LOW);
      delay(2);//wait for 2ms
    }
  }
}
