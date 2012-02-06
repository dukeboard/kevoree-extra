package org.kevoree.extra.kserial.jna;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 06/02/12
 * Time: 09:42
 */
public interface FlashJNA extends Library {


    int write_on_the_air_program(String port_device,int target,String node_id,int taille, PointerByReference raw_intel_hex_array);

    int register_FlashEvent(FlashEvent callback);

    void close_flash();
}
