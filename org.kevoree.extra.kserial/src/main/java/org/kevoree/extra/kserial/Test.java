package org.kevoree.extra.kserial;

import org.kevoree.extra.kserial.Flash.FlashFirmware;
import org.kevoree.extra.kserial.Flash.FlashFirmwareEvent;
import org.kevoree.extra.kserial.Flash.FlashFirmwareEventListener;
import org.kevoree.extra.kserial.SerialPort.*;
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
         System.out.println("device " + serial.getPort_name() + " is not connected anymore ");
         try {
             serial.autoReconnect(20,this);
         } catch (SerialPortException e) {
         }
     }
 });

  //Thread.sleep(2000);
  //serial.write("111".getBytes());

 Thread.currentThread().sleep(10000000);

                 */



  FlashFirmware flash = new FlashFirmware("/dev/tty.usbserial-A400fXsq","ATMEGA328","NODE0");

  Byte[] intel = KHelpers.read_file("/Users/oxyss35/kevoree-extra/org.kevoree.extra.kserial/src/main/c/FlashOvertheair/program_test/test.hex");
  if(flash.write_on_the_air_program(intel) >= 0){
      flash.addEventListener(new FlashFirmwareEventListener() {
                  // @Override
                  public void FlashEvent(FlashFirmwareEvent evt) {
                      System.out.println("sent "+evt.getSize_uploaded());
                  }
              });

      Thread.currentThread().sleep(1000000);

  }







    }

}
