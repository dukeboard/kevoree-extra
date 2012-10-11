package org.kevoree.extra.kserial.SerialPort;

import com.sun.jna.Memory;
import com.sun.jna.ptr.PointerByReference;
import org.kevoree.extra.kserial.CommPort;
import org.kevoree.extra.kserial.Constants;
import org.kevoree.extra.kserial.SerialInputStream;
import org.kevoree.extra.kserial.SerialOutputStream;
import org.kevoree.extra.kserial.Utils.ByteFIFO;
import org.kevoree.extra.kserial.Utils.KHelpers;
import org.kevoree.extra.kserial.jna.NativeLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 17:29

 * SerialPort
 */

public class SerialPort extends CommPort {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SerialPortEvent SerialPortEvent;
    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
    private boolean exit = false;
    private int sizefifo_out = 1024;
    private int sizefifo_in = 1024;
    private ByteFIFO fifo_out = new ByteFIFO(sizefifo_out);
    private	ByteFIFO fifo_in = new ByteFIFO(sizefifo_in);
    private SerialOutputStream outputStream = null;
    private SerialInputStream inputStream = null;

    public SerialPort (String portname, int bitrate) throws Exception
    {
        outputStream =  new SerialOutputStream(this);
        inputStream = new SerialInputStream(this);

        if(portname.equals("*"))
        {
            if(KHelpers.getPortIdentifiers().size() > 0)
            {
                this.setPort_name(KHelpers.getPortIdentifiers().get(0));
            }
            else
            {
                this.setPort_name("/dev/null");
            }

        } else {
            this.setPort_name(portname);
        }
        this.setPort_bitrate(bitrate);

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

    protected void fireSerialEvent (SerialPortEvent evt)
    {
        if(listenerList !=null ) {
            Object[] listeners = listenerList.getListenerList();
            if(listeners != null)
            {
                for (int i = 0; i < listeners.length; i += 2) {
                    if (listeners[i] == SerialPortEventListener.class)
                    {
                        if (evt instanceof SerialPortDisconnectionEvent)
                        {
                            ((SerialPortEventListener) listeners[i + 1]).disconnectionEvent((SerialPortDisconnectionEvent)evt);
                        } else if(evt instanceof SerialConcurrentOpenEvent)
                        {
                            ((SerialPortEventListener) listeners[i + 1]).concurrentOpenEvent((SerialConcurrentOpenEvent) evt);
                        }else {
                            ((SerialPortEventListener) listeners[i + 1]).incomingDataEvent(evt);
                        }
                    }
                }
            }
        }

    }

    private void storeData(byte[]bs){
        try
        {
            fifo_out.add(bs);
        } catch (InterruptedException e) {
            logger.error("The program has failed to backup data "+new String(bs));
        }
    }


    private void writeNative(byte[] bs) throws SerialPortException {
        Memory mem = new Memory(Byte.SIZE * bs.length + 1);
        mem.clear();

        PointerByReference inipar = new PointerByReference();
        inipar.setPointer(mem);
        for (int i = 0; i < bs.length; i++) {
            inipar.getPointer().setByte(i * Byte.SIZE / 8, bs[i]);
        }
        byte c = '\n';
        inipar.getPointer().setByte((bs.length + 1) * Byte.SIZE / 8, c);
        int rt =   NativeLoader.getInstance().serialport_write(getFd(), inipar);
        if (rt != 0)
        {
            logger.error("Warning fail to write store " + bs.length);
            throw new SerialPortException("The serial port is closed "+getFd());
        }
    }


    public void pending(int time){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e1)
        {
            // ignore
        }
    }
    @Override
    public void write (byte[] bs) throws SerialPortException {
        try
        {
            if(fifo_out.getSize() > 0)
            {
                flush();
            }
            writeNative(bs);
        }   catch (Exception e)
        {
            storeData(bs);
            logger.warn("Failed to write "+new String(bs)+" "+bs.length+" octects on "+this.getPort_name());
            pending(500);
            flush();
        }
    }

    @Override
    public void flush() throws SerialPortException
    {
        if(fifo_out.getSize() > 0)
        {
            writeNative(fifo_out.removeAll());
        }
    }

    private String getTmpfilePath(){
        String path_tmpdir = System.getProperty("java.io.tmpdir");
        String name =this.getPort_name().replace("/","");
        return path_tmpdir+"/"+name+"lock";
    }

