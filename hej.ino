#include <SoftwareSerial.h>
#include <LiquidCrystal.h>

LiquidCrystal lcdSW407F13(2, A4, 3, 4, 5, 9);
SoftwareSerial rfidSW407F13(7, 8);

//Global var
int flagSW407F13 = 0;
int Str1SW407F13[11];
int Str2SW407F13[11];
int Str3SW407F13[11];
int Str4SW407F13[11];

char in1SW407F13 = 0;
char in2SW407F13 = 0;
char in3SW407F13 = 0;
char in4SW407F13 = 0;

int chksumSW407F13;
int chkchksumSW407F13;
int stringlengthSW407F13;

byte authSW407F13 = 76;

void haltSW407F13()
{
  rfidSW407F13.write((uint8_t)255);
  rfidSW407F13.write((uint8_t)0);
  rfidSW407F13.write((uint8_t)1);
  rfidSW407F13.write((uint8_t)147);
  rfidSW407F13.write((uint8_t)148);
}

void parseSW407F13()
{
  while(rfidSW407F13.available()){
    if(rfidSW407F13.read() == 255){
      for(int iSW407F13=1;iSW407F13<11;iSW407F13++){
        Str1SW407F13[iSW407F13]= rfidSW407F13.read();
      }
    }
  }
}

void seekSW407F13()
{
  //search for RFID tag
  rfidSW407F13.write((uint8_t)255);
  rfidSW407F13.write((uint8_t)0);
  rfidSW407F13.write((uint8_t)1);
  rfidSW407F13.write((uint8_t)130);
  rfidSW407F13.write((uint8_t)131);
  delay(10);
}

void set_flagSW407F13()
{
  if(Str1SW407F13[2] == 6){
    flagSW407F13++;
  }
  else{
    flagSW407F13 = 0;
  }
}

boolean authenticateSW407F13(String tempidSW407F13, String tempcountSW407F13) //Attempt to authenticate block 0x01 with Transport Key
{
  boolean returnvalueSW407F13 = false;
  rfidSW407F13.write((uint8_t)255);
  rfidSW407F13.write((uint8_t)0);
  rfidSW407F13.write((uint8_t)3);
  rfidSW407F13.write((uint8_t)133);
  rfidSW407F13.write((uint8_t)1);
  rfidSW407F13.write((uint8_t)255);
  rfidSW407F13.write((uint8_t)136);

  delay(50);
  while(rfidSW407F13.available()){
    if(rfidSW407F13.read() == 255){
      for(int iSW407F13=1;iSW407F13<11;iSW407F13++){
        Str2SW407F13[iSW407F13]= rfidSW407F13.read();
      }
    }
  }

  rfidSW407F13.write((uint8_t)255);
  rfidSW407F13.write((uint8_t)0);
  rfidSW407F13.write((uint8_t)2);
  rfidSW407F13.write((uint8_t)135);
  rfidSW407F13.write((uint8_t)1);
  rfidSW407F13.write((uint8_t)138);
  
  delay(50);
  while(rfidSW407F13.available()){
    if(rfidSW407F13.read() == 255){
      for(int iSW407F13=1;iSW407F13<11;iSW407F13++){
        Str4SW407F13[iSW407F13]= rfidSW407F13.read();
      }
    }
  }
     
   if(Str2SW407F13[4] == authSW407F13){returnvalueSW407F13 = write_BlockSW407F13(tempidSW407F13, tempcountSW407F13);}
   return returnvalueSW407F13;
 }
 

