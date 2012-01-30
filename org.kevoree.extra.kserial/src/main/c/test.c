#include "serialposix.h"

int main(int argc,char ** argv){

	printf(" bfore \n");

	int	fd = open_serial(argv[1],115200);

if(fd < 0)
{
	printf("file descriptor %d \n",fd); 
	return -1;
}
char test[2048];
	while(1){
		memset(test,0,sizeof(test));
		serialport_read(fd,&test[0]);
		printf("%s\n",test);
		usleep(1000);
	}
	close_serial(fd);


	return 0;
}
