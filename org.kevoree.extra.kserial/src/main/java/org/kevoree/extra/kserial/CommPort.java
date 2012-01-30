package org.kevoree.extra.kserial;

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
	
	
	public static final int  DATABITS_5             =5;
	public static final int  DATABITS_6             =6;
	public static final int  DATABITS_7             =7;
	public static final int  DATABITS_8             =8;
	public static final int  PARITY_NONE            =0;
	public static final int  PARITY_ODD             =1;
	public static final int  PARITY_EVEN            =2;
	public static final int  PARITY_MARK            =3;
	public static final int  PARITY_SPACE           =4;
	public static final int  STOPBITS_1             =1;
	public static final int  STOPBITS_2             =2;
	public static final int  STOPBITS_1_5           =3;
	public static final int  FLOWCONTROL_NONE       =0;
	public static final int  FLOWCONTROL_RTSCTS_IN  =1;
	public static final int  FLOWCONTROL_RTSCTS_OUT =2;
	public static final int  FLOWCONTROL_XONXOFF_IN =4;
	public static final int  FLOWCONTROL_XONXOFF_OUT=8;

	
	public abstract void open()throws SerialPortException;
	public abstract void close()throws SerialPortException;

	public abstract void write(byte[] data) throws SerialPortException;
}
