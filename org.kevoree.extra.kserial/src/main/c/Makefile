
nix64:
	$(CC) -shared -fPIC -Wl,-soname,serialposix.so -o serialposix.so serialposix.c 
	sudo cp serialposix.so /usr/lib/libserialposix.so
	$(CC) test.c -o test -lserialposix -lpthread
	cp serialposix.so  ../resources/nix64

nix32:
	$(CC) -shared -fPIC -Wl,-soname,serialposix.so -o serialposix.so serialposix.c
	sudo cp serialposix.so /usr/lib/libserialposix.so
	$(CC) test.c -o test -lserialposix -lpthread
	cp serialposix.so  ../resources/nix32

clean :
	rm *.o *.so test

osx:
	$(CC) -shared -fPIC -Wl,-install_name,../resources/osx/serialposix.so -o ../resources/osx/serialposix.so serialposix.c
	$(CC) test.c -o test ../resources/osx/serialposix.so -lpthread
	cp serialposix.so  ../resources/osx
