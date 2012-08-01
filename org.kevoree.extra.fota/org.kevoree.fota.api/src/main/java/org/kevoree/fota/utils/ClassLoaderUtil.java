package org.kevoree.fota.utils;

/**
 * Created with IntelliJ IDEA.
 * User: jed
 * Date: 30/07/12
 * Time: 09:45
 * To change this template use File | Settings | File Templates.
 */
import java.lang.reflect.Field;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


/**
 * Provides utility functions related to URLClassLoaders or subclasses of it.
 *
 */
public class ClassLoaderUtil {

    private static final String   CLASSLOADER_LOADED_LIBRARY_NAMES = "loadedLibraryNames"; //$NON-NLS-1$
    private static final String   CLASSLOADER_NATIVE_LIBRARIES = "nativeLibraries"; //$NON-NLS-1$
    private static final String   CLASSLOADER_NATIVELIBRARY_INNER_CLASS_NAME = "java.lang.ClassLoader$NativeLibrary"; //$NON-NLS-1$
    private static final String   CLASSLOADER_NATIVELIBRARY_FIELD_NAME = "name"; //$NON-NLS-1$

    /* Fields used during processing - they can be set up once and then used repeatedly */
    private static Class classLoaderInnerClass;
    private static Field   loadedLibNamesField;
    private static Field   nativeLibsField;
    private static Field nameField;

    private static boolean initDone = false;

    /**
     *Initializes the class.
     *<p>
     *Each utility method should invoke init() before doing their own work
     *to make sure the initialization is done.
     *@throws any Throwable detected during static init.
     */
    private static void init() throws Throwable   {
        if ( ! initDone) {
            initForClosingDlls();
            initDone = true;
        }
    }

    /**
     *Sets up variables used in closing a loader's jar files.
     *@throws NoSuchFieldException in case a field of interest is not found where expected
     */
    private static void initForClosingDlls() throws NoSuchFieldException   {
        classLoaderInnerClass = getInnerClass(ClassLoader.class, CLASSLOADER_NATIVELIBRARY_INNER_CLASS_NAME);
        nameField = getField(classLoaderInnerClass, CLASSLOADER_NATIVELIBRARY_FIELD_NAME);
        loadedLibNamesField = getField(ClassLoader.class, CLASSLOADER_LOADED_LIBRARY_NAMES);
        nativeLibsField = getField(ClassLoader.class, CLASSLOADER_NATIVE_LIBRARIES);
    }

    /**
     *Retrieves a Field object for a given field on the specified class, having
     *set it accessible.
     *@param cls the Class on which the field is expected to be defined
     *@param fieldName the name of the field of interest
     *@throws NoSuchFieldException in case of any error retriving information about the field
     */
    private static Field getField(Class   cls, String   fieldName) throws NoSuchFieldException   {
        try {
            Field   field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException   nsfe) {
            NoSuchFieldException e = new NoSuchFieldException  (getMessage("classloaderutil.errorGettingField", fieldName)); //$NON-NLS-1$
            e.initCause(nsfe);
            throw e;
        }
    }

    /**
     *Retrieves a given inner class definition from the specified outer class.
     *@param cls the outer Class
     *@param innerClassName the fully-qualified name of the inner class of interest
     */
    private static Class   getInnerClass(Class   cls, String  innerClassName) {
        Class   result = null;
        Class  [] innerClasses = cls.getDeclaredClasses();
        for (Class   c : innerClasses) {
            if (c.getName().equals(innerClassName)) {
                result = c;
                break;
            }
        }
        return result;
    }

    //Just a workaround. couldn't find an alternate to close dll connections.
    public static void closeDLLs(URLClassLoader classLoader, List<String> dlls) {
        try {
            init();

            Vector<String> loadedLibNames = (Vector) loadedLibNamesField.get(classLoader);
            Vector nativeLibs = (Vector) nativeLibsField.get(classLoader);

            synchronized (loadedLibNames) {
                for (int index = 0; index < dlls.size(); index++) {
                    Iterator<String> iter = loadedLibNames.iterator();
                    while (iter.hasNext()) {
                        String libName = iter.next();
                        if (libName != null && libName.indexOf(dlls.get(index)) != -1) {
                            iter.remove();
                        }
                    }
                }
            }

            synchronized (nativeLibs) {
                for (int index = 0; index < dlls.size(); index++) {
                    Iterator nIter = nativeLibs.iterator();
                    while (nIter.hasNext()) {
                        Object ClassLoader$NativeLibrary = nIter.next();
                        String name = (String) nameField.get(ClassLoader$NativeLibrary);
                        if (name != null && name.indexOf(dlls.get(index)) != -1) {
                            nIter.remove();
                        }
                    }
                }
            }
        } catch (Throwable th) {
            // ignore
        }
    }


    /**
     *Returns a formatted string, using the key to find the full message and
     *substituting any parameters.
     *@param key the message key with which to locate the message of interest
     *@param o the object(s), if any, to be substituted into the message
     *@return a String containing the formatted message
     */
    private static String   getMessage(String   key, Object  ... o) {
        return MessageFormat.format(key, o);
    }
}