boolean write_BlockSW407F13(String paraidSW407F13, String paracountSW407F13){
    boolean returnvalueSW407F13 = false;
    
    int idlengthSW407F13 = paraidSW407F13.length() + 1;
    char idarraySW407F13[idlengthSW407F13];
    
    paraidSW407F13.toCharArray(idarraySW407F13,idlengthSW407F13);
    
    //write to tag
    rfidSW407F13.write((uint8_t)255); //Command header
    rfidSW407F13.write((uint8_t)0); //Reserved
    rfidSW407F13.write((uint8_t)6); 
    rfidSW407F13.write((uint8_t)138); //Write value block command 
    rfidSW407F13.write((uint8_t)1); //Number of block to write, change this to write to another block
  
    //data to write
    rfidSW407F13.write((uint8_t)byte(idarraySW407F13[0])); //Send four bytes from ealier:
    rfidSW407F13.write((uint8_t)byte(idarraySW407F13[1]));
    rfidSW407F13.write((uint8_t)byte(idarraySW407F13[2]));
    rfidSW407F13.write((uint8_t)byte(idarraySW407F13[3]));
  
    //close write command
    chksumSW407F13 = 6+138+1+byte(idarraySW407F13[0])+byte(idarraySW407F13[1])+byte(idarraySW407F13[2])+byte(idarraySW407F13[3]); //calculate checksum
    rfidSW407F13.write((uint8_t)chksumSW407F13);
  
    delay(50); //get response
    while(rfidSW407F13.available()){
      if(rfidSW407F13.read() == 255){
        for(int iSW407F13=1;iSW407F13<11;iSW407F13++){
          Str3SW407F13[iSW407F13]= rfidSW407F13.read();
        }
      }
    }
 
    chkchksumSW407F13 = Str3SW407F13[1] + Str3SW407F13[2] + Str3SW407F13[3] + Str3SW407F13[4] + Str3SW407F13[5] + Str3SW407F13[6] + Str3SW407F13[7] + Str3SW407F13[8];

    if(chksumSW407F13 == chkchksumSW407F13)
    {
      returnvalueSW407F13 = true;
    }
    else
    {
      returnvalueSW407F13 = false;
    }
    
    int countlengthSW407F13 = paracountSW407F13.length() + 1;
    char countarraySW407F13[countlengthSW407F13];
    
    paracountSW407F13.toCharArray(countarraySW407F13,countlengthSW407F13);
    
    rfidSW407F13.write((uint8_t)255); //Command header
    rfidSW407F13.write((uint8_t)0); //Reserved
    rfidSW407F13.write((uint8_t)6); 
    rfidSW407F13.write((uint8_t)138); //Write value block command 
    rfidSW407F13.write((uint8_t)2); //Number of block to write, change this to write to another block
    
    rfidSW407F13.write((uint8_t)byte(countarraySW407F13[0]));
    rfidSW407F13.write((uint8_t)byte(countarraySW407F13[1]));
    rfidSW407F13.write((uint8_t)byte(countarraySW407F13[2]));
    rfidSW407F13.write((uint8_t)byte(countarraySW407F13[3]));
  
    chksumSW407F13 = 6+138+2+byte(countarraySW407F13[0])+byte(countarraySW407F13[1])+byte(countarraySW407F13[2])+byte(countarraySW407F13[3]); //calculate checksum
    rfidSW407F13.write((uint8_t)chksumSW407F13);

    delay(50); //get response
    while(rfidSW407F13.available()){
      if(rfidSW407F13.read() == 255){
        for(int iSW407F13=1;iSW407F13<11;iSW407F13++){
          Str3SW407F13[iSW407F13]= rfidSW407F13.read();
        }
      }
    }
  
    chkchksumSW407F13 = Str3SW407F13[1] + Str3SW407F13[2] + Str3SW407F13[3] + Str3SW407F13[4] + Str3SW407F13[5] + Str3SW407F13[6] + Str3SW407F13[7] + Str3SW407F13[8];

    if(chksumSW407F13 == chkchksumSW407F13)
    {
      returnvalueSW407F13 = true;
    }
    else
    {
      returnvalueSW407F13 = false;
    }
    return returnvalueSW407F13;
}

boolean RFIDWrite (int idSW407F13, int countSW407F13){
  String tempidSW407F13;
  String tempcountSW407F13;
  if (idSW407F13 < 10){
    tempidSW407F13 = "000" + String(idSW407F13);
  }
  else if (idSW407F13 < 100 && idSW407F13 >= 10) {
    tempidSW407F13 = "00" + String(idSW407F13);
  }
  else if (idSW407F13 < 1000 && idSW407F13 >= 100) {
    tempidSW407F13 = "0" +  String(idSW407F13);
  }
  else {
    tempidSW407F13 = String(idSW407F13);
  }
  
  if (countSW407F13 < 10){
    tempcountSW407F13 = "000" + String(countSW407F13);
  }
  else if (countSW407F13 < 100 && countSW407F13 >= 10) {
    tempcountSW407F13 = "00" + String(countSW407F13);
  }
  else if (countSW407F13 < 1000 && countSW407F13 >= 100) {
    tempcountSW407F13 = "0" +  String(countSW407F13);
  }
  else {
    tempcountSW407F13 = String(countSW407F13);
  }

  return (authenticateSW407F13(tempidSW407F13, tempcountSW407F13));
}

