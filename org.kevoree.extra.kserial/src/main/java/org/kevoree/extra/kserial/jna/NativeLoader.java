package org.kevoree.extra.kserial.jna;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import java.io.*;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 30/01/12
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class NativeLoader {

    public static synchronized SerialPortJNA getInstance() {
        configure();
        return INSTANCE;
    }

    private static SerialPortJNA INSTANCE = null;

    private static void configure() {
        if (INSTANCE == null) {
            try {
                File folder = new File(System.getProperty("java.io.tmpdir") + File.separator + "kserial");
                if (folder.exists()) {
                    deleteOldFile(folder);
                }
                folder.mkdirs();
                String absolutePath = copyFileFromStream(getPath(), folder.getAbsolutePath(), "serialposix" + getExtension());
                NativeLibrary.addSearchPath("serialposix", folder.getAbsolutePath());
                INSTANCE = (SerialPortJNA) Native.loadLibrary(absolutePath, SerialPortJNA.class, new HashMap());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public static String getPath() {
        if (System.getProperty("os.name").toLowerCase().contains("nux")) {
            if (is64()) {
                return "nix64/serialposix.so";
            } else {
                return "nix32/serialposix.so";
            }
        }
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            return "osx/serialposix.so";
        }
        return null;
    }

    public static boolean is64() {
        String os = System.getProperty("os.arch").toLowerCase();
        return (os.contains("64"));
    }

    /* Utility fonctions */
    private static void deleteOldFile(File folder) {
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

    private static String copyFileFromStream(String inputFile, String path, String targetName) throws IOException {
        InputStream inputStream = NativeLoader.class.getClassLoader().getResourceAsStream(inputFile);
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
