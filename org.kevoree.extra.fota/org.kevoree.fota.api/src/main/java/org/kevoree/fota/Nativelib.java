package org.kevoree.fota;


import org.kevoree.fota.events.FotaEvent;
import org.kevoree.fota.events.UploadedFotaEvent;
import org.kevoree.fota.events.WaitingBLFotaEvent;
import org.kevoree.fota.utils.Constants;
import org.kevoree.fota.utils.FotaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.EventObject;

/**
 * Created with IntelliJ IDEA.
 * User: jed
 * Date: 17/07/12
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */
public class Nativelib extends EventObject implements FotaEvent {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // NATIVE METHODS
    public native int write_on_the_air_program(String port_device,int target,String path_hex_file);
    public native boolean register();
    public native void close_flash();


    private Fota fota;
    private int size_uploaded;

    public Nativelib(Fota o) throws FotaException {
        super(o);
        fota = o;
        configure();
    }
    /**
     * method call from JNI C
     * @param evt
     */
    @Override
    public void dispatchEvent(int evt)
    {
        try{
            if(evt == Constants.FINISH)
            {
                this.close_flash();
                fota.fireFlashEvent(new UploadedFotaEvent(fota));
            } else if(evt ==  Constants.RE_SEND_EVENT)
            {

            }
            else if(evt ==  Constants.ERROR_WRITE || evt ==  Constants.ERROR_READ)
            {
                logger.error("ERROR_WRITE/ERROR_READ ");
                // failover();
            }else if(evt ==  Constants.EVENT_WAITING_BOOTLOADER)
            {
                System.out.println("Waiting for target IC to boot into bootloader ");
                fota.fireFlashEvent(new WaitingBLFotaEvent(fota));
            }
            else if(evt > 0)
            {
                this.size_uploaded = evt;
                fota.fireFlashEvent(this);
            }
        }catch (FotaException e)
        {
            logger.error("dispatchEvent ",e);
        }
    }

    @Override
    public long getDuree() {
        return fota.getDuree();
    }

    @Override
    public int getProgram_size() {
        return fota.getProgram_size();
    }


    public int getSize_uploaded() {
        return size_uploaded;
    }

    public Fota getFota() {
        return fota;
    }


    private  void configure() throws FotaException
    {
        try
        {
            File folder = new File(System.getProperty("java.io.tmpdir") + File.separator + "fotanative");
            if (folder.exists())
            {
                deleteOldFile(folder);
            }
            folder.mkdirs();
            // todo kcl
            String absolutePath = copyFileFromStream(getPath("native.so"), folder.getAbsolutePath(), "fota" + getExtension());
            // load native
            System.load(absolutePath);

        } catch (Exception e) {
            throw new FotaException("load dynamic library "+e);
        }

    }

    /* Utility fonctions */
    public static void deleteOldFile(File folder) {
        if (folder.isDirectory()) {
            for (File f : folder.listFiles()) {
                if (f.isFile()) {
                    f.delete();
                } else {
                    deleteOldFile(f);
                }
            }
        }
        folder.delete();
    }

    public static String getExtension() {
        if (System.getProperty("os.name").toLowerCase().contains("nux")) {
            return ".so";
        }
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            return ".dynlib";
        }
        return null;
    }

    public static String getPath(String lib) {
        if (System.getProperty("os.name").toLowerCase().contains("nux")) {
            if (is64()) {
                return "nix64/"+lib;
            } else {
                return "nix32/"+lib;
            }
        }
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            return "osx/"+lib;
        }
        return null;
    }

    public static boolean is64() {
        String os = System.getProperty("os.arch").toLowerCase();
        return (os.contains("64"));
    }

    public static String copyFileFromStream(String inputFile, String path, String targetName) throws IOException {
        InputStream inputStream = Nativelib.class.getClassLoader().getResourceAsStream(inputFile);
        if (inputStream != null) {
            File copy = new File(path + File.separator + targetName);
            copy.deleteOnExit();
            OutputStream outputStream = new FileOutputStream(copy);
            byte[] bytes = new byte[1024];
            int length = inputStream.read(bytes);

            while (length > -1) {
                outputStream.write(bytes, 0, length);
                length = inputStream.read(bytes);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
            return copy.getAbsolutePath();
        }
        return null;
    }




}
