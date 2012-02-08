/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 02/02/12
 * Time: 18:49
 */
#include "flash.h"



#define _POSIX_SOURCE 1 /* POSIX compliant source */


//#define DEBUG

/**
 * Open a file
 * @param  path to file
 * @param  Pointer of array
 * @return size of the file
 */
static int flash_exit =0;
static int fd =0;


void (*FlashEvent) (int taille);



/**
 *  Assign function
 * @param fn pointer to the callback
 */
int register_FlashEvent( void* fn){
	FlashEvent=fn;
	return 0;
};


int open_file(char *path,unsigned char *hex_intel){

	FILE* file = NULL;
	int ptr = 0;
	int i=0;
	file = fopen(path, "r");

	if (file != NULL)
	{
		ptr = fgetc(file);

		while (ptr != EOF)
		{
			hex_intel[i]= ptr;
			ptr = fgetc(file);
			i++;
		}

		fclose(file);
	}

	return i;

}


unsigned int hex2dec( char *s )
{
	unsigned int v = 0;
	char *p;

	for( p = s; *p; p++ )
	{
		if( isdigit( *p ) )
			v = (v * 16) + (*p - '0');
		else
		{
			*p = tolower( *p );
			if( *p >= 'a' && *p <= 'f' )
				v = (v * 16) + 10 + (*p - 'a');
			else
				return -1;
		}
	}
	return v;
}


int HexToDec(char * str)
{
	unsigned int val = 0;
	char c;
	while(c = *str++)
	{
		val <<= 4;

		if (c >= '0' && c <= '9')
		{
			val += c & 0x0F;
			continue;
		}
		c &= 0xDF;
		if (c >= 'A' && c <= 'F')
		{
			val += (c & 0x07) + 9;
			continue;
		}

		errno = EINVAL;
		return 0;
	}

	return val;
}


int parseHex(char h,char l)
{
	char buffer[2];
	buffer[0] = h;
	buffer[1] = l;
	return HexToDec(buffer);
}


unsigned char * parse_intel_hex(int taille,int *last_memory, unsigned char *src_hex_intel)
{
	int i=0,j=0,y=0,ptr=0;
	int last_memory_address=0;
	int memory_address_high=0;
	int memory_address_low=0;
	int  memory_address=0;
	unsigned char page[taille];
	int length;
	int lower_byte;
	int upper_byte;


	// create a clean array
	unsigned char *destination_intel_hex_array= (unsigned char*)malloc(sizeof(unsigned char)*taille);
	for(i=0;i<taille;i++)
	{
		destination_intel_hex_array[i] = 255; // FF
	}


	// cleaning step
	i=0;
	while(i < taille)
	{


		if(src_hex_intel[i] != ':' && src_hex_intel[i] != '\r')
		{
			destination_intel_hex_array[j] = src_hex_intel[i];
			j++;
		}
		i++;
	}
	// parsing hex_intel
	i=0;
	j=0;
	do {

		if(destination_intel_hex_array[i] != '\n')
		{
			page[j] = destination_intel_hex_array[i];
			//printf("%d\n",j);
			j++;
		}else
		{
			j=0;
			// extract the larger
			length = parseHex(page[0],page[1]);
#ifdef DEBUG
		//	printf("\n Record length : %d ",length);
#endif
			// extract  adr high
			memory_address_high = parseHex(page[2],page[3]);
			// extract  adr low
			memory_address_low =  parseHex(page[4],page[5]);
			memory_address = memory_address_high * 256 + memory_address_low;

			int tmp =   memory_address + length;

			if(memory_address + length != 0)
			{
				last_memory_address = memory_address + length;
				*last_memory =  last_memory_address;
			}
			for(y=0;y<length;y++)
			{
				lower_byte = 8+y*2;
				upper_byte = 9+y*2;
				//printf("lower_byte %d\n upper_byte %d--> (%c%c) to base 10 (%d) \n",lower_byte,upper_byte,data[i+lower_byte],data[i+upper_byte],parseHex(data[lower_byte],data[upper_byte]));
				destination_intel_hex_array[memory_address + y] = parseHex(page[lower_byte],page[upper_byte]);
#ifdef DEBUG
				printf("%x",destination_intel_hex_array[memory_address + y]);
#endif
			}

			ptr++;

		}
		i++;

	}while(i < taille);


	return  destination_intel_hex_array;
}

