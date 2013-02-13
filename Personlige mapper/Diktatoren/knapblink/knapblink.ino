/*
  DigitalReadSerial
 Reads a digital input on pin 2, prints the result to the serial monitor 
 
 This example code is in the public domain.
 */

void setup() {
  pinMode(2, INPUT);
  pinMode(7, OUTPUT);
}

void loop() {
  int sensorValue = digitalRead(2);
  if(sensorValue == 1){
	digitalWrite(7,HIGH);
	delay(500);
	digitalWrite(7,LOW);
	delay(500);
  }
}
