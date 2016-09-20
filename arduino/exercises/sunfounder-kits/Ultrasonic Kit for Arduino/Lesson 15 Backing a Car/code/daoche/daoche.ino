/***************************************
name:Backing a Car
function:if the distance between the ultrasonic and the obstacle is greater than 5cm and less than 15cm, 
the buzzer will make sounds in a low frequency;
if the distance is less than 5cm, the buzzer will alarm and make sounds in a high frequency.
At the same time, the current distance between the ultrasonic and the obstacle will display on LCD1602.
**************************************/
//Email:support@sunfounder.com
//Website:www.sunfounder.com

#include<LiquidCrystal.h>  

LiquidCrystal lcd(7, 8, 9, 10, 11, 12);//initialize the pin of LCD
const int TrigPin = 2;//Trig attach to pin2
const int EchoPin = 3;//Echo attach to pin3
const int buzzer = 6;//buzzer attach to pin6
float cm;

void setup()
{
   Serial.begin(9600); //Sets the data rate in bits per second (baud) for serial data transmission
   //set the below pins as OUTPUT
   pinMode(TrigPin,OUTPUT);
   pinMode(EchoPin,INPUT);
   pinMode(buzzer,OUTPUT);
   lcd.begin(16,2); //Initializes the interface to the LCD screen 
   lcd.print("Reversing radar"); //display "Reversing radar"
   delay(2000);  //delay 2s
   lcd.clear();//clear lcd
}

void loop()//
{
  //set the frequency the pin Trig sends out pulses
  digitalWrite(TrigPin,LOW);
  delayMicroseconds(2);
  digitalWrite(TrigPin,HIGH);
  delayMicroseconds(10);
  digitalWrite(TrigPin,LOW);
  	
  cm = pulseIn(EchoPin,HIGH)/58.0;// read the pulses at pin Echo, and record the time between the pulse becoming high and then turning into low, which is the length of a pulse. Then convert the length into cm  
  cm = (int(cm * 100.0))/100.0; //turn the distance converted from time into a number showing two decimal places
  if(cm < 0)//If the distance is less than 0
  {
    cm = 0;
  }
  lcd.setCursor(0,0);  
  lcd.print("Distance:");//show the character "Distance:" on the LCD
  lcd.setCursor(0,1);  //Place the cursor at Line 1, Column 0, which means the second line and first column
  lcd.print("                ");//Add 16 spaces to make sure the remaining characters in Line 1, if any, are cleared
  lcd.setCursor(3,1);  // put the cursor at Line 1, Column 3.
  lcd.print(cm);  //Display the distance on the LCD
  lcd.setCursor(9,1);//put the cursor at Line 1, Column 9
  lcd.print("cm");//Display the character "cm" which is the unit
  delay(100);//delay 100ms
 
  if(cm < 15 & cm >= 5)//If the distance is 5 - 15 cm,buzzer beeping at low frequency
  {
    digitalWrite(buzzer, HIGH);
    delay(100);
    digitalWrite(buzzer, LOW);
  }
  else if(cm < 5 & cm >= 0)// If the distance is between 0 and 5 cm,buzzer  keep beeping ceaselessly
  {
    digitalWrite(buzzer, HIGH);
  }
  else      //If the distance is larger than 15cm, 
  {
    digitalWrite(buzzer, HIGH);
    delay(100);
    digitalWrite(buzzer, LOW);
     delay(1000);
  }
}
