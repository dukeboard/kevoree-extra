/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 03/02/12
 * Time: 17:27
 */



#define JED_IPC_PRIVATE 240118281

#define BOOTLOADER_SAVE_FLAG 'S'
#define READY_BOOTLOADER_FLAG 'T'
#define ALIVE 0
#define RE_SEND_FLAG 7
#define OK 0
#define ERROR -1
#define ERROR_WRITE -2
#define ERROR_READ -3
#define FD_DISCONNECTED -10
#define ERROR_NOT_ENOUGH_FLASH -11
#define FAIL_TO_BOOT_INTO_BOOTLOADER -12
#define EXIT 1
#define EVENT_WAITING_BOOTLOADER 2
#define FINISH 3
#define RE_SEND_EVENT 4
#define BOOTLOADER_STARTED 5
#define BOOT_INTO_BOOTLOADER_FLAG 6

#include <stdint.h>   /* Standard types */
#include <errno.h>    /* Error number definitions */
#include <termios.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/signal.h>
#include <sys/types.h>
#include <string.h>
#include <stdlib.h>
#include <sys/ioctl.h>

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
 char dest_node_id[MAX_SIZE_ID+1];
 int taille;
 int last_memory_address;
 unsigned char *intel_hex_array;
} Target;


void *flash_firmware(Target *targ);
unsigned char * parse_intel_hex(int taille,int *last_memory, unsigned char *src_hex_intel);
int serialport_writebyte( int fd, char b);
uint8_t  serialport_readbyte( int fd);

int register_FlashEvent( void* fn);