/*
  RFID Eval 13.56MHz Shield example sketch with Value Block Read/Writev10
  
    Aaron Weiss, aaron at sparkfun dot com
    Read/Write Capabilities added by NPoole
    OSHW license: http://freedomdefined.org/OSHW
  
  works with 13.56MHz MiFare 1k tags

  Based on hardware v13:
  D7 -> RFID RX
  D8 -> RFID TX
  D9 -> XBee TX
  D10 -> XBee RX
  
  Note: RFID Reset attached to D13 (aka status LED)
  
  Note: be sure include the NewSoftSerial lib, http://arduiniana.org/libraries/newsoftserial/
  
  Usage: Sketch prints 'Start' and waits for a tag. When a tag is in range, the shield reads the tag,
  blinks the 'Found' LED and prints the serial number of the tag to the serial port. It then attempts to
  authenticate memory block 0x01. If this is successful it will display the "Success" status message and
  the contents of memory block 0x01. It will then ask for a 4-byte input to write to the tag. After receiving
  a 4-byte input, it will attempt to write this data to the tag and return the write status. 
  
  This code uses the default transport key to authenticate the Value Block 0x01. If you have already written
  a key to this block in your MIFARE tag, the code may not work properly. 

*/
#include <SoftwareSerial.h>

SoftwareSerial rfid(7, 8);
SoftwareSerial xbee(10, 9);

//Prototypes
void check_for_notag(void);
void halt(void);
void parse(void);
void print_serial(void);
void read_serial(void);
void seek(void);
void set_flag(void);
void authenticate(void);
void write_Block(void);

//Global var
int flag = 0;
int Str1[11];
int Str2[11];
int Str3[11];
int Str4[11];
int Str5[4];

char in1 = 0;
char in2 = 0;
char in3 = 0;
char in4 = 0;

int chksum;
int chkchksum;

int flag2 = 0;

byte auth = 76;

const int buttonPin1 = 2;     // the number of the pushbutton pin
const int buttonPin2 = 3; 
const int buttonPinEnter = 4; 

// variables will change:
int buttonState1 = 0;         // variable for reading the pushbutton status
int buttonState2 = 0;
int buttonStateEnter = 0;
int Msg[15];
int plads = 0;

//INIT
void setup()  
{
  Serial.begin(9600);
  Serial.println("Start");
    // initialize the pushbutton pin as an input:
  pinMode(buttonPin1, INPUT);     
  pinMode(buttonPin2, INPUT);    
  pinMode(buttonPinEnter, INPUT); 
  
  // set the data rate for the NewSoftSerial ports
  xbee.begin(9600);
  rfid.begin(19200);
  delay(10);
  halt();
}

void SaveInArray(int input){
  if(plads < 16){
  Msg[plads] = input;
  plads = plads + 1;
  Serial.print(Msg[plads-1]);}
  else{
   //fejl 
  }
}

boolean WriteToRFID(int strToWrite[16]){ 
    read_serial();
    //write to tag
  rfid.write((uint8_t)255); //Command header
  rfid.write((uint8_t)0); //Reserved
  rfid.write((uint8_t)6); 
  rfid.write((uint8_t)138); //Write value block command 
  rfid.write((uint8_t)1); //Number of block to write, change this to write to another block
  chksum = 6+138+1;
  
  int i = 0;
  for (i=0;i<16;i++)
  {
      rfid.write(strToWrite[i]);
      chksum += strToWrite[i];
      strToWrite[i] = 0;
  }
  
    //close write command
  rfid.write((uint8_t)chksum);
  
  
    delay(50); //get response
  while(rfid.available()){
    if(rfid.read() == 255){
      for(int i=1;i<23;i++){
        Str3[i]= rfid.read();
      }
    }
  }
  int checksum = 0;
  int j = 1;
  for(j=1; j<16; j++)
  {
    checksum += Str3[j];
  }

  
  if(chksum == checksum)
  {
    Serial.print("Write sucess!");
    Serial.println();
  }
  else
  {
    Serial.print("Write failed :(, did you remove tag before data was written?");
    Serial.println();
    Serial.print("chksum: ");
    Serial.print(chksum);
    Serial.println();
    Serial.print("chkchksum: ");
    Serial.print(checksum);
    Serial.println();
  }
}

