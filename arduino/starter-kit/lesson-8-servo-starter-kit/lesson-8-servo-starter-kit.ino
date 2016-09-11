/***********************************************
  name:Servo
  function:you can see the servo motor rotate 90 degrees (rotate once every 15 degrees).
  And then rotate in the opposite direction.
************************************************/
//Email: support@sunfounder.com
//Website: www.sunfounder.com

#include <Servo.h>

/************************************************/

Servo myservo;//create servo object to control a servo
int delayInMs = 1000;

/************************************************/
void setup()
{
  myservo.attach(9);//attachs the servo on pin 9 to servo object
  myservo.write(0);//back to 0 degrees
  delay(delayInMs);//wait for a second
}

/*************************************************/
void loop()
{
  myservo.write(30);//goes to 30 degrees
  delay(delayInMs);//wait for a second.33
  myservo.write(60);//goes to 60 degrees
  delay(delayInMs);//wait for a second.33
  myservo.write(90);//goes to 15 degrees
  delay(delayInMs);//wait for a second
  myservo.write(120);//goes to 15 degrees
  delay(delayInMs);//wait for a second
  myservo.write(150);//goes to 15 degrees
  delay(delayInMs);//wait for a second
  myservo.write(180);//goes to 15 degrees
  delay(delayInMs);//wait for a second
  myservo.write(150);//goes to 15 degrees
  delay(delayInMs);//wait for a second
  myservo.write(120);//goes to 15 degrees
  delay(delayInMs);//wait for a second
  myservo.write(90);//goes to 90 degrees
  delay(delayInMs);//wait for a second
  myservo.write(60);//back to 60 degrees
  delay(delayInMs);//wait for a second.33
  myservo.write(30);//back to 30 degrees
  delay(delayInMs);//wait for a second.33
  myservo.write(0);//back to 0 degrees
  delay(delayInMs);//wait for a second
}
/**************************************************/
