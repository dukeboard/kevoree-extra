package org.kevoree.extra.jcl

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 30/01/12
 * Time: 10:52
 */

object Tester extends App {

  for (i <- 0 until 100) {

    val jcl2 = new KevoreeJarClassLoader
    jcl2.add("/Users/duke/Documents/dev/dukeboard/kevoree/kevoree-platform/org.kevoree.platform.osgi.standalone/target/org.kevoree.platform.osgi.standalone-1.5.1-SNAPSHOT.jar")
    val jcl = new KevoreeJarClassLoader
    jcl.addSubClassLoader(jcl2)
    jcl2.loadClass("org.kevoree.platform.osgi.standalone.App")


println("iteration i="+i)
  }
  
  
  System.gc()


}