/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 25/01/12
 * Time: 11:47
 Simple an robust posix serial interface
 */

#include <ctype.h>
#include <errno.h>
#include <sys/ioctl.h>
#include <signal.h>
#include <fcntl.h>
#include <termios.h>
#include <unistd.h>
#include <sys/select.h>
#include "../utils/utils.h"
#include "serialposix.h"
#include <dirent.h>


#define _POSIX_SOURCE 1 /* POSIX compliant source */

#define BUFFER_SIZE 512
//#define PROC_BASE  "/dev/"

static int quitter=0;


/**
 *  function to throw event
 */
void (*SerialEvent) (int taille,unsigned char *data);

/**
 *  Assign function
 * @param fn pointer to the callback
 */
int register_SerialEvent( void* fn){
	SerialEvent=fn;
	return 0;
};

/*
Port* scan_fd(void)
{
	DIR *dir;
	struct dirent *de;
	char path[PATH_MAX+1];
	pid_t pid;
	int empty;
	struct stat sb;
	if (!(dir = opendir(PROC_BASE)))
	{
		perror(PROC_BASE);
		return NULL;
	}
	empty = 0;
	char name[PATH_MAX+1];


	static Port myports[MAX_PORTS];
	int i;
	for(i=0;i<MAX_PORTS;i++)
	{
		memset(myports[i].devicename,0,sizeof(myports[i].devicename));
	}
	while ( ( de = readdir(dir) ) )
	{

		// /dev/cu.*     /dev/cu.usbserial-XXXXX
		// /dev/tty.*    dev/tty.usbserial-XXXXX
		// dev/ttyUSB*
		// dev/ttyAC*
		if(de->d_name[0] == 't' && de->d_name[1] == 't' && de->d_name[2] == 'y' || de->d_name[0] == 'c' && de->d_name[1] == 'u' )
		{
			sprintf(name,"%s%s",PROC_BASE,de->d_name);
			if (stat(name,&sb) >= 0) {
				switch (sb.st_mode & S_IFMT) {
				case S_IFCHR:

					if(de->d_name[3] == 'U' || de->d_name[3] == '.' || de->d_name[3] == 'A')
					{
						strcpy(myports[empty].devicename,de->d_name);
						empty++;
					}
					break;
				}
			}
		}
	}

	closedir(dir);

	if (empty == 0)
	{
		return myports;
		//fprintf(stderr,PROC_BASE " is empty (not mounted ?)\n");

	}
	return &myports[0];
}
*/

/**
 *  Write a byte on the fd
 * @param  file descriptor
 * @param  byte
 */
int serialport_writebyte( int fd, uint8_t b)
{
	int n = write(fd,&b,1);
	if( n!=1)
		return -1;
	return 0;
}

/**
 * Read a byte on the fd
 * @param  file descriptor
 * @param  byte
 */
int serialport_readbyte( int fd, uint8_t *b)
{
	int n = read(fd,&b,1);
	if( n!=1)
		return -1;
	return 0;
}

/**
 * write an array of char
 * @param  file descriptor
 * @param  pointer of char
 */
int serialport_write(int fd,  char* str)
{
	int len,n;
	len= strlen(str);

	n = write(fd, str, len);
	if( n!=len )
	{
	    perror("write");
	   	return -1;
	}
	return serialport_writebyte(fd,'\n'); /* finish */
}

/**
 * read an array of char
 * @param  file descriptor
 * @param  pointer of char
 */

int serialport_read(int fd, char *ptr){
	char b[1];
	int i=0;
	int n;
	do {
		n = read(fd, b, 1);
		if( n==-1) {
			usleep( 100*1000 );
			continue;
		}
		if( n==0 ) {
			usleep( 10 * 1000 );
			continue;
		}
		if(b[0] != 10){
			ptr[i] = b[0];
			i++;
		}
	} while( (b[0] != '\n') && (quitter == 0) && (i < BUFFER_SIZE)); /* detect finish and protect overflow*/

	return i;
}