void print_hex_array(int type,int taille,unsigned char *hex_array)
{
	if(hex_array != NULL){
		int i;
		printf("*********************************************\n");
		for(i=0;i<taille;i++){


			if(hex_array[i] == '\n')
				printf("\n");
			else{
				if(type == PRINT_HEXA){
					printf("%x",hex_array[i]);
				}else {
					printf("%c",hex_array[i]);
				}
			}


		}
		printf("*********************************************\n");
	} else {
		printf("ERROR\n");

	}

}

void close_flash(){

	if(flash_exit == 0)
		close(fd);

	flash_exit = 1;

}


void *flash_firmware(Target *infos)
{
	int last_memory;
	unsigned char  octet;
	uint8_t boot_flag,ready_flag,start_flag;
	uint8_t flash_flag=6;
	int n ;
	int Memory_Address_High;
	int Memory_Address_Low;
	int Check_Sum;
	int current_memory_address;
	int page_size;
	int flash_size;
	char c;
	char NODE_ID[MAX_SIZE_ID];

	// customize for target
	switch(infos->target)
	{
	case ATMEGA328:
		page_size = 128;            // 64 words
		flash_size = 30720;     // The max flash
		break;
	case ATMEGA1280:
		page_size = 256;
		flash_size = 131072;
		break;

	case ATMEGA168:

		break;
	default :
		FlashEvent(-32);
		close_flash();
		break;
	}


	usleep(1000);
	if(serialport_writebyte(infos->fd,'r') < 0)
	{
		//printf("ERROR\n");
		FlashEvent(-7);
		close_flash();
	}
	usleep(500);
	//printf("Waiting bootloader %d \n",last_memory_address );
	do
	{


		boot_flag =  serialport_readbyte(infos->fd);
		FlashEvent(-35);
		usleep(1000);

	}while( boot_flag !=5 && flash_exit == 0);

	FlashEvent(-29);

	//printf("Bootloader Ready ! \n");

	if(serialport_writebyte(infos->fd,6) < 0)
	{

		FlashEvent(-32);
		close_flash();
	}

	int i=0;
	for(i=0;i<MAX_SIZE_ID;i++)
	{
		NODE_ID[i]= serialport_readbyte(infos->fd);

		if((NODE_ID[i]) != infos->dest_node_id[i])
		{
			close_flash();
			FlashEvent(-33);
		}
		if(NODE_ID[i] == '\n')
		{
		 break;
		}
	}

	printf("FLASH <%s>\n",NODE_ID);

	current_memory_address = 0;

	while((current_memory_address < infos->last_memory_address) && (flash_exit == 0))
	{

		FlashEvent(current_memory_address);
    	//printf("\n %d/%d octets ",current_memory_address, infos->last_memory_address);
		ready_flag =  serialport_readbyte(fd);
		if(ready_flag == 'T')
		{
			//printf(" The bootloader is ready :%c\n",ready_flag);
		}else if(ready_flag = 7)
		{
			//printf("Re-send line %d \n",ready_flag);
			FlashEvent(-36);
			current_memory_address = current_memory_address - page_size;
			if(current_memory_address  < 0) current_memory_address  =0;
		}else
		{

			close_flash();
			FlashEvent(-34);
		}


		//  Convert 16-bit current_memory_address into two 8-bit characters
		Memory_Address_High =(current_memory_address / 256);
		Memory_Address_Low = (current_memory_address % 256);

		//printf("Memory_Address_High %d\n Memory_Address_Low %d \n",Memory_Address_High,Memory_Address_Low);

		//Calculate current check_sum
		Check_Sum = 0;
		Check_Sum = Check_Sum + page_size;
		Check_Sum = Check_Sum + Memory_Address_High; 	//'Convert high byte
		Check_Sum = Check_Sum + Memory_Address_Low; 	//'Convert low byte

		int i,j;
		for(i=0;i<page_size;i++)
		{
			Check_Sum = Check_Sum + infos->intel_hex_array[current_memory_address + i];
		}

		//Now reduce check_sum to 8 bits
		while(Check_Sum > 256){
			Check_Sum = Check_Sum - 256;
		}

		//Now take 2's compliment
		Check_Sum = 256 - Check_Sum;



		//printf("Send the start character :\n");
		if(serialport_writebyte(infos->fd,':') < 0)
		{

			FlashEvent(-7);
			close_flash();
		}


		//printf("Send page_size %d\n",page_size);
		c = page_size;
		//Send the record length
		if(serialport_writebyte(infos->fd,c) < 0){
			FlashEvent(-7);
			close_flash();
		}


		//printf("Send this block's address Low %d High %d\n",Memory_Address_Low,Memory_Address_High);
		c=Memory_Address_Low;
		if(serialport_writebyte(infos->fd,c) < 0)
		{
			FlashEvent(-7);
			close_flash();
		}
		c=Memory_Address_High;
		if(serialport_writebyte(infos->fd,Memory_Address_High) < 0)
		{
			FlashEvent(-7);
			close_flash();

		}


		//printf("Send this block's check sum %d \n",Check_Sum);
		c=Check_Sum;
		if(serialport_writebyte(infos->fd,c)< 0)
		{
			FlashEvent(-7);
			close_flash();
		}

		//Send the block
		j=0;
		while(j < (page_size))
		{
			char block = 	infos->intel_hex_array[current_memory_address + j];
			if(serialport_writebyte(infos->fd,block) < 0)
			{
				FlashEvent(-7);
				close_flash();
			}
			//printf("%c",block);

			j++;
		}

		current_memory_address = current_memory_address + page_size;

	}

	if(serialport_writebyte(fd,':') < 0)
	{
		FlashEvent(-7);
		close_flash();
	}

	if(serialport_writebyte(fd,'S') < 0)
	{
		FlashEvent(-7);
		close_flash();
	}
	if(serialport_writebyte(fd,'S') < 0)
	{
		FlashEvent(-7);
		close_flash();
	}
	if(serialport_writebyte(fd,'S') < 0)
	{
		FlashEvent(-7);
		close_flash();
	}

	FlashEvent(-38);

	if(infos != NULL)
		free(infos);
	if(infos->intel_hex_array != NULL)
		free(infos->intel_hex_array);

	close_flash();

	pthread_exit(NULL);
}

