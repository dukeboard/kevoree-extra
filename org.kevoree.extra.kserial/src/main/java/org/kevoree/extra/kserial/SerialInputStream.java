package org.kevoree.extra.kserial;

import org.kevoree.extra.kserial.SerialPort.SerialPort;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: jed
 * Date: 11/10/12
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
public class SerialInputStream extends InputStream
{
    private SerialPort serialPort=null;

    public SerialInputStream(SerialPort serialPort)
    {
        this.serialPort = serialPort;
    }

    @Override
    public int read() throws IOException {
        try
        {
            if(serialPort.getFifo_in().getSize() == 0){
                return  -1;
            } else {
                return  (int)serialPort.getFifo_in().remove();
            }
        } catch (InterruptedException e) {
            return  0;
        }
    }

    @Override
    public int read(byte b[]) throws IOException {

        if(serialPort.getFifo_in().getSize() == 0){
            return  -1;
        } else {
            b = serialPort.getFifo_in().removeAll();
            return  b.length;
        }
    }

}
