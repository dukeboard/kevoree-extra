package org.kevoree.extra.kserial.Flash;

import org.kevoree.extra.kserial.SerialPort.SerialPortEvent;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 06/02/12
 * Time: 11:49
 */
public interface FlashFirmwareEventListener  extends java.util.EventListener {
    void FlashEvent(FlashFirmwareEvent evt);
}
