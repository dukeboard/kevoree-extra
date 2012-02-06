package org.kevoree.extra.kserial.SerialPort;


/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 9:01
 */

public interface SerialPortEventListener extends java.util.EventListener
{
	  void incomingDataEvent(SerialPortEvent evt);

	void disconnectionEvent(SerialPortDisconnectionEvent evt);

}
