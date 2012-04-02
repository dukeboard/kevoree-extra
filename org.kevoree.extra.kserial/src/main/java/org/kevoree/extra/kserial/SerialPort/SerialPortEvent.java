package org.kevoree.extra.kserial.SerialPort;

import com.sun.jna.Pointer;
import org.kevoree.extra.kserial.Utils.ByteFIFO;
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
    private int monitorid;

    public SerialPortEvent(SerialPort serialport) throws SerialPortException {
        super(serialport);
        this.serialPort = serialport;


        NativeLoader.getINSTANCE_SerialPort().register_SerialEvent(this);

        if((pthreadid=NativeLoader.getINSTANCE_SerialPort().reader_serial(serialPort.getFd())) != 0)
        {
            NativeLoader.getINSTANCE_SerialPort().close_serial(serialPort.getFd());
            serialPort.fireSerialEvent(new SerialPortDisconnectionEvent(serialPort));
        }

        if((monitorid=NativeLoader.getINSTANCE_SerialPort().monitoring_serial(serialPort.getPort_name())) != 0)
        {
            NativeLoader.getINSTANCE_SerialPort().close_serial(serialPort.getFd());
            serialPort.fireSerialEvent(new SerialPortDisconnectionEvent(serialPort));
        }

    }

    public void serial_reader_callback(int taille, Pointer data) throws SerialPortException {
        if(taille < 0){
            System.out.println("Message = "+taille);
            NativeLoader.getINSTANCE_SerialPort().close_serial(serialPort.getFd());
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
