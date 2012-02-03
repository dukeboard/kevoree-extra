#
# Created by jed
# User: jedartois@gmail.com
# Date: 02/02/12
# Time: 18:49
#

nix64:
	$(CC) -shared -fPIC -Wl,-soname,../../../resources/nix64/flash.so -o ../../../resources/nix64/flash.so  flash.c ../../../resources/nix64/serialposix.so -lpthread
	$(CC) test.c -o test  ../../../resources/nix64/flash.so ../../../resources/nix64/serialposix.so

