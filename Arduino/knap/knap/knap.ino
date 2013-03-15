const int buttonPin1 = 2;     // the number of the pushbutton pin
const int buttonPin2 = 3; 
const int buttonPinEnter = 4; 

// variables will change:
int buttonState1 = 0;         // variable for reading the pushbutton status
int buttonState2 = 0;
int buttonStateEnter = 0;
int Msg[15];
int plads = 0;

void setup() {
  // initialize the pushbutton pin as an input:
  pinMode(buttonPin1, INPUT);     
  pinMode(buttonPin2, INPUT);    
  pinMode(buttonPinEnter, INPUT);    
}

void SaveInArray(int input){
  if(plads < 16){
  Msg[plads] = input;
  plads = plads + 1;}
  else{
   //fejl 
  }
}

void WriteToRFID(){ 
    //write to tag
  rfid.write((uint8_t)255); //Command header
  rfid.write((uint8_t)0); //Reserved
  rfid.write((uint8_t)6); 
  rfid.write((uint8_t)138); //Write value block command 
  rfid.write((uint8_t)1); //Number of block to write, change this to write to another block
  
  int i = 0;
  for (i=0;i<16;i++){
    rfid.write((uint8_t)Msg[i]);
    Msg[i] = 0;
  }
  plads = 0;
}

void loop(){
  // read the state of the pushbutton value:
  buttonState1 = digitalRead(buttonPin1);
  buttonState2 = digitalRead(buttonPin2);
  buttonStateEnter = digitalRead(buttonPinEnter);

  // check if the pushbutton is pressed.
  // if it is, the buttonState is HIGH:
  if (buttonState1 == HIGH && buttonState2 == HIGH) {     
    //fejl
  } 
  else if (buttonState1 == HIGH){
   SaveInArray(1);  
 }else if (buttonState2 == HIGH){
   SaveInArray(2);  
 }else if (buttonStateEnter == HIGH){
   SaveInArray(1);  
 }
}

