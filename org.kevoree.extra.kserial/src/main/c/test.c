

#include "serialposix.h"

void test(int fd,char *data)
{
 //printf(" %d %s \n",fd,data);
 if(fd == -1){ printf("ERRROR\n");}
}

int main(int argc,char ** argv){

	int	fd = open_serial(argv[1],19200);

	if(fd < 0)
	{
		printf("file descriptor %d \n",fd); 
		exit(-2);
	}

    register_SerialEvent( test);

	 char test2[512];

	printf("File DS :  %d\n",fd);


	if(reader_serial(fd) != 0)
	{
	    printf("ERROR\n");
	}
	 monitoring_serial(argv[1]);

	
    sleep(10);
    printf("close\n");
    close(fd);
    exit(0);
	return 0;
}
