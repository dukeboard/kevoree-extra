package org.kevoree.extra.kserial;

import org.kevoree.extra.kserial.Flash.FlashFirmware;
import org.kevoree.extra.kserial.Flash.FlashFirmwareEvent;
import org.kevoree.extra.kserial.Flash.FlashFirmwareEventListener;
import org.kevoree.extra.kserial.Utils.KHelpers;

public class Test {

    /**
     * @param args
     * @throws Exception
     */


    public static void main(String[] args) throws Exception {


       /*
        System.out.println(KHelpers.getPortIdentifiers());

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
               */



 FlashFirmware flash = new FlashFirmware("/dev/ttyUSB0","ATMEGA328","NODE02");
        
        Byte[] intel = KHelpers.read_file("/home/jed/kevoree/kevoree-extra/org.kevoree.extra.kserial/src/main/c/FlashOvertheair/program_test/test.hex");


        flash.write_on_the_air_program(intel);


        flash.addEventListener(new FlashFirmwareEventListener() {
            @Override
            public void FlashEvent(FlashFirmwareEvent evt) {


           System.out.println(evt.getSize_uploaded());


            }
        });


        Thread.currentThread().sleep(1000000);


    }

}
