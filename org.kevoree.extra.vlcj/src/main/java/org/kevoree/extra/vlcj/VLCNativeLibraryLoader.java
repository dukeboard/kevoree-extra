package org.kevoree.extra.vlcj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * User: Erwan Daubert - erwan.daubert@gmail.com
 * Date: 17/08/11
 * Time: 21:49
 */
public class VLCNativeLibraryLoader {

	private static Logger logger = LoggerFactory.getLogger(VLCNativeLibraryLoader.class);

	public static String configure () {
		try {
			File folder = new File(System.getProperty("java.io.tmpdir") + File.separator + "libvlc");
			if (folder.exists()) {
				deleteOldFile(folder);
			}
			folder.mkdirs();
			String path = foundOSPath();
			String[] names = foundOSName();
			for (String name : names) {
				if (name.endsWith(".zip")) {
					copyZipFileAndExtract(name, path, folder);
				} else {
					copyFileFromStream(name, path, folder);
				}
			}
			logger.debug("libvlc copied in " + folder.getAbsolutePath());
			return folder.getAbsolutePath();
		} catch (IOException e) {
			logger.error("cannot copy dynamic libs for libvlc", e);
			return ".";// TODO throw exception
		}
	}

	private static String foundOSPath () {
		if (isUnix()) {
			if (!is64()) {
				return "nativelib/linux/x86";
			} else if (is64()) {
				return "nativelib/linux/x86_64";
			}
		} else if (isMac()) {
			return "nativelib/macos";
		} else if (isWindows()) {
			if (!is64()) {
				return "nativelib/windows/x86";
			} else if (is64()) {
				return "nativelib/windows/x86_64";
			}
		}
		return ".";
	}

	private static String[] foundOSName () {
		if (isUnix()) {
			if (!is64()) {
				return new String[]{"libvlc.so", "libvlccore.so", "vlc.zip"};
			} else if (is64()) {
				return new String[]{""};
			}
		} else if (isMac()) {
			return new String[]{"libvlc.dylib", "libvlccore.dylib", "vlc.zip"};
		} else if (isWindows()) {
			if (!is64()) {
				return new String[]{""};
			} else if (is64()) {
				return new String[]{""};
			}
		}
		return null;
	}

	public static boolean isWindows () {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.contains("win"));
	}

	public static boolean isMac () {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.contains("mac"));
	}

	public static boolean isUnix () {
		String os = System.getProperty("os.name").toLowerCase();
		return (os.contains("nix") || os.contains("nux"));
	}

	public static boolean is64 () {
		String os = System.getProperty("os.arch").toLowerCase();
		return (os.contains("64"));
	}

	private static void copyFileFromStream (String fileName, String filePath, File folder) throws IOException {
		InputStream inputStream = VLCNativeLibraryLoader.class.getClassLoader()
				.getResourceAsStream(filePath + "/" + fileName);
		//if (inputStream != null) {
		File copy = new File(folder + File.separator + fileName);
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
		//}
		//return null;
	}

	private static void copyZipFileAndExtract (String fileName, String filePath, File folder) throws IOException {
		copyFileFromStream(fileName, filePath, folder);
		ZipFile zipFile = new ZipFile(folder + File.separator + fileName);
		Enumeration<? extends ZipEntry> enums = zipFile.entries();
		while (enums.hasMoreElements()) {
			ZipEntry entry = enums.nextElement();
			System.out.println(entry.getName());
			if (entry.isDirectory()) {
				new File(folder + File.separator + entry.getName()).mkdirs();
			} else {
				copyFileFromZip(zipFile, entry, folder);
			}
		}
	}

	private static void copyFileFromZip (ZipFile zipFile, ZipEntry entry, File folder) throws IOException {
		InputStream inputStream = zipFile.getInputStream(entry);
		File copy = new File(folder + File.separator + entry.getName());
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
	}


	/*private void copyResult (String filePath, File folder) {
			val file = new File(filePath)
			if (file.exists()) {
			  val copy = new File(folder + File.separator + file.getName)
			  val inputStream = new FileInputStream(file)
			  val outpuStream = new FileOutputStream(copy)
			  var length: Int = 0
			  val bytes = new Array[Byte](1024)
			  length = inputStream.read(bytes)
			  while (length > -1) {
				outpuStream.write(bytes, 0, length)
				length = inputStream.read(bytes)
			  }
			}
		  }*/

	private static void deleteOldFile (File folder) {
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
}
