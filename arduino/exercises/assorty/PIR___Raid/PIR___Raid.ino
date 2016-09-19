/*
   PIR Motion Sensor + Raid Siren Through Speakers
   This Code is distruted under General Public License 3

   Hardware Used:
   1.Mediatek Link It ONE
   2.PIR Motion Sensor
   3.Computer Speaker or portable speakers with audio jack.

   The Code uses Mediatek Link it ONE Audio Library to
   Play raidsiren.mp3 when some is detected by Motion Sensor.
   This is the Best way to keep unwanted guests/theives/Cats/Dogs Away :p

   Author: Ayush Sharma
   Created on: 10/30/2015

*/


//#include <LAudio.h>

const int pirPin = 12;
const int ledPin = 7;
int i = 0;
  

void setup() {

  //LAudio.begin();

  Serial.begin(9600);

  pinMode(pirPin, INPUT);
  pinMode(ledPin, OUTPUT);

  delay(15000);       // delay to make the PIR Stabilized
}

void loop() {

  i++;
  i =  i % 100;
  
  int val = digitalRead(pirPin);
  if (val == HIGH) {
    //LAudio.playFile( storageFlash,(char*)"raidsiren.mp3");   // Playing Raid Siren Audio File
    //LAudio.setVolume(1);                                // Volume (1-6) Set 6 Max.
    digitalWrite(ledPin, HIGH);
    Serial.println(++i);
  } else {
    //LAudio.stop();
    digitalWrite(ledPin, LOW);
    Serial.println("---");
    i = 0;
  }
  delay(100);
}
