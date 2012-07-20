/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 02/02/12
 * Time: 18:49
 */
#include "fota.h"
#include <sys/ipc.h>
#include <sys/shm.h>

#define _POSIX_SOURCE 1 /* POSIX compliant source */


// EVENTS

///#define DEBUG

/**
 * Open a file
 * @param  path to file
 * @param  Pointer of array
 * @return size of the file
 */
static int fd =0;
int shmid;
// memory shared
static int *quitter;


void (*FlashEvent) (int evt);






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


int HexToDec (char *str)
{
  int value;
  if (sscanf (str, "%x", &value) == 1)
    {
      return value;
    }
  else
    {
      return ERROR;
    }
}


int parseHex(char h,char l)
{
    static unsigned char buffer[2];
    memset(buffer,0,sizeof(buffer));
    buffer[0] = h;
    buffer[1] = l;
    return HexToDec(&buffer[0]);;
}

unsigned char * parse_intel_hex(int taille,int *last_memory, unsigned char *src_hex_intel)
{
    int i=0,j=0,y=0;
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
    // clean 
    memset(page,0,sizeof(page));

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
    do 
    {
        if(destination_intel_hex_array[i] != '\n' )
        {
            page[j] = destination_intel_hex_array[i];
            j++;
        }
        else
        {
            j=0;
            // extract the larger
            length = parseHex(page[0],page[1]);
            //printf("\n Record length : %d ",length);
            // extract  adr high
            memory_address_high = parseHex(page[2],page[3]);
            // extract  adr low
            memory_address_low =  parseHex(page[4],page[5]);
            memory_address = (memory_address_high * 256 + memory_address_low);
            if( memory_address + length  != 0)
            {
                last_memory_address = (memory_address + length);
                //    printf("last_memory_address  %d \n",    last_memory_address  );
                *last_memory =  last_memory_address;
            }
            for(y=0;y<length;y++)
            {
                lower_byte = (8+y*2);
                upper_byte = (9+y*2);
                //    printf("lower_byte %d\n upper_byte %d--> (%c%c) to base 10 (%d) \n",lower_byte,upper_byte,page[i+lower_byte],page[i+upper_byte],parseHex(page[lower_byte],page[upper_byte]));
                destination_intel_hex_array[memory_address + y] = parseHex(page[lower_byte],page[upper_byte]);
                //    printf("%x",destination_intel_hex_array[memory_address + y]);
            }
        }
        i++;
    }while(i < taille-1);
    return  destination_intel_hex_array;
}

void print_hex_array(int type,int taille,unsigned char *hex_array)
{
    if(hex_array != NULL){
        int i;
    //    printf("*********************************************\n");
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
    //    printf("*********************************************\n");
    } else {
        printf("WTF\n");
    }
}

int hex2bin(char *sHexString)
{
    int answer;
    char *pH;
    pH = sHexString;
    answer = 0;
    while (*pH != '\0')
    {
        if (*pH >= '0' && *pH <= '9') // it's a digit
            answer = (answer*16) + (*pH - '0');
        else if (*pH >= 'a' && *pH <= 'f') // it's a smallcase letter a to f
            answer = (answer*16) + (*pH - 'a') + 10;
        else if (*pH >= 'A' && *pH <= 'F') // it's a smallcase letter a to f
            answer = (answer*16) + (*pH - 'A') + 10;

        pH++;
    }
    return answer;
}

void close_flash()
{
    if(quitter !=NULL){
            *quitter = EXIT;
    }
     close(fd);
}


