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

    public static final int  FD_DISCONNECTED  =-10;
    public static final int  FD_ALREADY_CLOSED  =-12;
    public static final int EXIT_CONCURRENT_VM = -42;

    public final static Map messages= new HashMap() {
        {
            put(-1, "The SerialPort bitrate choosen is out of scope");
            put(-2, "SerialPort device does not exist");
            put(-3," Attributes and fill termios structure");
            put(-4, " set baud rate ");
            put(-7, " Fail to write byte");
            put(-8, " Fail to read byte");

        }
    };


}
