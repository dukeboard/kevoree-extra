package org.kevoree.fota;


import org.kevoree.fota.events.FotaEvent;
import org.kevoree.fota.events.UploadedFotaEvent;
import org.kevoree.fota.events.WaitingBLFotaEvent;
import org.kevoree.fota.utils.Constants;
import org.kevoree.fota.utils.FotaException;
import org.kevoree.kcl.KevoreeJarClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.EventObject;
import java.util.Random;

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
    private int size_uploaded=-1;
    private double last_progress=0;

    public Nativelib(Fota o) throws FotaException {
        super(o);
        fota = o;
        configureCL();
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
                fota.fireFlashEvent(new UploadedFotaEvent(fota));
                fota.close();
            } else if(evt ==  Constants.RE_SEND_EVENT)
            {
                logger.debug("RE_SEND_EVENT ");
            }else if(evt ==  Constants.FAIL_OPEN_FILE)
            {
                System.out.println("FAIL_OPEN_FILE ");
                fota.close();
            }
            else if(evt ==  Constants.ERROR_WRITE || evt ==  Constants.ERROR_READ)
            {
                logger.error("ERROR_WRITE/ERROR_READ ");
                fota.close();
            }else if(evt ==  Constants.EVENT_WAITING_BOOTLOADER)
            {
                System.out.println("Waiting for target IC to boot into bootloader ");
                fota.fireFlashEvent(new WaitingBLFotaEvent(fota));
            }
            else if(evt > 0)
            {
                if(evt > size_uploaded)
                {
                    last_progress = System.currentTimeMillis();
                }

                double duree = ( System.currentTimeMillis() - last_progress)  / 1000;

                if(duree> 2)
                {
                    logger.error("The bootloader is not responding");
                    fota.close();
                }
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

    /**
     * Adds the specified path to the java library path
     *
     * @param pathToAdd the path to add
     * @throws Exception
     */
    public static void addLibraryPath(String pathToAdd) throws Exception{
        final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
        usrPathsField.setAccessible(true);

        //get array of paths
        final String[] paths = (String[])usrPathsField.get(null);

        //check if the path to add is already present
        for(String path : paths) {
            if(path.equals(pathToAdd)) {
                return;
            }
        }

        //add the new path
        final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
        newPaths[newPaths.length-1] = pathToAdd;
        usrPathsField.set(null, newPaths);
    }

    public static String configureCL()
    {
        try
        {
            File folder = new File(System.getProperty("java.io.tmpdir") + File.separator + "fotanative");
            if (folder.exists())
            {
                deleteOldFile(folder);
            }
            folder.mkdirs();

            addLibraryPath(folder.getAbsolutePath());
            //http://developer.android.com/guide/practices/jni.html
            //http://phani-bandanakanti.blogspot.fr/
            // load the librairy with a different name - Bad approach :-)
            String r = ""+new Random().nextInt(800);
            String absolutePath = copyFileFromStream(getPath("native.so"), folder.getAbsolutePath(),"libnative"+r+""+ getExtension());

            System.loadLibrary("native"+r);

            return absolutePath;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
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
