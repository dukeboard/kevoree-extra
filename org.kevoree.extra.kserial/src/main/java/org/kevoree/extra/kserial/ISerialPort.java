package org.kevoree.extra.kserial;

import org.kevoree.extra.kserial.SerialPort.SerialPortEventListener;
import org.kevoree.extra.kserial.SerialPort.SerialPortException;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 06/02/12
 * Time: 09:58
 */
public interface ISerialPort
{

    public  void open()throws SerialPortException;
    public void exit()throws SerialPortException;

    public void write(byte b) throws SerialPortException;
    public void write(byte[] data) throws SerialPortException;
    public int writeBytes(byte[] byteArray);
    public int writeByte(byte b);
    public void flush() throws SerialPortException;
    public void addEventListener (SerialPortEventListener listener);
    public void removeEventListener (SerialPortEventListener listener);

    public InputStream getInputStream() throws SerialPortException;
    public OutputStream getOutputStream() throws SerialPortException;


    public String getPort_name();
    public boolean autoReconnect (int tentative,SerialPortEventListener event) throws SerialPortException;

}
