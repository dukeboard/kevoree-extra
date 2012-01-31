package org.kevoree.extra.kserial;

public class Test {

    /**
     * @param args
     * @throws Exception
     */


    public static void main(String[] args) throws Exception {

        SerialPort serial = new SerialPort("/dev/ttyACM0", 9600);

        serial.open();
        serial.addEventListener(new SerialPortEventListener(){

            public void serialEvent(SerialPortEvent evt) {

                    System.out.println("event="+evt.getSize()+"/"+new String(evt.read()));


            }


        });


        Thread.currentThread().sleep(1000000);
    }

}