    public ByteFIFO getFifo_out() {
        return fifo_out;
    }

    public void setFifo_out(ByteFIFO fifo_out) {
        this.fifo_out = fifo_out;
    }

    public ByteFIFO getFifo_in() {
        return fifo_in;
    }

    public void setFifo_in(ByteFIFO fifo_in) {
        this.fifo_in = fifo_in;
    }


    public int getSizefifo_out() {
        return sizefifo_out;
    }

    public void setSizefifo_out(int sizefifo_out) {
        this.sizefifo_out = sizefifo_out;
    }

    public int getSizefifo_in() {
        return sizefifo_in;
    }

    public void setSizefifo_in(int sizefifo_in) {
        this.sizefifo_in = sizefifo_in;
    }

    @Override
    public void open () throws SerialPortException
    {
        File   tmpfile=null;
        try
        {
            tmpfile = new File(getTmpfilePath());
            if(tmpfile.exists())
            {
                FileReader fstream=null;
                BufferedReader in=null;
                try
                {
                    fstream = new FileReader(tmpfile);
                    in = new BufferedReader(fstream);
                    String readline = in.readLine();
                    Integer fdlock  =   Integer.parseInt(readline);
                    setFd(fdlock);
                    logger.warn("The serial port is locked " + tmpfile.getAbsolutePath()+" fd "+fdlock );
                    close();
                }   catch (Exception e)
                {
                    //ignore
                }   finally
                {
                    in.close();
                }
                try
                {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    //ignore
                }
            }
            setFd(NativeLoader.getInstance().open_serial(this.getPort_name(), this.getPort_bitrate()));
            if (getFd() < 0)
            {
                NativeLoader.getInstance().close_serial(getFd());
                throw new SerialPortException(this.getPort_name()+"- [" + getFd() + "] " + Constants.messages.get(getFd())+" Ports : "+ KHelpers.getPortIdentifiers());
            }
            else
            {
                logger.debug("Creating lock file "+tmpfile.getAbsolutePath()+" "+getFd());

                tmpfile.createNewFile();
                FileWriter fstream=null;
                BufferedWriter out =null;
                try
                {
                    fstream = new FileWriter(tmpfile);
                    out = new BufferedWriter(fstream);
                    // write the file descriptor number
                    out.write(getFd());
                }   catch (Exception e){
                    logger.error("Write file descriptor number ",e);
                }   finally
                {
                    if(out != null)
                    {
                        out.close();

                    }
                }

                SerialPortEvent = new SerialPortEvent(this);
                SerialPortEvent.subscribeNativeC();
            }

        }catch (Exception e)
        {
            if(tmpfile !=null)
            {

                tmpfile.delete();
            }
            close();
            throw  new SerialPortException(e.getMessage());
        }
    }

    public boolean autoReconnect (int tentative,SerialPortEventListener event) throws SerialPortException {
        close();
        removeEventListener(event);
        int i=0;
        while(i < tentative && exit == false)
        {
            try
            {
                try
                {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    //ignore
                }
                open();
                NativeLoader.getInstance().register_SerialEvent(SerialPortEvent);
                addEventListener(event);

                return true;
            }catch (Exception e){
                logger.error("The reconnection on port "+this.getPort_name()+" failed");
                close();
            }
            i++;
        }
        return false;
    }

    private void close () throws SerialPortException {
        File   tmpfile=null;
        int rt =0;
        try
        {
            if((rt= NativeLoader.getInstance().close_serial(getFd())) != 0){

                logger.debug("An error occurred while closing ");
            }
            tmpfile = new File(getTmpfilePath());
            if(tmpfile != null && tmpfile.exists())
            {
                logger.debug("Removing lock file "+tmpfile.getAbsolutePath());
                tmpfile.delete();
            }
        }catch (Exception e)
        {
            logger.error("Closing serial port ",e);
        }
    }
    @Override
    public void exit()
    {
        exit = true;
        try
        {
            close();
            listenerList = null;
            SerialPortEvent =null;
            fifo_out = null;
            // TODO
            Thread.sleep(2000);
            NativeLoader.destroy();
        } catch (Exception e) {
            // ignore
        }
    }

    @Override
    public InputStream getInputStream() throws SerialPortException {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() throws SerialPortException {
        return outputStream;
    }

    @Override
    public void write(byte b) throws SerialPortException {
        byte[] array = new byte[2];
        array[0] = b;
        write(array);
    }

}