String RFIDReadSW407F13 (){
  byte authSW407F13 = 76;
  String FetchedidSW407F13;
  String FetchedcountSW407F13;
  char aSW407F13, bSW407F13, cSW407F13, dSW407F13;
  
  rfidSW407F13.write((uint8_t)255);
  rfidSW407F13.write((uint8_t)0);
  rfidSW407F13.write((uint8_t)3);
  rfidSW407F13.write((uint8_t)133);
  rfidSW407F13.write((uint8_t)1);
  rfidSW407F13.write((uint8_t)255);
  rfidSW407F13.write((uint8_t)136);

  delay(50);
  while(rfidSW407F13.available()){
    if(rfidSW407F13.read() == 255){
      for(int iSW407F13=1;iSW407F13<11;iSW407F13++){
        Str2SW407F13[iSW407F13]= rfidSW407F13.read();
      }
    }
  }

  delay(50);
  
  rfidSW407F13.write((uint8_t)255);
  rfidSW407F13.write((uint8_t)0);
  rfidSW407F13.write((uint8_t)2);
  rfidSW407F13.write((uint8_t)135);
  rfidSW407F13.write((uint8_t)1);
  rfidSW407F13.write((uint8_t)138);
  
  delay(50);
  while(rfidSW407F13.available()){
    if(rfidSW407F13.read() == 255){
      for(int iSW407F13=1;iSW407F13<11;iSW407F13++){
        Str1SW407F13[iSW407F13]= rfidSW407F13.read();
      }
    }
  }
  
  aSW407F13 = char(Str1SW407F13[5]);
  bSW407F13 = char(Str1SW407F13[6]);
  cSW407F13 = char(Str1SW407F13[7]);
  dSW407F13 = char(Str1SW407F13[8]);
  
  FetchedidSW407F13 = String(aSW407F13) + String(bSW407F13) + String(cSW407F13) + String(dSW407F13);
  
  rfidSW407F13.write((uint8_t)255);
  rfidSW407F13.write((uint8_t)0);
  rfidSW407F13.write((uint8_t)2);
  rfidSW407F13.write((uint8_t)135);
  rfidSW407F13.write((uint8_t)2);
  rfidSW407F13.write((uint8_t)139);

  delay(50);
  while(rfidSW407F13.available()){
    if(rfidSW407F13.read() == 255){
      for(int iSW407F13=1;iSW407F13<11;iSW407F13++){
        Str1SW407F13[iSW407F13]= rfidSW407F13.read();
      }
    }
  }
  aSW407F13 = char(Str1SW407F13[5]); //Convert contents of block 0x01 from Hex
  bSW407F13 = char(Str1SW407F13[6]);
  cSW407F13 = char(Str1SW407F13[7]);
  dSW407F13 = char(Str1SW407F13[8]);
  
  FetchedcountSW407F13 = String(aSW407F13) + String(bSW407F13) + String(cSW407F13) + String(dSW407F13);
  
  return (FetchedidSW407F13 + "#" + FetchedcountSW407F13);
}

void LCDPrint(String input, int line)
{
lcdSW407F13.setCursor(0,line);
lcdSW407F13.print("                ");
lcdSW407F13.setCursor(0,line);
lcdSW407F13.print(input);
}

void LCDClear(){
  lcdSW407F13.clear();
}

void pourDrink (double drink[ ][2])
{
  int i;
  int arraysize = drink[0][0];

  for(i = 1; i <= arraysize; i = i + 1)
  {
    LCDPrint ("Pouring "+ContainersnameSW407F13[(int)drink[i][0]], 0);
    LCDPrint ("Phase "+ (String)(i) + "/" + (String)(arraysize), 1);
    pour(ContainerspinSW407F13[(int)drink[i][0]], drink[i][1]);
  }
  return;
}
void makedrink ()
{
String message = "Hello World!";
LCDPrint (message , 1);
return ;
}

void setup() {

lcdSW407F13.begin(16, 2);
Serial.begin(9600);
Serial.println("Start");
rfidSW407F13.begin(19200);
delay(10);
haltSW407F13();return;
}
void loop() {
seekSW407F13();
delay(50);
parseSW407F13();
delay(50);
set_flagSW407F13();
delay(50);
if (flagSW407F13 == 1) {
  String resultstringSW407F13 = RFIDReadSW407F13();
  int resultlengthSW407F13 = resultstringSW407F13.length() + 1;
  char temparraySW407F13[resultlengthSW407F13];
  resultstringSW407F13.toCharArray(temparraySW407F13,resultlengthSW407F13);
  String idSW407F13 = String(temparraySW407F13[0]) + String(temparraySW407F13[1]) + String(temparraySW407F13[2]) + String(temparraySW407F13[3]);
  String countSW407F13 = String(temparraySW407F13[5]) + String(temparraySW407F13[6]) + String(temparraySW407F13[7]) + String(temparraySW407F13[8]);
  RFIDFound(idSW407F13.toInt(), countSW407F13.toInt());
}return;
}
