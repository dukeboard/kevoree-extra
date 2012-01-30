package org.kevoree.extra.kserial.jna;

import org.kevoree.extra.kserial.SerialPortException;

import javax.security.auth.callback.Callback;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 30/01/12
 * Time: 17:46
 */
public interface SerialBrokenLink extends Callback{

    void serial_broken_link();
}
