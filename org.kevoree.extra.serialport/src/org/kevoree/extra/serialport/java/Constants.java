package org.daum.ArduinoFOA;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 9:01
 */

public final class Constants {
	
	public final static Map messages_errors = new HashMap() {
		{
		put(-1, "The Serial bitrate choosen is out of scope\n");
		put(-2, "Serial device does not exist\n");
		put(-3," Attributes and fill termios structure");
		put(-4, " set baud rate ");
		}
		};
		
		
		public static String BytesToString(byte[] array) {
			StringBuffer sb = new StringBuffer(array.length);
			for( int i=0 ; (i < array.length) && (array[i]!=0) ; i++) {
				sb.append((char)array[i]);
			}
			return sb.toString();
		}
	
}
