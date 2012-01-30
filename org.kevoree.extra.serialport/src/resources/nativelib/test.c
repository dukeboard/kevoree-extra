#include "serialposix.h"

int main(void){

	//signal(SIGINT, handler_sigint);
	int	fd = open_serial("/dev/ttyUSB1",115200);

	printf("%d\n",reader_serial(fd));


char test[1024];
	while(1){
		serialport_write(fd,"hello \n");
		serialport_read(fd,&test[0]);
		printf("%s\n",test);
		sleep(10000);
	}
	close(fd);


	return 0;
}
