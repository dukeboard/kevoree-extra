package org.kevoree.extra.kserial;


import org.kevoree.extra.kserial.jna.SerialBrokenLink;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 26/01/12
 * Time: 9:01
 */

public interface SerialPortEventListener extends java.util.EventListener
{
	  void serialEvent(SerialPortEvent evt);

}
