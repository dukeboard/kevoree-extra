/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 03/02/12
 * Time: 17:27
 */

#include <stdio.h>    /* Standard input/output definitions */
#include <stdlib.h>
#include <stdint.h>   /* Standard types */
#include <string.h>   /* String function definitions */
#include <unistd.h>   /* UNIX standard function definitions */
#include <fcntl.h>    /* File control definitions */
#include <errno.h>    /* Error number definitions */
#include <termios.h>  /* POSIX terminal control definitions */
#include <sys/ioctl.h>
#include <getopt.h>
#include <ctype.h>
#include <fcntl.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <termios.h>
#include <unistd.h>


#define PRINT_HEXA 0
#define PRINT_CHAR 1
#define MAX_SIZE_ID 5

#define MAX_SIZE_DEVICE_NAME 512

#define ATMEGA328 0
#define ATMEGA1280 1
#define ATMEGA168 2


typedef struct _target
{
 char port_device[MAX_SIZE_DEVICE_NAME];
 int fd;
 int target;
 char dest_node_id[MAX_SIZE_ID];
 int taille;
 int last_memory_address;
 unsigned char *intel_hex_array;
} Target;


void *flash_firmware(Target *targ);
unsigned char * parse_intel_hex(int taille,int *last_memory, unsigned char *src_hex_intel);
int serialport_writebyte( int fd, uint8_t b);
uint8_t  serialport_readbyte( int fd);

int register_FlashEvent( void* fn);