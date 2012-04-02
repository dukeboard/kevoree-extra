package org.kevoree.extra.kserial;

import org.kevoree.extra.kserial.SerialPort.*;
import org.kevoree.extra.kserial.Utils.KHelpers;

public class Test {

    /**
     * @param args
     * @throws Exception
     */


    public static void main(String[] args) throws Exception {


        System.out.println(KHelpers.getPortIdentifiers());
        final SerialPort serial = new SerialPort("/dev/tty.usbserial-A400g2AP", 115200);
        serial.open();


        serial.addEventListener(new SerialPortEventListener() {
            public void incomingDataEvent(SerialPortEvent evt) {
                System.out.println("event=" + evt.getSize() + "/" + new String(evt.read()));
            }

            public void disconnectionEvent(SerialPortDisconnectionEvent evt) {
                System.out.println("device " + serial.getPort_name() + " is not connected anymore ");
                try {
                    serial.autoReconnect(20, this);
                } catch (SerialPortException e) {
                }
            }
        });


        Thread.sleep(2000);

        serial.write("123456".getBytes());

       // serial.write("$8{udi:t1:period=500}".getBytes());

        Thread.sleep(2000);
       // serial.write("$8{udi:t1:period=200}".getBytes());

        Thread.currentThread().sleep(3000);


/*
FlashFirmware flash = new FlashFirmware("/dev/tty.usbserial-A400g2wl","ATMEGA328","NODE0");

Byte[] intel = KHelpers.read_file("/Users/oxyss35/kevoree-extra/org.kevoree.extra.kserial/src/main/c/FlashOvertheair/program_test/test.hex");
if(flash.write_on_the_air_program(intel) >= 0){
    flash.addEventListener(new FlashFirmwareEventListener() {
                // @Override
                public void FlashEvent(FlashFirmwareEvent evt) {
                    System.out.println("Callback Event received :  "+evt.getSize_uploaded());
                }
            });

    Thread.currentThread().sleep(1000000);

}*/


    }

}