void *flash_firmware(Target *infos)
{
    int last_memory;
    unsigned char  octet;
    uint8_t boot_flag,ready_flag,start_flag;
    uint8_t flash_flag;
    int n ;
    int Memory_Address_High;
    int Memory_Address_Low;
    int Check_Sum;
    int current_memory_address;
    int page_size;
    int flash_size;
    char c;
    char flag_bootloader;
    char *addr;

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
        page_size = 168;
        flash_size = 131072;  // TODO
        break;
    default :
        FlashEvent(ERROR);
        close_flash();
        break;
    }

         // create memory shared
         shmid = shmget(JED_IPC_PRIVATE,sizeof(int), 0666 | IPC_CREAT );
         if(shmid < 0)
         {
             perror("shmid");
           FlashEvent(ERROR);
           close_flash();
         }
          addr = shmat(shmid, 0, 0);
          if(addr < 0)
          {
              perror("shmat");
           FlashEvent(ERROR);
           close_flash();
          }
         // bind to memory shared
        quitter = (int *) addr;
        if(quitter == NULL)
        {
                FlashEvent(ERROR);
                close_flash();
        }

        current_memory_address = 0;

        while((current_memory_address < infos->last_memory_address) && (*quitter == ALIVE))
        {

            FlashEvent(current_memory_address);
            //printf("\n %d/%d octets ",current_memory_address, infos->last_memory_address);
            ready_flag =  serialport_readbyte(fd);
            if(ready_flag == READY_BOOTLOADER_FLAG)
            {
                //printf(" The bootloader is ready :%c\n",ready_flag);
            }else if(ready_flag = RE_SEND_FLAG)
            {
                //printf("Re-send line %d \n",ready_flag);
                FlashEvent(RE_SEND_EVENT);
                current_memory_address = current_memory_address - page_size;
                if(current_memory_address  < 0) current_memory_address  =0;
            }else
            {
                FlashEvent(ERROR);
                // WTF ?!!
            }

            //  Convert 16-bit current_memory_address into two 8-bit characters
            Memory_Address_High =(current_memory_address / 256);
            Memory_Address_Low = (current_memory_address % 256);

            //printf("Memory_Address_High %d\n Memory_Address_Low %d \n",Memory_Address_High,Memory_Address_Low);

            //Calculate current check_sum
            Check_Sum = 0;
            Check_Sum = Check_Sum + page_size;
            Check_Sum = Check_Sum + Memory_Address_High;     //'Convert high byte
            Check_Sum = Check_Sum + Memory_Address_Low;     //'Convert low byte

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
                FlashEvent(ERROR_WRITE);
            }
        //    printf("Send page_size %d\n",page_size);
            c = page_size;
            //Send the record length
            if(serialport_writebyte(infos->fd,c) < 0){
                FlashEvent(ERROR_WRITE);
            }

        //    printf("Send this block's address Low %d \n",Memory_Address_Low);
            c=Memory_Address_Low;
            if(serialport_writebyte(infos->fd,c) < 0)
            {
                // perror("write byte mem low");
                FlashEvent(ERROR_WRITE);
            }
            c=Memory_Address_High;
            if(serialport_writebyte(infos->fd,Memory_Address_High) < 0)
            {
                //  perror("write byte mem high");
                FlashEvent(ERROR_WRITE);
            }

        //    printf("Send this block's check sum %d \n",Check_Sum);
            c=Check_Sum;
            if(serialport_writebyte(infos->fd,c)< 0)
            {
                //  perror("write byte check sum");
                FlashEvent(ERROR_WRITE);
            }

            //Send the block
            j=0;
            while(j < (page_size)  && (*quitter == ALIVE) )
            {
                unsigned char block =     infos->intel_hex_array[current_memory_address + j];
                if(serialport_writebyte(infos->fd,block) < 0)
                {
                      perror("write byte hex");
                    FlashEvent(ERROR_WRITE);
                }
            //    printf("%x",block);
                j++;
            }
            current_memory_address = current_memory_address + page_size;

        }

        int ack1,ack2,ack3;
        if(serialport_writebyte(infos->fd,':') < 0)
        {
            FlashEvent(ERROR_WRITE);
        }
        ack1 = serialport_readbyte(infos->fd);

        if(serialport_writebyte(infos->fd,BOOTLOADER_SAVE_FLAG) < 0)
        {
            FlashEvent(ERROR_WRITE);
        }
        ack2 = serialport_readbyte(infos->fd);
        if(serialport_writebyte(infos->fd,BOOTLOADER_SAVE_FLAG) < 0)
        {
            FlashEvent(ERROR_WRITE);
        }
        ack3 = serialport_readbyte(infos->fd);
        if(serialport_writebyte(infos->fd,BOOTLOADER_SAVE_FLAG) < 0)
        {
            FlashEvent(ERROR_WRITE);
        }


    close_flash();

    FlashEvent(FINISH);


    if(infos != NULL)
        free(infos);



    pthread_exit(NULL);
}

