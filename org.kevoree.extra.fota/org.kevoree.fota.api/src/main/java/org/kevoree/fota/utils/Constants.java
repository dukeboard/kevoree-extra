package org.kevoree.fota.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 9:01
 */

public final class Constants {


    public final static String avrDevices[] = {"m328p", "ATMEGA328"};
    public final static String programmerType[] = {"usbtiny"};



    public final static int FINISH=3;
    public final static int OK=0;
    public final static int EVENT_WAITING_BOOTLOADER=2;
    public final static int ERROR_WRITE=-2;
    public final static int ERROR_READ=-3;
    public final static int RE_SEND_EVENT=4;


    public final static Map messages= new HashMap() {
        {

            put(-1, "The avr device selected is not available");
            put(-2, " Fail to burnBootloader byte");
            put(-3, " Fail to read byte");
            put(-4, " set baud rate ");
            put(-5, "SerialPort device does not exist");
            put(-29, "Bootloader found and ready\n");
            put(-30, " the program is empty");
            put(-31, " the last memory address was not found");
            put(-32, " the device target was out of available");
            put(-33, " Target node is wrong");
            put(-34, " Target IC does not boot into bootloader");
            put(-35,"Waiting for target IC to boot into bootloader");
            put(-36,"Re-send");
            put(-38," Transmission completed successfully");

        }
    };


    public final static Map boards = new HashMap() {
        {
            put("m328p","0");
            put("ATMEGA328","0");
            put("ATMEGA328P","1");

        }
    };




}
