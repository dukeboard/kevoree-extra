package org.kevoree.extra.kserial.SerialPort;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 31/01/12
 * Time: 10:55
 *
 * @author Erwan Daubert
 * @version 1.0
 */
public class SerialPortDisconnectionEvent extends SerialPortEvent {
	public SerialPortDisconnectionEvent (SerialPort serialport) throws SerialPortException {
		super(serialport);
	}
}
