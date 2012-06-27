/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 25/01/12
 * Time: 11:47
 Simple an robust posix serial interface

ChangeLog:
- fix concurrent JVM (open the same file descriptor)   27 juin 2012 jed

 */

#include <ctype.h>
#include <errno.h>
#include <sys/ioctl.h>
#include <signal.h>
#include <fcntl.h>
#include <termios.h>
#include <unistd.h>
#include <sys/select.h>
#include "serialposix.h"
#include <dirent.h>
#include <sys/ipc.h>
#include <sys/shm.h>


#define _POSIX_SOURCE 1 /* POSIX compliant source */
#define VERSION 1.5
#define JED_IPC_PRIVATE 24011985
#define BUFFER_SIZE 512
#define OK 0
#define ERROR -1
#define ALIVE 0
#define EXIT 1

#define FD_DISCONNECTED -10

#define EXIT_CONCURRENT_VM -42
#define FD_ALREADY_CLOSED -12
//#define PROC_BASE  "/dev/"

int shmid;
// memory shared
static int *quitter;

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
	return OK;
};


/*                     -
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
	return OK;
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
		return ERROR;
	return OK;
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
	   	return ERROR;
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
	} while( (b[0] != '\n') && (*quitter == 0) && (i < BUFFER_SIZE)); /* detect finish and protect overflow*/

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
		return ERROR;
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
	while(*quitter ==0)
	{
		sleep(1);
		if(verify_fd(name) == -1)
		{
			SerialEvent(FD_DISCONNECTED,"");
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
	while(*quitter ==0)
	{
		if((taille =serialport_read(fd,byte)) > 0 && *quitter == 0)
		{
			SerialEvent(taille,byte);
			memset(byte,0,sizeof(byte));
		}
	}

	if(*quitter == EXIT_CONCURRENT_VM)
	{
	 	SerialEvent(EXIT_CONCURRENT_VM,"");
	}
	pthread_exit(NULL);
}

/**
 * Create a pthread  to manage incomming data
 * @param int file descriptor
 */
int reader_serial(int fd){
	pthread_t lecture;
	if(*quitter == 0)
	{
		return  pthread_create (& lecture, NULL,&serial_reader, fd);
	} else
	{
	    return ERROR;
	}
}

/**
 * Create a pthread  to manage incomming data
 * @param devicename the name of serial port  eg: /dev/ttyUSB0
 */
int monitoring_serial(char *name_device)
{
	pthread_t monitor;
	if(*quitter == 0)
    {
     	return pthread_create (& monitor, NULL,&serial_monitoring, name_device);
    }
    else
    {
        return ERROR;
   }
}


/**
 * Open a file descriptor
 * @param devicename the name of serial port  eg: /dev/ttyUSB0
 * @param bitrate the speed of the serial port
 */
int open_serial(const char *_name_device,int _bitrate){

    char *addr;
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
      // create memory shared
     shmid = shmget(JED_IPC_PRIVATE,sizeof(int), 0666 | IPC_CREAT );
     if(shmid < 0)
     {
         perror("shmid");
         return -1;
     }
      addr = shmat(shmid, 0, 0);
      if(addr < 0)
      {
          perror("shmat");
         return -1;
      }
     // bind to memory shared
    quitter = (int *) addr;
    if(quitter == NULL)
    {
        return -1;
    }
    if(quitter != NULL)
    {
       if(*quitter == ALIVE)
        {
           	*quitter = EXIT_CONCURRENT_VM;
            sleep(1);
        }
    }

	// init  loop variable
	*quitter = ALIVE;

	/* open the serial device */
	fd = open(_name_device,O_RDWR |O_NOCTTY | O_NONBLOCK );

	if(fd < 0)
	{
		close_serial(fd);
		return -2;
	}

    tcgetattr(fd, &termios);
    termios.c_iflag       = INPCK;
    termios.c_lflag       = 0;
    termios.c_oflag       = 0;
    termios.c_cflag       = CREAD | CS8 | CLOCAL;
    termios.c_cc[ VMIN ]  = 0;
    termios.c_cc[ VTIME ] = 0;
    cfsetispeed(&termios, bitrate);
    cfsetospeed(&termios, bitrate);
    tcsetattr(fd, TCSANOW, &termios);

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
    // todo destroy memory shared if there is no more clients
	if(*quitter ==ALIVE)
	{
		close(fd);
		*quitter = EXIT;
	}else
	{
		*quitter = EXIT;
		return FD_ALREADY_CLOSED;
	}
	return OK;
}


