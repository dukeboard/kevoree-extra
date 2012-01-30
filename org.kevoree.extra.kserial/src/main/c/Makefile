
lib: 
	$(CC) -shared -fPIC -Wl,-soname,serialposix.so -o serialposix.so serialposix.c semaphore.c

deploy:
	sudo cp serialposix.so /usr/lib/libserialposix.so

	
test : 
	$(CC) test.c -o test -lserialposix -lpthread

clean :
	rm *.o *.so test

osx:
	$(CC) -shared -fPIC -Wl,-install_name,../resources/osx/serialposix.so -o ../resources/osx/serialposix.so serialposix.c semaphore.c
	$(CC) test.c -o test ../resources/osx/serialposix.so -lpthread