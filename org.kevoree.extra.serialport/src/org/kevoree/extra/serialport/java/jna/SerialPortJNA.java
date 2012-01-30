package org.daum.ArduinoFOA.jna;

import java.util.HashMap;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

public interface SerialPortJNA extends Library
{
	
	int open_serial(String name_device,int _bitrate);
	
	int close_serial(int fd);
	
	int register_SerialEvent(SerialEvent callback);
	int reader_serial(int fd);
	int serialport_write(int fd,PointerByReference inipar);
	int upload_program(int fd, Pointer hex_program);

	
	SerialPortJNA INSTANCE = (SerialPortJNA) Native.loadLibrary("serialposix", SerialPortJNA.class, new HashMap());
}