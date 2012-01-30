
lib: 
	$(CC) -shared -fPIC -Wl,-soname,serialposix.so -o serialposix.so serialposix.c semaphore.c

deploy:
	sudo cp serialposix.so /usr/lib/libserialposix.so

	
test : 
	$(CC) test.c -o test -lserialposix -lpthread

clean :
	rm *.o test