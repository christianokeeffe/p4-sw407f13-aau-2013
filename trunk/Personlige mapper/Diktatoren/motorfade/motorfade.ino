
const int OutputPin = 9;
const int Hop = 5;
boolean fremad = true;
boolean value = 0;

void setup() {
  // put your setup code here, to run once:
  pinMode(OutputPin, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly: 
  if(fremad)
  {
   if(value + Hop >= 255)
    {
      fremad = false;
    }
    else
    {
    value = value + Hop; 
    }
  }
  else
  {
    if(value - Hop <= 0)
    {
      fremad = true;
    }
    else
    {
     value = value - Hop; 
   }
  }
  analogWrite(OutputPin, value);
  delay(10);
}
