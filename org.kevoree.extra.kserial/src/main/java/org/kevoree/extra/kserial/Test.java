package org.kevoree.extra.kserial;

public class Test {

    /**
     * @param args
     * @throws Exception
     */


    public static void main(String[] args) throws Exception {

        final SerialPort serial = new SerialPort("/dev/ttyACM3", 9600);

        serial.open();
        serial.addEventListener(new SerialPortEventListener(){


			public void incomingDataEvent (SerialPortEvent evt) {
				System.out.println("event="+evt.getSize()+"/"+new String(evt.read()));
			}

			public void disconnectionEvent (SerialPortDisconnectionEvent evt) {
				System.out.println("device " + serial.port_name + " is not connected anymore ");
			}
		});


        Thread.currentThread().sleep(1000000);
    }

}
