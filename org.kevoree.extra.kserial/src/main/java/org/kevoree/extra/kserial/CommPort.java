package org.kevoree.extra.kserial;

import org.kevoree.extra.kserial.SerialPort.SerialPortException;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 9:01
 */


public abstract class CommPort extends Port
{
	public abstract void write(byte[] data) throws SerialPortException;
   // public abstract byte[] read() throws SerialPortException;
}
