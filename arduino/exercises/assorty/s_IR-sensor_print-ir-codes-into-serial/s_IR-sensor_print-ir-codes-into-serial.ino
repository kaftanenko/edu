/*
  IRremote: IRrecvDemo - demonstrates
  receiving IR codes with IRrecv
  An IR detector/demodulator must be
  connected to the input RECV_PIN.
  Version 0.1 July, 2009
  Copyright 2009 Ken Shirriff
  http://arcfn.com
*/
//Infromationen über das ursprüngliche Programm „IrrecvDemo“.

int TKD2 = 11;
#include <IRremote.h>   // Das Programm greift an dieser Stelle auf eine „Library“ zurück. Das erleichtert einem viel Arbeit. Denn das Infrarotlicht wird mit einem Code verschlüsselt gesendet. Um diesen Code selber auszulesen und in passende Werte umzuwandeln, wären sehr viele Zeilen Code erforderlich.

int RECV_PIN = 11;  //  Der Kontakt der am Infrarotsensor die Daten ausgibt, wird mit Pin 11 des Arduinoboards verbunden.

IRrecv irrecv(RECV_PIN);   // An dieser Stelle wird ein Objekt definiert, dass den Infrarotsensor an Pin 11 ausliest.

decode_results results;  // Dieser Befehl sorgt dafür, dass die Daten, die per Infrarot eingelesen werden unter „results“ abgespeichert werden.

void setup()
{

  Serial.begin(9600);    //Im Setup wird die Serielle Verbindung gestartet, damit man sich die Empfangenen Daten der Fernbedienung per seriellen Monitor ansehen kann.

  pinMode (13, OUTPUT);

  irrecv.enableIRIn();   //Dieser Befehl initialisiert den Infrarotempfänger.
}

void loop()

{ //Der loop-Teil fällt durch den Rückgriff auf die „library“ sehr kurz aus.

  if (irrecv.decode(&results)) {    //Wenn Daten empfangen wurden,

    Serial.println(results.value, DEC); //werden sie als Dezimalzahl (DEC) an den Serial-Monitor ausgegeben.

    irrecv.resume();  //Der nächste Wert soll vom IR-Empfänger eingelesen werden
  }
}
