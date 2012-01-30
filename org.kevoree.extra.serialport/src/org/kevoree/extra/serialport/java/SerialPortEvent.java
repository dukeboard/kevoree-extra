package org.daum.ArduinoFOA;

import java.io.ByteArrayOutputStream;


import java.io.IOException;
import java.io.OutputStream;
import java.util.EventObject;

import org.daum.ArduinoFOA.jna.SerialEvent;
import org.daum.ArduinoFOA.jna.SerialPortJNA;

import com.sun.jna.Pointer;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 9:01
 */

public class SerialPortEvent extends EventObject  implements SerialEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int sizefifo= 1024;
	private	ByteFIFO fifo_in = new ByteFIFO(sizefifo);
	private SerialPort SerialPort;
	private int pthreadid;

	public SerialPortEvent(SerialPort serialport) throws SerialPortException {
		super(serialport);
		this.SerialPort = serialport;
		SerialPortJNA.INSTANCE.register_SerialEvent(this);

		if((pthreadid=SerialPortJNA.INSTANCE.reader_serial(SerialPort.fd)) != 0)
		{
			throw new SerialPortException("callback reader");
		}
	}

	@Override
	public void serial_reader_callback(int taille, Pointer data) {
		try 
		{
			if(fifo_in.free() < taille)
			{
				// full create buffer larger
				sizefifo = sizefifo+512;
				ByteFIFO tmp = new ByteFIFO(sizefifo);
				tmp.add(fifo_in.removeAll());
				fifo_in = tmp;
			}

			fifo_in.add(data.getByteArray(0, taille));
			SerialPort.fireSerialEvent(this);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getSize(){
		return fifo_in.getSize();
	}
	public byte[] read(){
		return fifo_in.removeAll();
	}


}
