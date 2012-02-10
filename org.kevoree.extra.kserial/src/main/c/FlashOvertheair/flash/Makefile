#
# Created by jed
# User: jedartois@gmail.com
# Date: 02/02/12
# Time: 18:49
#

nix32:
	$(CC) -shared -fPIC -Wl,-soname,../../../resources/nix32/flash.so -o ../../../resources/nix32/flash.so  flash.c -lpthread -DNUX
	$(CC) test.c -o test  ../../../resources/nix32/flash.so

nix64:
	$(CC) -shared -fPIC -Wl,-soname,../../../resources/nix64/flash.so -o ../../../resources/nix64/flash.so  flash.c -lpthread -DNUX
	$(CC) test.c -o test  ../../../resources/nix64/flash.so

osx:
	$(CC) -shared -fPIC -Wl,-install_name,../../../resources/osx/flash.so -o ../../../resources/osx/flash.so  flash.c -lpthread -DOSX


clean :
	rm *.o *.so test
	../../../resources/osx/flash.so
	../../../resources/nix32/flash.so
	../../../resources/nix64/flash.so