/**
 *   Verify is the serial port is opennable
 * @param devicename the name of serial port  eg: /dev/ttyUSB0
 */
int verify_fd(char *devicename)
{
	int fd;
	if((fd = open(devicename,O_RDONLY|  O_NONBLOCK )) == -1)
	{
		return -1;
	}
	else
	{
		close(fd);
		return 0;
	}

}

/**
 *   Monitor the serial and throw an event if the link is broken
 * @param devicename the name of serial port  eg: /dev/ttyUSB0
 */
void *serial_monitoring(char *devicename)
{
	char name[512];
	int fd;
	strcpy(name,devicename);    // store local to protect garbage collector JNA
	while(quitter ==0)
	{
		sleep(1);
		if(verify_fd(name) == -1)
		{
			SerialEvent(-1,"WTF 42 \n");
		    pthread_exit(NULL);
		}
	}
	pthread_exit(NULL);
}

/**
 * throw callback event on icomming data
 * @param int file descriptor
 */
void *serial_reader(int fd)
{
	char byte[BUFFER_SIZE];
	int taille;
	while(quitter ==0)
	{
		if((taille =serialport_read(fd,byte)) > 0 && quitter == 0)
		{
			SerialEvent(taille,byte);
			memset(byte,0,sizeof(byte));
		}

	}
	pthread_exit(NULL);
}

/**
 * Create a pthread  to manage incomming data
 * @param int file descriptor
 */
int reader_serial(int fd){
	pthread_t lecture;
	if(quitter == 0)
	{
		return  pthread_create (& lecture, NULL,&serial_reader, fd);
	} else {

	return 0;
	}

}

/**
 * Create a pthread  to manage incomming data
 * @param devicename the name of serial port  eg: /dev/ttyUSB0
 */
int monitoring_serial(char *name_device)
{
	pthread_t monitor;
	return pthread_create (& monitor, NULL,&serial_monitoring, name_device);
}


/**
 * Open a file descriptor
 * @param devicename the name of serial port  eg: /dev/ttyUSB0
 * @param bitrate the speed of the serial port
 */
int open_serial(const char *_name_device,int _bitrate){

	int fd,bitrate;
	struct termios termios;
    int         status = 0;
	/* process baud rate */
	switch (_bitrate) {
	case 4800: bitrate = B4800; break;
	case 9600: bitrate = B9600; break;
	case 19200: bitrate = B19200; break;
	case 38400: bitrate = B38400; break;
	case 57600: bitrate = B57600; break;
	case 115200: bitrate = B115200; break;
	case 230400 : bitrate = B230400; break;
	default:
		return -1;
	}

    quitter = 1;
    usleep(2000);

	// init  loop variable
	quitter = 0;

	/* open the serial device */
	fd = open(_name_device,O_RDWR |O_NOCTTY | O_NONBLOCK );

	if(fd < 0)
	{
		close_serial(fd);
		return -2;
	}

	tcgetattr(fd, & termios);
    cfmakeraw(& termios);
    cfsetispeed(& termios, bitrate);
    cfsetospeed(& termios, bitrate);

	termios.c_cflag = CREAD | CLOCAL;	 // turn on READ and ignore modem control lines
    termios.c_cflag |= CS8;

	// see: http://unixwiz.net/techtips/termios-vmin-vtime.html
	termios.c_cc[VMIN]  = 0; 	// read() returns immediately
	termios.c_cc[VTIME] = 0;


	if (tcsetattr(fd, TCSANOW, & termios) != 0) {
		perror("tcflush");
       	return  -4;
	}


    /* flush the serial device */
	if (tcflush(fd, TCIOFLUSH))
	{
		perror("tcflush");
		return  -4;
	}

	if( tcsetattr(fd, TCSANOW, &termios) < 0) {
		perror("init_serialport: Couldn't set term attributes");
		return -5;
	}



	return fd;

}

int close_serial(int fd)
{
	if(quitter ==0)
	{
		close(fd);
		quitter = 1;
	}else
	{
		quitter = 1;
		return -12;
	}

	return 0;
}


