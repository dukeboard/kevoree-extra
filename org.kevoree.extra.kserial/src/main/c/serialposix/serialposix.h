#ifndef SERIAL
#define SERIAL

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
#include <dirent.h>
#define MAX_PORTS 32
#define MAX_SIZE_DEVICENAME 128


typedef  struct _Port {
	char devicename[MAX_SIZE_DEVICENAME];
} Port;


int open_serial(const char *_name_device,int _bitrate);

int serialport_writebyte(int fd, uint8_t b);
int serialport_readebyte(int fd, uint8_t *b);
int serialport_write(int fd,  char* str);
int serialport_read(int fd,char *ptr);

Port* scan_fd(void);
void *serial_monitoring();
int reader_serial();

int verify_fd(char *devicename);


#endif
