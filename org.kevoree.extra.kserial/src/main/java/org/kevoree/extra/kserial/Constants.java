package org.kevoree.extra.kserial;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 9:01
 */

public final class Constants {

    public final static Map messages= new HashMap() {
        {
            put(-1, "The SerialPort bitrate choosen is out of scope");
            put(-2, "SerialPort device does not exist");
            put(-3," Attributes and fill termios structure");
            put(-4, " set baud rate ");

            put(-7, " Fail to write byte");
            put(-8, " Fail to read byte");

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
            put("ATMEGA328","0");
            put("ATMEGA328P","1");

        }
    };




}