int write_on_the_air_program(char *port_device,int target,int taille,unsigned char *raw_intel_hex_array)
{
    pthread_t flash;
    struct termios tty;
    int status,i;
    char *addr;
    uint8_t boot_flag;
    int tentative = 0;
    Target *mytarget  = (Target*)malloc(sizeof(Target));
    strcpy(mytarget->port_device,port_device);
    mytarget->target =  target;
   // memset(mytarget->dest_node_id,0,sizeof(mytarget->dest_node_id));
   // strcpy(mytarget->dest_node_id,dest_node_id);
    mytarget->taille = taille;


    mytarget->intel_hex_array =  parse_intel_hex(mytarget->taille,&mytarget->last_memory_address,raw_intel_hex_array);


    if(mytarget->last_memory_address <= 0)
    {
        return ERROR;
    }


  // create memory shared
     shmid = shmget(JED_IPC_PRIVATE,sizeof(int), 0666 | IPC_CREAT );
     if(shmid < 0)
     {
         perror("shmid");
         return ERROR;
     }
      addr = shmat(shmid, 0, 0);
      if(addr < 0)
      {
          perror("shmat");
         return ERROR;
      }
     // bind to memory shared
    quitter = (int *) addr;
    if(quitter == NULL)
    {
          return ERROR;
    }

    if(*quitter == ALIVE)
    {
       *quitter = EXIT;
       sleep(2);
    }


    *quitter =ALIVE;

 RESTART:

    fd = open(mytarget->port_device, O_RDWR);

    tcgetattr(fd, &tty);
    tty.c_iflag       = INPCK;
    tty.c_lflag       = 0;
    tty.c_oflag       = 0;
    tty.c_cflag       = CREAD | CS8 | CSTOPB;
    tty.c_cc[ VMIN ]  = 0;
    tty.c_cc[ VTIME ] = 1;
    cfsetispeed(&tty, B19200);
    cfsetospeed(&tty, B19200);
    tcsetattr(fd, TCSANOW, &tty);

    do
    {
        boot_flag =  serialport_readbyte(fd);
       // FlashEvent(EVENT_WAITING_BOOTLOADER);
        usleep(1000);
    }while( boot_flag !=BOOTLOADER_STARTED && *quitter == ALIVE);

    if(serialport_writebyte(fd,BOOT_INTO_BOOTLOADER_FLAG) < 0)
    {
     //   FlashEvent(ERROR_WRITE);
    }
    boot_flag =  serialport_readbyte(fd);
    if(boot_flag == BOOTLOADER_STARTED )
    {
        close(fd);
        usleep(1000);
        tentative++;
        if(tentative > 5)
        {
             // return FAIL_TO_BOOT_INTO_BOOTLOADER;
        }
        goto RESTART;
    }

    mytarget->fd = fd;
    if(pthread_create (& flash, NULL,&flash_firmware, mytarget) != 0){
        return ERROR;
    }

    return mytarget->last_memory_address;
}

uint8_t  serialport_readbyte( int fd)
{
    char b;
    int n = read(fd,&b,1);
    if( n!=1){
            return ERROR;
    }

    return b;
}
int serialport_writebyte( int fd, char b)
{
    int n = write(fd,&b,1);
    if( n!=1){
            return ERROR;
    }
    return 0;
}