package org.kevoree.extra.kserial.SerialPort;

import com.sun.jna.Memory;
import com.sun.jna.ptr.PointerByReference;
import org.kevoree.extra.kserial.CommPort;
import org.kevoree.extra.kserial.Constants;
import org.kevoree.extra.kserial.Utils.ByteFIFO;
import org.kevoree.extra.kserial.Utils.KHelpers;
import org.kevoree.extra.kserial.jna.NativeLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 17:29

 * SerialPort
 */

public class SerialPort extends CommPort {

    private SerialPortEvent SerialPortEvent;
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
    private boolean exit = false;
    private int sizefifo_out = 1024;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ByteFIFO fifo_out = new ByteFIFO(sizefifo_out);

    public SerialPort (String portname, int bitrate) throws Exception {
        this.setPort_bitrate(bitrate);
        this.setPort_name(portname);
    }

    public void addEventListener (SerialPortEventListener listener) {
        if(listenerList != null){
            listenerList.add(SerialPortEventListener.class, listener);
        }
    }

    public void removeEventListener (SerialPortEventListener listener) {
        if(listenerList != null){
            listenerList.remove(SerialPortEventListener.class, listener);
        }
    }

    void fireSerialEvent (SerialPortEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == SerialPortEventListener.class) {
                if (evt instanceof SerialPortDisconnectionEvent) {
                    ((SerialPortEventListener) listeners[i + 1]).disconnectionEvent((SerialPortDisconnectionEvent)evt);
                } else {
                    ((SerialPortEventListener) listeners[i + 1]).incomingDataEvent(evt);
                }
            }
        }
    }

    public void storeData(byte[]bs){
        try
        {
            fifo_out.add(bs);
        } catch (InterruptedException e) {
            logger.error("The program has failed to backup data "+new String(bs));
        }
    }


    public void send(byte [] bs){
        Memory mem = new Memory(Byte.SIZE * bs.length + 1);
        mem.clear();

        PointerByReference inipar = new PointerByReference();
        inipar.setPointer(mem);
        for (int i = 0; i < bs.length; i++) {
            inipar.getPointer().setByte(i * Byte.SIZE / 8, bs[i]);
        }
        byte c = '\n';
        inipar.getPointer().setByte((bs.length + 1) * Byte.SIZE / 8, c);
        if (NativeLoader.getINSTANCE_SerialPort().serialport_write(getFd(), inipar) != 0)
        {
            logger.error("Warning fail to write store " + bs.length);
            storeData(bs);
        }
    }

    @Override
    public void write (byte[] bs)
    {
        if (this.getFd() > 0)
        {
            if(fifo_out.getSize() > 0)
            {
                send(fifo_out.removeAll());
            }
            send(bs);
        } else
        {
            logger.error("The program has failed write "+new String(bs)+" "+bs.length+" octects on "+this.getPort_name());
            storeData(bs);
        }
    }

    @Override
    public void open () throws SerialPortException {
        setFd(NativeLoader.getINSTANCE_SerialPort().open_serial(this.getPort_name(), this.getPort_bitrate()));
        if (getFd() < 0) {
            NativeLoader.getINSTANCE_SerialPort().close_serial(getFd());
            throw new SerialPortException(this.getPort_name()+"- [" + getFd() + "] " + Constants.messages.get(getFd())+" Ports : "+ KHelpers.getPortIdentifiers());
        }
        else
        {
            SerialPortEvent = new SerialPortEvent(this);
        }
    }

    public boolean autoReconnect (int tentative,SerialPortEventListener event) throws SerialPortException {
        close();
        removeEventListener(event);
        int i=0;
        while(i < tentative && exit == false)   {
            try
            {
                try
                {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    //ignore
                }
                open();
                addEventListener(event);
                return true;
            }catch (Exception e){
                logger.error("Try ["+i+"/"+tentative+" ]-> the program has not successfully reconnect automatically to port " + this.getPort_name());
                close();
            }
            i++;
        }
        return false;
    }

    @Override
    public void close () throws SerialPortException {
        NativeLoader.getINSTANCE_SerialPort().close_serial(getFd());
    }

    public void exit()
    {
        exit = true;
        try
        {
            close();
            listenerList = null;
            SerialPortEvent =null;
            fifo_out = null;
        } catch (SerialPortException e) {
            // ignore
        }
    }

}
