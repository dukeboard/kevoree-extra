

#include "serialposix.h"

void test(int fd,char *data)
{
 printf(" %d %s \n",fd,data);
}

int main(int argc,char ** argv){

	int	fd = open_serial(argv[1],9600);

	if(fd < 0)
	{
		printf("file descriptor %d \n",fd); 
		exit(-2);
	}
            register_SerialEvent( test);

	 char test2[512];

	printf("File DS :  %d\n",fd);


	while(1){

	     memset(test2,0,sizeof(test2));

	if(serialport_read(fd,&test2[0]) > 0){
		
		printf("<%s> \n",test2);
	}
	
	
	}
	close_serial(fd);


	return 0;
}
