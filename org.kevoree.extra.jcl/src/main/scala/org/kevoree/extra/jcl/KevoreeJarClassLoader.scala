package org.kevoree.extra.jcl

import org.xeustechnologies.jcl.JarClassLoader
import java.io.{ByteArrayInputStream, InputStream}
import java.net.URL
import java.lang.{Class, String}
import ref.WeakReference

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 23/01/12
 * Time: 18:57
 */

class KevoreeJarClassLoader extends JarClassLoader {

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
      return super[JarClassLoader].loadClass(className,resolveIt)
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
                if(m.isInstanceOf[KevoreeJarClassLoader]){
                  return m.asInstanceOf[KevoreeJarClassLoader].loadClass(className,false)
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
    val res = this.classpathResources.getResource(name)
    if (res != null) {
      new ByteArrayInputStream(res)
    } else {
      null
    }
  }

  override def getResource(p1: String): URL = {
    classpathResources.asInstanceOf[KevoreeLazyJarResources].getContentURL(p1)
  }

  def unload() {
    import scala.collection.JavaConversions._
    (this.getLoadedClasses.keySet().toList ++ List()).foreach {
      lc =>
        unloadClass(lc)
    }
  }

}