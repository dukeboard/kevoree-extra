package org.daum.ArduinoFOA;


/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 9:01
 */

public class Impl implements SerialPortEventListener {


	SerialPort serial;


	public Impl() throws Exception{


		serial = new SerialPort("/dev/ttyUSB1",115200);

		serial.open();
		serial.addEventListener(this);

		
	}
	
	
	@Override
	public void serialEvent(SerialPortEvent evt) 
	{
		System.out.println("event "+evt.getSize()+" octets <"+Constants.BytesToString(evt.read())+">");
		

	}



}
