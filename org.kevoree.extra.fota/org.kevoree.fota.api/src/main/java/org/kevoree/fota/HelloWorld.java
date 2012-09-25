package org.kevoree.fota;

import org.kevoree.fota.utils.ClassLoaderUtil;

import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jed
 * Date: 30/07/12
 * Time: 10:14
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorld {
    private native void print();

    private static List<ClassLoader> loaders = new LinkedList<ClassLoader>();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 3; i++) {
            // Clone the current class loader. Use null as parent, so that no classes (but the standard java
            // classes) are ever shared.
            URLClassLoader loader = new URLClassLoader(((URLClassLoader) HelloWorld.class.getClassLoader()).getURLs(), null);
            //Just to make sure the classloader is not GCed.
            loaders.add(loader);

            Class main;
            main = loader.loadClass(HelloWorld.class.getName());
            Method start = main.getMethod("start", new Class[0]);
            start.invoke(null, new Object[0]);
            //Now clear the dlls cache so that another classloader can load the same
            List<String> dlls = new LinkedList<String>();
            dlls.add("HelloWorld.dll");
            ClassLoaderUtil.closeDLLs(loader, dlls);
        }
    }

    public static void start() {
        try {
            System.loadLibrary("HelloWorld");
            new HelloWorld().print();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

}