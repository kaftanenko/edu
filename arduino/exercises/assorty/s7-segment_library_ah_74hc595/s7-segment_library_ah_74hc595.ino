/********************************************************
** Download from:                                      **
** http://www.alhin.de/arduino/index.php?n=53          **
********************************************************/

#include <AH_74HC595.h>

#define SER_Pin 4   //Serial data input
#define RCLK_Pin 3  //Register clock
#define SRCLK_Pin 2 //Shift register clock

//Initialisation
AH_74HC595 shifter(SER_Pin, RCLK_Pin, SRCLK_Pin); 

void setup(){
 //shifter.clear();  
 //delay(500);
 for (int i=0;i<8;i++){          //set Pins 
   shifter.setPin(i,HIGH);
   delay(500);
 }
  
 delay(1000);
 shifter.clear();
 shifter.setValue(B10101010);
 delay(1000);
 shifter.setValue(B11110000);
 delay(1000); 
 shifter.setValue(B00001111);
 delay(1000); 
 shifter.clear(); 
 delay(500);
 shifter.shift(1);
 delay(500);
 for (int i=0;i<8;i++){
   shifter.shift(0); 
   delay(500); 
 } 
}

void loop(){
 shifter.shift(1);   //shift one time, add 1
 delay(500);
 shifter.shift(0);   //shift one time, add 0
 delay(500); 
}


