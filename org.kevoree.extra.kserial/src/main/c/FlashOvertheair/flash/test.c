#include "flash.h"



int main(int argc,char **argv[])
{

int lastmem;
   unsigned char file_intel_hex_array[30720];
   int taille =  open_file("../program_test/test.hex",&file_intel_hex_array);


   write_on_the_air_program("/dev/ttyUSB0",ATMEGA328,"NODE0",taille,&file_intel_hex_array[0]);

 return 0;
}