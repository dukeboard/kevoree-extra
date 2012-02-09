package org.kevoree.extra.kserial.Flash;

import org.kevoree.extra.kserial.Constants;
import org.kevoree.extra.kserial.SerialPort.SerialPortDisconnectionEvent;
import org.kevoree.extra.kserial.jna.FlashEvent;
import org.kevoree.extra.kserial.jna.NativeLoader;

import javax.sound.midi.SysexMessage;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.EventObject;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 06/02/12
 * Time: 11:44
 */
public class FlashFirmwareEvent extends EventObject implements FlashEvent {

    /**
     * Constructs a prototypical Event.
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    int size_uploaded;

    private  FlashFirmware flashFirmware;
    public FlashFirmwareEvent(FlashFirmware src) {
        super(src);
        NativeLoader.getINSTANCE_Foa().register_FlashEvent(this);
        this.flashFirmware = src;
    }

    @Override
    public void FlashEvent(int taille)
    {

        if(taille == -38)
        {
            System.out.println("-38 : "+Constants.messages.get(taille));

            NativeLoader.getINSTANCE_Foa().close_flash();
            System.exit(0);

        }else  if(taille == -35)
        {

            System.out.println(Constants.messages.get(taille));
        }else if(taille == -29)
        {
            System.out.println(Constants.messages.get(taille));
        }
        else if(taille == -36)
        {
            //System.out.println("Ready..");
        }
        else if (taille <0)
        {

            System.out.println(Constants.messages.get(taille));

        }  else {
            this.size_uploaded = taille;
            flashFirmware.fireFlashEvent(this);
        }

    }

    public int getSize_uploaded() {
        return size_uploaded;
    }
}