//MAIN
void loop()                 
{
  // read the state of the pushbutton value:
  if(!((buttonState1 == HIGH && digitalRead(buttonPin1) == HIGH) || (buttonState2 == HIGH && digitalRead(buttonPin2) == HIGH) || (buttonStateEnter == HIGH && digitalRead(buttonPinEnter) == HIGH)))
  {
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
     WriteToRFID(Msg);
      plads = 0;
   }
   
   delay(10);
 }
}


void check_for_notag()
{
  seek();
  delay(10);
  parse();
  set_flag();
  
  if(flag = 1){
    seek();
    delay(10);
    parse();
  }
}

void halt()
{
 //Halt tag
  rfid.write((uint8_t)255);
  rfid.write((uint8_t)0);
  rfid.write((uint8_t)1);
  rfid.write((uint8_t)147);
  rfid.write((uint8_t)148);
}

void parse()
{
  while(rfid.available()){
    if(rfid.read() == 255){
      for(int i=1;i<11;i++){
        Str1[i]= rfid.read();
      }
    }
  }
}

void print_serial()
{
  if(flag == 1){
    //print to serial port
    Serial.print("TAG ID: ");
    Serial.print(Str1[8], HEX);
    Serial.print(Str1[7], HEX);
    Serial.print(Str1[6], HEX);
    Serial.print(Str1[5], HEX);
    Serial.println();
    //print to XBee module
    xbee.print(Str1[8], HEX);
    xbee.print(Str1[7], HEX);
    xbee.print(Str1[6], HEX);
    xbee.print(Str1[5], HEX);
    xbee.println();
    delay(100);
    //check_for_notag();

  }
}

void read_serial()
{
  seek();
  delay(10);
  parse();
  set_flag();
  print_serial();
  delay(100);
  if(flag2 == 0){authenticate();};
}

void seek()
{
  //search for RFID tag
  rfid.write((uint8_t)255);
  rfid.write((uint8_t)0);
  rfid.write((uint8_t)1);
  rfid.write((uint8_t)130);
  rfid.write((uint8_t)131);
  delay(10);
}

void set_flag()
{
  if(Str1[2] == 6){
    flag++;
    flag2 = 0;
    
  }
  if(Str1[2] == 2){
    flag = 0;
    flag2 = 1;
  }
}

void authenticate() //Attempt to authenticate block 0x01 with Transport Key
{
  int resp = 0;
  byte auth = 76; //Status message for successful authorization
  rfid.write((uint8_t)255);
  rfid.write((uint8_t)0);
  rfid.write((uint8_t)3);
  rfid.write((uint8_t)133);
  rfid.write((uint8_t)1);
  rfid.write((uint8_t)255);
  rfid.write((uint8_t)136);
//Get Response

  delay(50);
  while(rfid.available()){
    if(rfid.read() == 255){
      for(int i=1;i<11;i++){
        Str2[i]= rfid.read();
      }
    }
  }
    Serial.print("Login Status: "); //Status of Login attempt
   // Serial.print(Str2[1], HEX);
   // Serial.print(Str2[2], HEX);
   // Serial.print(Str2[3], HEX);
   // Serial.print(Str2[4], HEX);
    
    if(Str2[4] == auth){Serial.print(" (Successful!)");} //If successful, tell the user
    else {Serial.print(" (Failed.)");} //If any other that successful
    
    Serial.println();  //Make some breathing room on the terminal
/*
//Read the contents of block 0x01
  rfid.write((uint8_t)255);
  rfid.write((uint8_t)0);
  rfid.write((uint8_t)2);
  rfid.write((uint8_t)135);
  rfid.write((uint8_t)1);
  rfid.write((uint8_t)138);

  //Get response
  
  delay(50);
  while(rfid.available()){
    if(rfid.read() == 255){
      for(int i=1;i<11;i++){
        Str4[i]= rfid.read();
      }
    }
  }
    Serial.print("Read Status: "); //Return Read attempt status
    Serial.print(Str4[1], HEX);
    Serial.print(Str4[2], HEX);
    Serial.print(Str4[3], HEX);
    Serial.print(Str4[4], HEX);
    Serial.println();
    Serial.print("Block Content: "); 
    
       in1 = char(Str4[5]); //Convert contents of block 0x01 from Hex
       in2 = char(Str4[6]);
       in3 = char(Str4[7]);
       in4 = char(Str4[8]);
   
    Serial.print(in1); //Return contents of block 0x01
    Serial.print(in2);
    Serial.print(in3);
    Serial.print(in4);
    Serial.println();

     flag2 = 1;
     
     if(Str2[4] == auth){write_Block();} //If block is successfuly authenticated, move on to writing */
 }

