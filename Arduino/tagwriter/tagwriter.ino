/*
  RFID Eval 13.56MHz Shield example sketch v10

  Aaron Weiss, aaron at sparkfun dot com
  OSHW license: http://freedomdefined.org/OSHW

  works with 13.56MHz MiFare 1k tags

  Based on hardware v13:
  D7 -> RFID RX
  D8 -> RFID TX
  D9 -> XBee TX
  D10 -> XBee RX

  Note: RFID Reset attached to D13 (aka status LED)

  Note: be sure include the SoftwareSerial lib, http://arduiniana.org/libraries/newsoftserial/

  Usage: Sketch prints 'Start' and waits for a tag. When a tag is in range, the shield reads the tag,
  blinks the 'Found' LED and prints the serial number of the tag to the serial port
  and the XBee port.
  
  06/04/2013 - Modified for compatibility with Arudino 1.0. Seb Madgwick.

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

int flag2 = 0;

byte auth = 76;
//INIT
void setup()
{
  Serial.begin(9600);
  Serial.println("Start");

  // set the data rate for the SoftwareSerial ports
  xbee.begin(9600);
  rfid.begin(19200);
  delay(10);
  halt();
}

//MAIN
void loop()
{
  read_serial();
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
    flag2 = 0;
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
  }
  if(Str1[2] == 2){
    flag = 0;
  }
}

void authenticate() //Attempt to authenticate block 0x01 with Transport Key
{
  int resp = 0;
  byte auth = 76; //Status message for successful authorization
  rfid.print((uint8_t)255); //Command header
  rfid.print((uint8_t)0); //reserved
  rfid.print((uint8_t)3); 
  rfid.print((uint8_t)133); //Auth Command
  rfid.print((uint8_t)1); //Number of Block to authenticate, change this to write a different block
  rfid.print((uint8_t)255); 
  rfid.print((uint8_t)136); //Checksum, change this too if you change any other part of the command, chksm = (all values) - header

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
    Serial.print(Str2[1], HEX);
    Serial.print(Str2[2], HEX);
    Serial.print(Str2[3], HEX);
    Serial.print(Str2[4], HEX);
    
    if(Str2[4] == auth){Serial.print(" (Successful!)");} //If successful, tell the user
    else {Serial.print(" (Failed.)");} //If any other that successful
    
    Serial.println();  //Make some breathing room on the terminal

//Read the contents of block 0x01

  rfid.print((uint8_t)255);//Command header
  rfid.print((uint8_t)0);//reserved
  rfid.print((uint8_t)2);
  rfid.print((uint8_t)135);//Read Value Block Command
  rfid.print((uint8_t)1);//Number of Block to read, change this to read a different block
  rfid.print((uint8_t)138);//Checksum, change this too if you change any other part of the command, chksm = (all values) - header
  
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
    
       in1 = char((uint8_t)Str4[5]); //Convert contents of block 0x01 from Hex
       in2 = char((uint8_t)Str4[6]);
       in3 = char((uint8_t)Str4[7]);
       in4 = char((uint8_t)Str4[8]);
   
    Serial.print(in1); //Return contents of block 0x01
    Serial.print(in2);
    Serial.print(in3);
    Serial.print(in4);
    Serial.println();
    write_Block();
     flag2 = 1;
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
  rfid.print((uint8_t)255);//Command Header
  rfid.print((uint8_t)0);//reserved
  rfid.print((uint8_t)6);
  rfid.print((uint8_t)138); //Write Value Block Command
  rfid.print((uint8_t)1); //Number of Block to write, change this to write a different block
  //data to write
  rfid.print((uint8_t)Str5[0]); //send 4 bytes from earlier
  rfid.print((uint8_t)Str5[1]);
  rfid.print((uint8_t)Str5[2]);
  rfid.print((uint8_t)Str5[3]);
  //close write command
  chksum = 6+138+1+Str5[0]+Str5[1]+Str5[2]+Str5[3]; //calculate checksum
  rfid.print((uint8_t)chksum); //write checksum

  delay(50); //get response
  while(rfid.available()){
    if(rfid.read() == 255){
      for(int i=1;i<11;i++){
        Str3[i]= rfid.read();
      }
    }
  }
    Serial.print("Write Status: "); //reutrn write status
    Serial.print(Str3[1], HEX);
    Serial.print(Str3[2], HEX);
    Serial.print(Str3[3], HEX);
    Serial.print(Str3[4], HEX);
    Serial.print(Str3[5], HEX);
    Serial.print(Str3[6], HEX);
    Serial.print(Str3[7], HEX);
    Serial.print(Str3[8], HEX);
    Serial.println();
}
