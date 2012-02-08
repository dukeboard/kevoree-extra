#
# Created by jed
# User: jedartois@gmail.com
# Date: 01/02/12
# Time: 9:00
#

nix64:
	$(CC) -shared -fPIC -Wl,-soname,../../resources/nix64/serialposix.so -o ../../resources/nix64/serialposix.so serialposix.c
	$(CC) test.c -o test  ../../resources/nix64/serialposix.so -lpthread

nix32:
	$(CC) -shared -fPIC -Wl,-soname,../../resources/nix32/serialposix.so -o ../../resources/nix32/serialposix.so serialposix.c
	#$(CC) ../test.c -o test  ../../resources/nix32/serialposix.so -lpthread

osx:
	$(CC) -shared -fPIC -Wl,-install_name,../../resources/osx/serialposix.so -o ../../resources/osx/serialposix.so serialposix.c
	$(CC) test.c -o test  ../../resources/osx/serialposix.so -lpthread
	


clean :
	rm *.o *.so test
	../../resources/osx/serialposix.so
	../../resources/nix32/serialposix.so
	../../resources/nix64/serialposix.so