package org.kevoree.extra.jcl

import org.xeustechnologies.jcl.JarClassLoader
import java.net.URL
import java.lang.{Class, String}
import ref.WeakReference
import org.slf4j.LoggerFactory
import java.util.{Collections, Enumeration}
import java.io._

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 23/01/12
 * Time: 18:57
 */

class KevoreeJarClassLoader extends JarClassLoader {

  private val logger = LoggerFactory.getLogger(classOf[KevoreeJarClassLoader]);


  classpathResources = new KevoreeLazyJarResources

  def setLazyLoad(lazyload: Boolean) {
    classpathResources.asInstanceOf[KevoreeLazyJarResources].setLazyLoad(lazyload)
  }

  protected var subClassLoaders = List[ClassLoader]()

  def addSubClassLoader(cl: ClassLoader) {
    subClassLoaders = subClassLoaders ++ List(cl)
  }

  protected var subWeakClassLoaders = List[WeakReference[ClassLoader]]()

  def addWeakClassLoader(wcl: ClassLoader) {
    subWeakClassLoaders = subWeakClassLoaders ++ List(new WeakReference[ClassLoader](wcl))
  }


  override def loadClass(className: String, resolveIt: Boolean): Class[_] = {
    try {
      return super[JarClassLoader].loadClass(className, resolveIt)
    } catch {
      case nf: ClassNotFoundException =>
    }
    if (resolveIt) {
      subClassLoaders.foreach {
        subCL =>
          try {
            return subCL.loadClass(className)
          } catch {
            case nf: ClassNotFoundException =>
          }
      }
      subWeakClassLoaders.foreach {
        subCL =>
          try {
            subCL.get.map {
              m =>
                if (m.isInstanceOf[KevoreeJarClassLoader]) {
                  return m.asInstanceOf[KevoreeJarClassLoader].loadClass(className, false)
                }
            }
          } catch {
            case nf: ClassNotFoundException =>
          }
      }
    }

    throw new ClassNotFoundException(className)
  }

  override def loadClass(className: String): Class[_] = {
    loadClass(className, true)
  }

  override def getResourceAsStream(name: String): InputStream = {
    logger.debug("Get Ressource : " + name)
    val res = this.classpathResources.getResource(name)
    if (res != null) {
      new ByteArrayInputStream(res)
    } else {
      null
    }
  }

  override def getResource(s: String): URL = {
    if (classpathResources.asInstanceOf[KevoreeLazyJarResources].containKey(s)) {
      if (classpathResources.asInstanceOf[KevoreeLazyJarResources].getContentURL(s) == null) {
        val cleanName = if (s.contains("/")) {
          s.substring(s.lastIndexOf("/") + 1)
        } else {
          s
        }
        val tFile = File.createTempFile("dummy_kcl_temp", cleanName)
        tFile.deleteOnExit()
        val tWriter = new FileOutputStream(tFile)
        tWriter.write(classpathResources.asInstanceOf[KevoreeLazyJarResources].getResource(s))
        tWriter.close()
        new URL("file:///" + tFile.getAbsolutePath)
      } else {
        classpathResources.asInstanceOf[KevoreeLazyJarResources].getContentURL(s)
      }
    } else {
      logger.debug("getResource not found null=>"+s+" in "+classpathResources.asInstanceOf[KevoreeLazyJarResources].getLastLoadedJar)
      null
    }
  }

  def unload() {
    import scala.collection.JavaConversions._
    (this.getLoadedClasses.keySet().toList ++ List()).foreach {
      lc =>
        unloadClass(lc)
    }
  }

  override def findResource(s: java.lang.String): java.net.URL = {
    if (classpathResources.asInstanceOf[KevoreeLazyJarResources].containKey(s)) {
      if (classpathResources.asInstanceOf[KevoreeLazyJarResources].getContentURL(s) == null) {
        val cleanName = if (s.contains("/")) {
          s.substring(s.lastIndexOf("/") + 1)
        } else {
          s
        }

        val tFile = File.createTempFile("dummy_kcl_temp", cleanName)
        tFile.deleteOnExit()
        val tWriter = new FileOutputStream(tFile)
        tWriter.write(classpathResources.asInstanceOf[KevoreeLazyJarResources].getResource(s))
        tWriter.close()
        new URL("file:///" + tFile.getAbsolutePath)
      } else {
        classpathResources.asInstanceOf[KevoreeLazyJarResources].getContentURL(s)
      }
    } else {
      logger.debug("findResource not found null=>"+s+" in "+classpathResources.asInstanceOf[KevoreeLazyJarResources].getLastLoadedJar)
      null
    }
  }

  override def findResources(p1: String): Enumeration[URL] = {
    if (classpathResources.asInstanceOf[KevoreeLazyJarResources].containKey(p1)) {
      val url = if (classpathResources.asInstanceOf[KevoreeLazyJarResources].getContentURL(p1) == null) {
        val cleanName = if (p1.contains("/")) {
          p1.substring(p1.lastIndexOf("/") + 1)
        } else {
          p1
        }
        val tFile = File.createTempFile("dummy_kcl_temp", cleanName)
        tFile.deleteOnExit()
        val tWriter = new FileOutputStream(tFile)
        tWriter.write(classpathResources.asInstanceOf[KevoreeLazyJarResources].getResource(p1))
        tWriter.close()
        new URL("file:///" + tFile.getAbsolutePath)
      } else {
        classpathResources.asInstanceOf[KevoreeLazyJarResources].getContentURL(p1)
      }
      Collections.enumeration(Collections.singleton(url));
    } else {
      Collections.enumeration(new java.util.ArrayList[URL]())
    }
  }


  def printDump(){
    logger.debug("KCL : "+classpathResources.asInstanceOf[KevoreeLazyJarResources].getLastLoadedJar)
    subClassLoaders.foreach{ s =>
      logger.debug("    l->"+s.asInstanceOf[KevoreeJarClassLoader].asInstanceOf[KevoreeLazyJarResources].getLastLoadedJar)
    }
    subWeakClassLoaders.foreach{ s =>
      logger.debug("    w~>"+s.asInstanceOf[KevoreeJarClassLoader].asInstanceOf[KevoreeLazyJarResources].getLastLoadedJar)
    }
  }

}