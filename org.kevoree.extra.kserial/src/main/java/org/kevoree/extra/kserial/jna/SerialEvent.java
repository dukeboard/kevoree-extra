package org.kevoree.extra.kserial.jna;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import org.kevoree.extra.kserial.SerialPort.SerialPortException;

/*
 * 
 * Callbacks c functions
 */
public interface SerialEvent extends Callback {
	
	public static final int DATA_AVAILABLE      =1;
	public static final int OUTPUT_BUFFER_EMPTY =2;
	public static final int CTS                 =3;
	public static final int DSR                 =4;
	public static final int RI                  =5;
	public static final int CD                  =6;
	public static final int OE                  =7;
	public static final int PE                  =8;
	public static final int FE                  =9;
	public static final int BI                 =10;
	
	
	void serial_reader_callback(int taille,Pointer data) throws SerialPortException;

} 


