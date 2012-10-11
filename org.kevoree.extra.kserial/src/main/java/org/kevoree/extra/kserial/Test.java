package org.kevoree.extra.kserial;

import org.kevoree.extra.kserial.SerialPort.*;
import org.kevoree.extra.kserial.Utils.KHelpers;

public class Test {


    public static void main(String[] args) throws Exception {


        System.out.println(KHelpers.getPortIdentifiers());
        final SerialPort serial = new SerialPort("*", 19200);
        serial.open();


        serial.addEventListener(new SerialPortEventListener()
        {
            public void incomingDataEvent(SerialPortEvent evt)
            {
                System.out.println("event=" + evt.getSize() + "/" + new String(evt.read()));
            }

            public void disconnectionEvent(SerialPortDisconnectionEvent evt)
            {
                System.out.println("device " + serial.getPort_name() + " is not connected anymore ");
                try {
                    serial.autoReconnect(20, this);
                } catch (SerialPortException e) {
                }
            }

            @Override
            public void concurrentOpenEvent(SerialConcurrentOpenEvent evt) {

                System.out.println("Concurrent VM open serial port");


            }
        });



        Thread.sleep(4000);



    }

}
