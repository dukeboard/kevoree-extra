package org.kevoree.extra.kserial;

import com.sun.jna.Structure;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 9:01
 */


public abstract class CommPort {
	protected int fd;
	protected String port_name;
	protected int port_bitrate;


	public abstract void open()throws SerialPortException;
	public abstract void close()throws SerialPortException;

	public abstract void write(byte[] data) throws SerialPortException;
}
