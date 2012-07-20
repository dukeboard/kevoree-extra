package org.kevoree.fota.events;


import org.kevoree.fota.Fota;
import org.kevoree.fota.Nativelib;
import org.kevoree.fota.utils.FotaException;

/**
 * Created with IntelliJ IDEA.
 * User: jed
 * Date: 10/07/12
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
public class WaitingBLFotaEvent extends Nativelib {
    public WaitingBLFotaEvent(Fota src) throws FotaException {
        super(src);
    }
}
