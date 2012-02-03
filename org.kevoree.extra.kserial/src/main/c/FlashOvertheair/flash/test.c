#include "flash.h"



int main(int argc,char **argv[])
{

int lastmem;
   unsigned char file_intel_hex_array[30720];
   int taille =  open_file("../program_test/test.hex",&file_intel_hex_array);

  //	print_hex_array(PRINT_CHAR,taille,file_intel_hex_array);

     unsigned char *parsed_intel_hex_array =  parse_intel_hex(taille,&lastmem,&file_intel_hex_array[0]);
     //print_hex_array(PRINT_HEXA,lastmem,parsed_intel_hex_array);



    write_on_the_air_program("/dev/ttyUSB0",ATMEGA328,"NODE0",taille,lastmem,parsed_intel_hex_array);

 return 0;
}