void write_Block(){

     Serial.println();
     Serial.println();
     Serial.print("Data to Write?"); //Ask for data to write. (4-byte Value)
     
     while(Serial.available() < 4){}; //Grab 4 bytes from Serial
     for (int i=0; i < 4; i++){
       Str5[i] = Serial.read();}
       
       in1 = char(Str5[0]); //Convert bytes
       in2 = char(Str5[1]);
       in3 = char(Str5[2]);
       in4 = char(Str5[3]);

    Serial.println();
    Serial.print("To Write: "); //Return Bytes to Write
    Serial.print(in1);
    Serial.print(in2);
    Serial.print(in3);
    Serial.print(in4);
       
       Serial.println(); //Back up, everyone, give me some space
       
  //write to tag
  rfid.write((uint8_t)255); //Command header
  rfid.write((uint8_t)0); //Reserved
  rfid.write((uint8_t)6); 
  rfid.write((uint8_t)138); //Write value block command 
  rfid.write((uint8_t)1); //Number of block to write, change this to write to another block
  
  //data to write
  rfid.write((uint8_t)Str5[0]); //Send four bytes from ealier:
  rfid.write((uint8_t)Str5[1]);
  rfid.write((uint8_t)Str5[2]);
  rfid.write((uint8_t)Str5[3]);
  
  //close write command
  chksum = 6+138+1+Str5[0]+Str5[1]+Str5[2]+Str5[3]; //calculate checksum
  rfid.write((uint8_t)chksum);

  delay(50); //get response
  while(rfid.available()){
    if(rfid.read() == 255){
      for(int i=1;i<11;i++){
        Str3[i]= rfid.read();
      }
    }
  }
  
  chkchksum = Str3[1] + Str3[2] + Str3[3] + Str3[4] + Str3[5] + Str3[6] + Str3[7] + Str3[8];
  
  if(chksum == chkchksum)
  {
    Serial.print("Write sucess!");
    Serial.println();
  }
  else
  {
    Serial.print("Write failed :(, did you remove tag before data was written?");
    Serial.println();
    //Serial.print("chksum: ");
    //Serial.print(chksum);
    //Serial.println();
    //Serial.print("chkchksum: ");
    //Serial.print(chkchksum);
    Serial.println();
  }
  

 /* 
    Serial.print("Write Status: "); //reutrn write status
    Serial.print(Str3[1]);
    Serial.print(" ");
    Serial.print(Str3[2]);
    Serial.print(" ");
    Serial.print(Str3[3]);
    Serial.print(" ");
    Serial.print(Str3[4]);
    Serial.print(" ");
    Serial.print(Str3[5]);
    Serial.print(" ");
    Serial.print(Str3[6]);
    Serial.print(" ");
    Serial.print(Str3[7]);
    Serial.print(" ");
    Serial.print(Str3[8]);
    Serial.println();
*/
read_serial(); //start all over
     
}
