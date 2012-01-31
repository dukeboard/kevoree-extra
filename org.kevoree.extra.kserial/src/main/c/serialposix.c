/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 25/01/12
 * Time: 11:47

 Posix serial interface
 */

#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/time.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <signal.h>
#include <fcntl.h>
#include <termios.h>
#include <unistd.h>
#include "utils.h"
#include "serialposix.h"

#define BUFFER_SIZE 512

volatile int quitter;

typedef struct _contexte{
	int fd;
	const char *name_device;
} SerialContext;

void (*SerialEvent) (int taille,unsigned char *data);


static SerialContext ctx;


void handler_sigint(int sig)
{
	printf("CATCH SIGNAL EXIT ");
	quitter = 1;
	close_serial(ctx.fd);
	exit(sig);
}


int init(int fd,const char *name_device)
{
	int rt;
	pthread_t monitor;
	signal(SIGINT, handler_sigint);
	signal(SIGKILL, handler_sigint);
	ctx.fd = fd;
	ctx.name_device =name_device;

	rt = pthread_create (& monitor, NULL,&serial_monitoring, NULL);
	if(rt != 0)
	{
		close(fd);
		return rt;
	}
	return 0;
}


int register_SerialEvent( void* fn){
	SerialEvent=fn;
	return 0;
};


void *serial_monitoring()
{
	char byte[BUFFER_SIZE];
    int fd;
	while(quitter ==0)
	{
        if((fd = open(ctx.name_device,O_RDONLY)) == -1)
        {
                SerialEvent(-1,"BROKEN LINK\n");
                close_serial(ctx.fd);
        }else {
        close(fd);
        }

		usleep(3000);
	}

	pthread_exit(NULL);
}


void *serial_reader()
{
	char byte[BUFFER_SIZE];

	int taille;
	while(quitter ==0)
	{
		if((taille =serialport_read(ctx.fd,byte)) > 0)
		{
			SerialEvent(taille,byte);
			memset(byte,0,sizeof(byte));
		}

	}
	pthread_exit(NULL);
}

/**
 * Open a file descriptor
 * @param
 * @param
 */
int reader_serial(int fd){
 	pthread_t reader;
	int rt;
	rt = pthread_create (& reader, NULL,&serial_reader, NULL);
	if(rt != 0)
	{
	    close(ctx.fd);
		return rt;
	}
}




/**
 * Open a file descriptor
 * @param
 * @param
 */
int open_serial(const char *_name_device,int _bitrate){


	int fd, err, rc, bitrate;
	struct termios termios;
	struct timeval tv;
	fd_set fds;
	struct flock fl;
	quitter = 0;


	//printf("Opening serial device %s %d \n", _name_device,_bitrate);

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

	/* open the serial device */
	fd = open(_name_device,O_RDWR | O_NOCTTY | O_NONBLOCK);

	if(fd < 0) {
		close(fd);
		return -2;
	}
	if(init(fd,_name_device) != 0) { return -11; }

	/* get attributes and fill termios structure */
	err = tcgetattr(fd, &termios);
	if(err < 0) {
		fprintf(stderr, "tcgetattr: %s\n", strerror(errno));
		close(fd);
		return  -3;
	}

	/* set input baud rate */
	err = cfsetispeed(&termios, bitrate);
	if(err < 0) {
		fprintf(stderr, "cfsetispeed: %s\n", strerror(errno));
		close(fd);
		return  -4;
	}

	/* set baud rate */
	err = cfsetspeed(&termios, bitrate);
	if(err < 0) {
		fprintf(stderr, "cfsetspeed: %s\n", strerror(errno));
		close(fd);
		return  -4;
	}

   	 //No parity (8N1):
    termios.c_cflag &= ~PARENB;
    termios.c_cflag &= ~CSTOPB;
    termios.c_cflag &= ~CSIZE;
    termios.c_cflag |= CS8;

    // no flow control
    termios.c_cflag &= ~CRTSCTS;

    termios.c_cflag |= CREAD | CLOCAL;  // turn on READ & ignore ctrl lines
    termios.c_iflag &= ~(IXON | IXOFF | IXANY); // turn off s/w flow ctrl

    termios.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG); // make raw
    termios.c_oflag &= ~OPOST; // make raw

    // see: http://unixwiz.net/techtips/termios-vmin-vtime.html
    termios.c_cc[VMIN]  = 0;
    termios.c_cc[VTIME] = 0;


    if( tcsetattr(fd, TCSANOW, &termios) < 0) {
        perror("init_serialport: Couldn't set term attributes");
        return -5;
    }

	/* lock the file */
	fl.l_type = F_WRLCK | F_RDLCK;
	fl.l_whence = SEEK_SET;
	fl.l_start = 0;
	fl.l_len = 0;
	fl.l_pid = getpid();

	if (fcntl(fd, F_SETLK, &fl) == -1) {
		close(fd);
		return  -7;
	}
	
    fcntl(fd, F_SETFL, FNDELAY);

	rc = fcntl(fd, F_GETFL, 0);
	if (rc != -1)
    		fcntl(fd, F_SETFL, rc & ~O_NONBLOCK);

	/* flush the serial device */
	tcflush(fd, TCIOFLUSH);

	return fd;

}

