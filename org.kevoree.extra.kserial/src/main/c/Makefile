
nix64:
	$(CC) -shared -fPIC -Wl,-soname,serialposix.so -o ../resources/nix64/serialposix.so serialposix.c

nix32:
	$(CC) -shared -fPIC -Wl,-soname,serialposix.so -o ../resources/nix32/serialposix.so serialposix.c

clean :
	rm *.o *.so test

osx:
	$(CC) -shared -fPIC -Wl,-install_name,serialposix.so -o ../resources/osx/serialposix.so serialposix.c
	
test:
	$(CC) test.c -o test ../resources/osx/serialposix.so -lpthread
