
clean :
	rm *.o *.so test

osx:
	$(CC) -shared -fPIC -Wl,-install_name,../resources/osx/serialposix.so -o ../resources/osx/serialposix.so serialposix.c
	$(CC) test.c -o test ../resources/osx/serialposix.so -lpthread
	
linux_32:
	$(CC) -shared -fPIC -Wl,-soname,../resources/nix32/serialposix.so -o ../resources/nix32/serialposix.so serialposix.c
	$(CC) test.c -o test ../resources/nix32/serialposix.so -lpthread

linux_64:
	$(CC) -shared -fPIC -Wl,-soname,../resources/nix64/serialposix.so -o ../resources/nix64/serialposix.so serialposix.c
	$(CC) test.c -o test ../resources/64/serialposix.so -lpthread