int close_serial(int fd){
	close(fd);
	quitter = 1;
}


/**
 * Open a file descriptor
 * @param
 * @param
 */
int serialport_writebyte( int fd, uint8_t b)
{
	int n = write(fd,&b,1);
	if( n!=1)
		return -1;
	return 0;
}

/**
 * Open a file descriptor
 * @param
 * @param
 */
int serialport_readbyte( int fd, uint8_t *b)
{
	int n = read(fd,&b,1);
	if( n!=1)
		return -1;
	return 0;
}

/**
 * Open a file descriptor
 * @param
 * @param
 */
int serialport_write(int fd,  char* str)
{
	int len,n;
	len= strlen(str);

	n = write(fd, str, len);
	if( n!=len )
		return -1;
	return serialport_writebyte(fd,'\n'); /* finish */
}

/**
 * Open a file descriptor
 * @param
 * @param
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

	} while( b[0] != '\n'); /* detect finish and protect overflow*/

	return i;
}


/**
 * Open a file descriptor
 * @param
 * @param
 */
int upload_program(int fd, char *hex_user_program){

	uint8_t boot_flag,ready_flag,start_flag;
	uint8_t flash_flag=6;
	int n ;

	int Memory_Address_High;
	int Memory_Address_Low;
	int Check_Sum;
	int last_memory_address;
	int current_memory_address;
	int page_size=128; 			// 64 words --> 128 bytes
	int flash_size=30720;			// The max flash size ATmega168 is 16384 bytes atmega368 30720 bytes
	unsigned char intel_hex_array[flash_size];

	strcpy(intel_hex_array,hex_user_program);
	//	printf("%s\n",intel_hex_array);



	last_memory_address = flash_size - 1;
	while(last_memory_address > 0 )
	{
		if(intel_hex_array[last_memory_address] != 255)
		{
			last_memory_address = last_memory_address - 1;
		}
		else
		{
			printf("last_memory_address is %d\n",last_memory_address);
			break;
		}
	}

	printf("Waiting bootloader");
	do
	{
		printf(".");
		serialport_readbyte(fd,&boot_flag);
	}while( boot_flag !=5 );
	printf("\n");

	printf("Boot flag :%x\n",boot_flag);

	if(serialport_writebyte(fd,6) < 0)
	{
		printf("ERROR");
		exit(-1);
	}

	current_memory_address = 0;

	while((current_memory_address < last_memory_address))
	{

		printf("\nLoading : %d/%d\n",current_memory_address,last_memory_address);
		serialport_readbyte(fd,&ready_flag);
		if(ready_flag == 'T')
		{
			printf(" The bootloader is ready :%c\n",ready_flag);
		}else if(ready_flag = 7)
		{
			printf("Re-send line %d \n",ready_flag);
			current_memory_address = current_memory_address - page_size;
		}else
		{

			printf("Error : Incorrect response from target IC. Programming is incomplete and will now halt.");
			exit(-1);
		}

		//  Convert 16-bit current_memory_address into two 8-bit characters
		Memory_Address_High =(current_memory_address / 256);
		Memory_Address_Low = (current_memory_address % 256);

		printf("Memory_Address_High %d\nMemory_Address_Low %d\n",Memory_Address_High,Memory_Address_Low);

		//Calculate current check_sum
		Check_Sum = 0;
		Check_Sum = Check_Sum + page_size;
		Check_Sum = Check_Sum + Memory_Address_High; 	//'Convert high byte
		Check_Sum = Check_Sum + Memory_Address_Low; 	//'Convert low byte
		int i,j;
		for(i=0;i<page_size-1;i++)
		{
			Check_Sum = Check_Sum + intel_hex_array[current_memory_address + i];
		}

		//Now reduce check_sum to 8 bits
		while(Check_Sum > 256){
			Check_Sum = Check_Sum - 256;
		}

		//Now take 2's compliment
		Check_Sum = 256 - Check_Sum;



		printf("Send the start character :\n");
		if(serialport_writebyte(fd,':') < 0)
		{
			exit(-1);
		}


		printf("Send page_size %d\n",page_size);

		//Send the record length
		if(serialport_writebyte(fd,page_size) < 0){
			exit(-1);
		}


		printf("Send this block's address Low %d High %d\n",Memory_Address_Low,Memory_Address_High);
		if(serialport_writebyte(fd,Memory_Address_Low) < 0)
		{
			exit(-1);
		}
		if(serialport_writebyte(fd,Memory_Address_High) < 0)
		{	exit(-1);

		}


		printf("Send this block's check sum %d \n",Check_Sum);
		if(serialport_writebyte(fd,Check_Sum)< 0)
		{
			exit(-1);
		}

		//Send the block
		j=0;
		while(j < (page_size-1))
		{
			unsigned char block = 	intel_hex_array[current_memory_address + j];
			if(serialport_writebyte(fd,block) < 0)
			{
				exit(-1);
			}
			//printf("%c",block);

			j++;
		}

		current_memory_address = current_memory_address + page_size;
		sleep(1);
	}

	if(serialport_writebyte(fd,':') < 0)
	{
		exit(-1);
	}

	if(serialport_writebyte(fd,'S') < 0)
	{
		exit(-1);
	}
}

