package org.kevoree.fota.events;


import org.kevoree.fota.Fota;
import org.kevoree.fota.Nativelib;
import org.kevoree.fota.utils.FotaException;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 29/02/12
 * Time: 10:28
 */
public class UploadedFotaEvent extends Nativelib {
    public UploadedFotaEvent(Fota src) throws FotaException {
        super(src);
    }
}
