package org.kevoree.extra.kserial;

import org.kevoree.extra.kserial.SerialPort.SerialPortException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: jed
 * Date: 11/10/12
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */
public class SerialOutputStream extends OutputStream
{
    private ISerialPort serialPort=null;

    public SerialOutputStream(ISerialPort serialPort)
    {
        this.serialPort = serialPort;
    }

    @Override
    public void write(int i) throws IOException {
        try
        {
            serialPort.write((byte)i);
        } catch (SerialPortException e) {
          e.printStackTrace();
        }
    }

    @Override
    public void write( byte b[] ) throws IOException
    {
        try
        {
            serialPort.write(b);
        } catch (SerialPortException e) {
            throw new IOException();
        }
    }

     @Override
    public void write( byte b[], int off, int len )throws IOException
    {
        try
        {
            serialPort.write(b);
        } catch (SerialPortException e) {
            throw new IOException();
        }
    }

    @Override
    public void flush() throws IOException
    {
        try
        {
            serialPort.flush();
        } catch (SerialPortException e) {
            throw new IOException();
        }
    }


}