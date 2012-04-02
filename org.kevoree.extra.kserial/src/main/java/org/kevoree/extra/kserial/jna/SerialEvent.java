package org.kevoree.extra.kserial.jna;
import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import org.kevoree.extra.kserial.SerialPort.SerialPortException;

/*
 * 
 * Callbacks c functions
 */
public interface SerialEvent extends Callback {

	void serial_reader_callback(int taille,Pointer data) throws SerialPortException;

} 


