package org.kevoree.extra.kserial;

import com.sun.jna.Pointer;
import org.kevoree.extra.kserial.jna.NativeLoader;
import org.kevoree.extra.kserial.jna.SerialEvent;

import java.util.EventObject;

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
    private SerialPort serialPort;
    private int pthreadid;

    public SerialPortEvent(SerialPort serialport) throws SerialPortException {
        super(serialport);
        this.serialPort = serialport;



        NativeLoader.getInstance().register_SerialEvent(this);



        if((pthreadid=NativeLoader.getInstance().reader_serial(serialPort.fd)) != 0)
        {
            throw new SerialPortException("callback reader "+pthreadid);
        }



    }

    public void serial_reader_callback(int taille, Pointer data) throws SerialPortException {
            System.out.println("event ");
        if(taille <= 0){
            NativeLoader.getInstance().close_serial(serialPort.fd);
//            throw new SerialPortException("Broken link");
			serialPort.fireSerialEvent(new SerialPortDisconnectionEvent(serialPort));
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
				serialPort.fireSerialEvent(this);

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
