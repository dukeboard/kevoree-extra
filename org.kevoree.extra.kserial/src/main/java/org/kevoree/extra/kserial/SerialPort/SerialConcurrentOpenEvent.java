package org.kevoree.extra.kserial.SerialPort;

/**
 * Created by IntelliJ IDEA.
 * User: oxyss35
 * Date: 27/06/12
 * Time: 10:09
 * To change this template use File | Settings | File Templates.
 */
public class SerialConcurrentOpenEvent extends SerialPortEvent {
    public SerialConcurrentOpenEvent (SerialPort serialport) throws SerialPortException {
        super(serialport);
    }
}

