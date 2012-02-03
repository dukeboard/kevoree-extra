package org.kevoree.extra.kserial;

import org.kevoree.extra.kserial.Utils.KserialHelper;
import org.kevoree.extra.kserial.jna.NativeLoader;

public class Test {

    /**
     * @param args
     * @throws Exception
     */


    public static void main(String[] args) throws Exception {



        System.out.println(KserialHelper.getPortIdentifiers());

        final SerialPort serial = new SerialPort("/dev/ttyUSB0", 19200);


        serial.open();
        serial.addEventListener(new SerialPortEventListener(){


            public void incomingDataEvent (SerialPortEvent evt) {
                System.out.println("event="+evt.getSize()+"/"+new String(evt.read()));
            }

            public void disconnectionEvent (SerialPortDisconnectionEvent evt) {
                System.out.println("device " + serial.port_name + " is not connected anymore ");


                try {
                    serial.autoReconnect(20,this);
                } catch (SerialPortException e) {


                }




            }
        });


        Thread.currentThread().sleep(1000000);
    }

}
