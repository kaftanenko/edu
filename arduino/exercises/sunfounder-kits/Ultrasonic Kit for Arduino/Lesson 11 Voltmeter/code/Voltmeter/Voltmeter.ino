/***************************************
 * name:Voltmeter
 * function:adjust the potentiometer connected to pin A0, you will see the voltage displayed on the LCD1602 varies accordingly.
 ***************************************/
//Email: support@sunfounder.com
//Website: www.sunfounder.com

#include <LiquidCrystal.h>
/****************************************************/
const int analogIn = A0;//potentiometer attach to A0
LiquidCrystal lcd(4, 6, 10, 11, 12, 13);//lcd(RS,E,D4,D5,D6.D7)
float val = 0;// define the variable as value=0
/****************************************************/
void setup()
{
  Serial.begin(9600);//Initialize the serial serial
  lcd.begin(16, 2);// set the position of the characters on the LCD as Line 2, Column 16
  lcd.print("Voltage Value:");//print "Voltage Value:"
}
/****************************************************/
void loop()
{
  val = analogRead(A0);//Read the value of the potentiometer to val
  val = val/1024*5.0;// Convert the data to the corresponding voltage value in a math way
  Serial.print(val);//Print the number of val on the serial monitor
  Serial.print("V"); // print the unit as V, short for voltage on the serial monitor
  lcd.setCursor(6,1);//Place the cursor at Line 1, Column 6. From here the characters are to be displayed
  lcd.print(val);//Print the number of val on the LCD
  lcd.print("V");//Then print the unit as V, short for voltage on the LCD
  delay(200); //Wait for 200ms
}

