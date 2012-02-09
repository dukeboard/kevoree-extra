package org.kevoree.extra.kserial.Flash;

import com.sun.jna.Memory;
import com.sun.jna.ptr.PointerByReference;
import org.kevoree.extra.kserial.Constants;
import org.kevoree.extra.kserial.SerialPort.SerialPortDisconnectionEvent;
import org.kevoree.extra.kserial.SerialPort.SerialPortEvent;
import org.kevoree.extra.kserial.SerialPort.SerialPortEventListener;
import org.kevoree.extra.kserial.jna.FlashEvent;
import org.kevoree.extra.kserial.jna.NativeLoader;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 06/02/12
 * Time: 09:56
 */
public class FlashFirmware extends FoaAbstract {

    /**
     * Flash device firmware
     * @param devicename    eg : /dev/ttyUSB0
     * @param board         eg : ATMEGA328
     * @param node_id       eg : NODE0
     */

    protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
    private  FlashFirmwareEvent flashFirmwareEvent;

    public FlashFirmware(String devicename,String board,String node_id){
        this.setDevice_name(devicename);
        this.setDevice_target(Integer.parseInt(Constants.boards.get(board).toString()));
        this.setNode_target(node_id);
        flashFirmwareEvent = new FlashFirmwareEvent(this);

    }



    public void addEventListener (FlashFirmwareEventListener listener) {
        listenerList.add(FlashFirmwareEventListener.class, listener);
    }

    public void removeEventListener (FlashFirmwareEventListener listener) {
        listenerList.remove(FlashFirmwareEventListener.class, listener);
    }


    void fireFlashEvent (FlashFirmwareEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == FlashFirmwareEventListener.class) {
                    ((FlashFirmwareEventListener) listeners[i + 1]).FlashEvent((FlashFirmwareEvent)evt);
            }
        }
    }

    @Override
    public int write_on_the_air_program( Byte[] raw_intel_hex_array)
    {
        Memory mem = new Memory(Byte.SIZE * raw_intel_hex_array.length + 1);
        mem.clear();

        PointerByReference inipar = new PointerByReference();
        inipar.setPointer(mem);
        for (int i = 0; i < raw_intel_hex_array.length; i++) {
            inipar.getPointer().setByte(i * Byte.SIZE / 8, raw_intel_hex_array[i]);
        }
        byte c = '\n';
        inipar.getPointer().setByte((raw_intel_hex_array.length + 1) * Byte.SIZE / 8, c);

     //   System.out.println("taille ="+raw_intel_hex_array.length);
        return      NativeLoader.getINSTANCE_Foa().write_on_the_air_program(this.getDevice_name(),this.getDevice_target(),this.getNode_target(),raw_intel_hex_array.length,inipar);
    }
}
