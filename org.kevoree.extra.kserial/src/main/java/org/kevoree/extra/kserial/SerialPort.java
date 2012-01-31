package org.kevoree.extra.kserial;

import com.sun.jna.Memory;
import com.sun.jna.ptr.PointerByReference;
import org.kevoree.extra.kserial.jna.NativeLoader;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 17:29
 * <p/>
 * SerialPort
 */

public class SerialPort extends CommPort {

	public static final int DATABITS_5 = 5;
	public static final int DATABITS_6 = 6;
	public static final int DATABITS_7 = 7;
	public static final int DATABITS_8 = 8;
	public static final int PARITY_NONE = 0;
	public static final int PARITY_ODD = 1;
	public static final int PARITY_EVEN = 2;
	public static final int PARITY_MARK = 3;
	public static final int PARITY_SPACE = 4;
	public static final int STOPBITS_1 = 1;
	public static final int STOPBITS_2 = 2;
	public static final int STOPBITS_1_5 = 3;
	public static final int FLOWCONTROL_NONE = 0;
	public static final int FLOWCONTROL_RTSCTS_IN = 1;
	public static final int FLOWCONTROL_RTSCTS_OUT = 2;
	public static final int FLOWCONTROL_XONXOFF_IN = 4;
	public static final int FLOWCONTROL_XONXOFF_OUT = 8;

	private SerialPortEvent SerialPortEvent;
	protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();

	private int sizefifo = 1024;
	private ByteFIFO fifo_out = new ByteFIFO(sizefifo);


	public SerialPort (String portname, int bitrate) throws Exception {
		this.port_bitrate = bitrate;
		this.port_name = portname;
	}

	public void addEventListener (SerialPortEventListener listener) {
		listenerList.add(SerialPortEventListener.class, listener);
	}

	public void removeEventListener (SerialPortEventListener listener) {
		listenerList.remove(SerialPortEventListener.class, listener);
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

	@Override
	public void write (byte[] bs) throws SerialPortException {

		if (fd > 0) {
			Memory mem = new Memory(Byte.SIZE * bs.length + 1);
			mem.clear();

			PointerByReference inipar = new PointerByReference();
			inipar.setPointer(mem);
			for (int i = 0; i < bs.length; i++) {
				inipar.getPointer().setByte(i * Byte.SIZE / 8, bs[i]);
			}
			byte c = '\n';
			inipar.getPointer().setByte((bs.length + 1) * Byte.SIZE / 8, c);

			if (NativeLoader.getInstance().serialport_write(fd, inipar) != 0) {
				throw new SerialPortException("Write " + bs);
			}

		} else {
			throw new SerialPortException("not open " + bs);
		}


	}

	@Override
	public void open () throws SerialPortException {
		fd = NativeLoader.getInstance().open_serial(port_name, port_bitrate);
        System.out.println(" file descr : "+fd);
		if (fd < 0) {
			NativeLoader.getInstance().close_serial(fd);
			throw new SerialPortException("[" + fd + "] " + Constants.messages_errors.get(fd));
		}
		SerialPortEvent = new SerialPortEvent(this);

	}

	@Override
	public void close () throws SerialPortException {
		NativeLoader.getInstance().close_serial(fd);

	}

}
