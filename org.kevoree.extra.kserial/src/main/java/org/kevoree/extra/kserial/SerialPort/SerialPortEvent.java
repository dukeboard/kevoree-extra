package org.kevoree.extra.kserial.SerialPort;

import com.sun.jna.Pointer;
import org.kevoree.extra.kserial.Utils.ByteFIFO;
import org.kevoree.extra.kserial.jna.NativeLoader;
import org.kevoree.extra.kserial.jna.SerialEvent;
import org.kevoree.extra.kserial.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.EventObject;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 9:01
 */

public class SerialPortEvent extends EventObject  implements SerialEvent {

    private static final long serialVersionUID = 1L;
    private SerialPort serialPort;
    private int pthreadid;
    private int monitorid;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public SerialPortEvent(SerialPort serialport) throws SerialPortException
    {
        super(serialport);
        this.serialPort = serialport;
    }

    public void subscribeNativeC(){
        try
        {
            NativeLoader.getInstance().register_SerialEvent(this);
            if((pthreadid=NativeLoader.getInstance().reader_serial(serialPort.getFd())) != 0)
            {
                NativeLoader.getInstance().close_serial(serialPort.getFd());
                serialPort.fireSerialEvent(new SerialPortDisconnectionEvent(serialPort));
            }
            if((monitorid=NativeLoader.getInstance().monitoring_serial(serialPort.getPort_name())) != 0)
            {
                NativeLoader.getInstance().close_serial(serialPort.getFd());
                serialPort.fireSerialEvent(new SerialPortDisconnectionEvent(serialPort));

            }
        }catch (Exception e){
           logger.error("subscribe native callback ",e);
        }
    }

    public void serial_reader_callback(int taille, Pointer data) throws SerialPortException {
        if(taille < 0)
        {
            if (taille == Constants.EXIT_CONCURRENT_VM)
            {
                NativeLoader.getInstance().register_SerialEvent(null);
                serialPort.fireSerialEvent(new SerialConcurrentOpenEvent(serialPort));
            } else if(taille == Constants.FD_DISCONNECTED)
            {
                if(serialPort != null)
                {
                    NativeLoader.getInstance().close_serial(serialPort.getFd());
                    serialPort.fireSerialEvent(new SerialPortDisconnectionEvent(serialPort));
                }
            }
        }
        else
        {
            try
            {
                if(serialPort.getFifo_in().free() < taille)
                {
                    // full create buffer larger
                      serialPort.setSizefifo_in(serialPort.getSizefifo_in()+512);
                    ByteFIFO tmp = new ByteFIFO(serialPort.getSizefifo_in());
                    tmp.add(serialPort.getFifo_in().removeAll());
                    serialPort.setFifo_in(tmp);
                }

                serialPort.getFifo_in().add(data.getByteArray(0, taille));
                serialPort.fireSerialEvent(this);

            } catch (InterruptedException e)
            {
                logger.error("Reading native callback",e);
            }
        }

    }

    public int getSize(){
        return serialPort.getFifo_in().getSize();
    }
    public byte[] read(){
        return serialPort.getFifo_in().removeAll();
    }

    public InputStream getInputStream() throws SerialPortException {
        return serialPort.getInputStream();
    }

}
