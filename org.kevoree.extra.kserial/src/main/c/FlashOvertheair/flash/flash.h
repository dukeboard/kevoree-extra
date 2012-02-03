/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 03/02/12
 * Time: 17:27
 */

#include "../../serialposix/serialposix.h"
#include <unistd.h>

#define PRINT_HEXA 0
#define PRINT_CHAR 1
#define MAX_SIZE_ID 5


#define ATMEGA328 0
#define ATMEGA1280 1
#define ATMEGA168 2

typedef struct _device {
	int  memsize;
	int pagesize;
} device;


unsigned char * parse_intel_hex(int taille,int *last_memory, unsigned char *src_hex_intel);