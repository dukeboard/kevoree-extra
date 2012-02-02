package org.kevoree.extra.jcl

import java.lang.String
import java.net.{URLStreamHandler, URLStreamHandlerFactory, URL}


/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 30/01/12
 * Time: 10:52
 */

object Tester extends App {

  /*
  val bigURL = new URL("jar:/Users/duke/Documents/dev/dukeboard/kevoree/kevoree-library/frascati/org.kevoree.library.frascati.frascatiNode/target/org.kevoree.library.frascati.frascatiNode-1.6.0-SNAPSHOT.jar!/lib/frascati-factory-tools-1.4.jar!/META-INF/MANIFEST.MF")
  bigURL.openConnection()


  URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory{
    def createURLStreamHandler(p1: String): URLStreamHandler = new KevoreeJarClassLoaderHandler(null)
  })

  val key = "java.protocol.handler.pkgs";
  var newValue = "org.kevoree.extra.jcl.classpath";
  //System.setProperty(key, newValue);
  println(System.getProperty(key))

  val u = new URL("classpath:some/package/resource.extension")
  u.openConnection()
  */

/*
  for (i <- 0 until 10) {

    val jcl2 = new KevoreeJarClassLoader
    jcl2.add("/Users/duke/Documents/dev/dukeboard/kevoree/kevoree-platform/org.kevoree.platform.osgi.standalone/target/org.kevoree.platform.osgi.standalone-1.5.1-SNAPSHOT.jar")
    val jcl = new KevoreeJarClassLoader
    jcl.addSubClassLoader(jcl2)
    jcl2.loadClass("org.kevoree.platform.osgi.standalone.App")


    println("iteration i=" + i)
  }


  System.gc()
*/

}