

#include "serialposix.h"
#include <stdio.h>
/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 02/02/12
 * Time: 11:47
 Posix serial interface
 */


static int fd=0;

void event(int fd,char *data)
{

     if(fd == -1)
     {
         printf("The device is not connected anymore \n");
         close_serial(fd);
     }
     else
     {
            printf("\t Event  %d octets  --> <%s> \n",fd,data);
     }
}



int main(int argc,char ** argv){
    //Port *ports= scan_fd();
    /*
     int i;
     for(i=0;i<MAX_PORTS;i++)
     {
        if(strlen(ports[i].devicename) >  0)
        printf("%s\n",ports[i].devicename);
     }
*/
	fd = open_serial(argv[1],115200);

	if(fd < 0)
	{
		printf("Serial device does not exist %d \n",fd);

	}

    register_SerialEvent( event);

	 char test2[512];

	printf("The device is open on the file descriptor : %d\n",fd);


	if(reader_serial(fd) != 0)
	{
	    printf("pthread reader_serial \n");
	}
	if(monitoring_serial(argv[1])!= 0)
	{
    	 printf("pthread monitoring \n");
    }

	
    sleep(10000);
    printf("close\n");
    close_serial(fd);

	return 0;
}