int write_on_the_air_program(char *port_device,int target,char *dest_node_id,int taille,unsigned char *raw_intel_hex_array)
{
	pthread_t flash;
	struct termios original;
	struct termios parametres;
	int status;
	Target *mytarget  = (Target*)malloc(sizeof(Target));
	strcpy(mytarget->port_device,port_device);
	mytarget->target =  target;
	strcpy(mytarget->dest_node_id,dest_node_id);
	mytarget->taille = taille;


	mytarget->intel_hex_array =  parse_intel_hex(mytarget->taille,&mytarget->last_memory_address,raw_intel_hex_array);

    if(mytarget->last_memory_address == 0)
    {

      printf("taille %d %d  ",	mytarget->taille,mytarget->last_memory_address);

    return -50;
    }

	mytarget->fd = open(mytarget->port_device, O_RDWR, 0);
	if(fd < 0)
	{
		close_flash();
		return -2;
	}

	printf("%d\n",fd);
	fd = mytarget->fd;
	flash_exit =0;

	tcgetattr(mytarget->fd, & original);
	tcgetattr(mytarget->fd, & parametres);
	cfmakeraw(& parametres);
	cfsetispeed(& parametres, B19200);
	cfsetospeed(& parametres, B19200);

	//No parity (8N1):
	parametres.c_cflag &= ~PARENB;
	parametres.c_cflag &= ~CSTOPB;
	parametres.c_cflag &= ~CSIZE;
	parametres.c_cflag |= CS8;

	// no flow control
	parametres.c_cflag &= ~CRTSCTS;
	parametres.c_cflag |= CREAD | CLOCAL | IXON;  // turn on READ & ignore ctrl lines
	parametres.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG); // make raw

	parametres.c_oflag &= ~OPOST; // make raw

	// see: http://unixwiz.net/techtips/termios-vmin-vtime.html
	parametres.c_cc[VMIN]  = 0;
	parametres.c_cc[VTIME] = 10;


	if (tcsetattr(mytarget->fd, TCSANOW, & parametres) != 0) {
		perror("tcsetattr");
		close_flash();
		return  -4;
	}

	/* flush the serial device */
	tcflush(mytarget->fd, TCIOFLUSH);

	// Set RTS
	ioctl(mytarget->fd, TIOCMGET, &status);
	status |= TIOCM_RTS;
	ioctl(fd, TIOCMSET, &status);

	// Set DTR
	ioctl(mytarget->fd, TIOCMGET, &status);
	status |= TIOCM_DTR;
	ioctl(mytarget->fd, TIOCMSET, &status);


	/* flush the serial device */
	tcflush(mytarget->fd, TCIOFLUSH);

	return  pthread_create (& flash, NULL,&flash_firmware, mytarget);
}


uint8_t  serialport_readbyte( int fd)
{
	uint8_t b;
	int n = read(fd,&b,1);
	if( n!=1)
		return -1;
	return b;
}
int serialport_writebyte( int fd, uint8_t b)
{
	int n = write(fd,&b,1);
	if( n!=1)
		return -1;
	return 0;
}