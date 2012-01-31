package org.kevoree.extra.kserial;

import java.util.EventObject;


import com.sun.jna.Pointer;
import org.kevoree.extra.kserial.jna.NativeLoader;
import org.kevoree.extra.kserial.jna.SerialEvent;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 9:01
 */

public class SerialPortEvent extends EventObject  implements SerialEvent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int sizefifo= 1024;
    private	ByteFIFO fifo_in = new ByteFIFO(sizefifo);
    private SerialPort SerialPort;
    private int pthreadid;

    public SerialPortEvent(SerialPort serialport) throws SerialPortException {
        super(serialport);
        this.SerialPort = serialport;



        NativeLoader.getInstance().register_SerialEvent(this);
        if((pthreadid=NativeLoader.getInstance().reader_serial(SerialPort.fd)) != 0)
        {
            throw new SerialPortException("callback reader");
        }



    }

    public void serial_reader_callback(int taille, Pointer data) throws SerialPortException {
        if(taille <= 0){
            NativeLoader.getInstance().close_serial(SerialPort.fd);
            throw new SerialPortException("Broken link");
        }
        else
        {
            try
            {
                if(fifo_in.free() < taille)
                {
                    // full create buffer larger
                    sizefifo = sizefifo+512;
                    ByteFIFO tmp = new ByteFIFO(sizefifo);
                    tmp.add(fifo_in.removeAll());
                    fifo_in = tmp;
                }

                fifo_in.add(data.getByteArray(0, taille));
                SerialPort.fireSerialEvent(this);

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }



    public int getSize(){
        return fifo_in.getSize();
    }
    public byte[] read(){
        return fifo_in.removeAll();
    }



}
