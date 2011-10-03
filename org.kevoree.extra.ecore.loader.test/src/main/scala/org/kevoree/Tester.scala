package org.kevoree

import java.io.File
import org.kevoree.serializer.ModelSerializer

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 03/10/11
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */

object Tester extends App {
  val localModel = ContainerRootLoader.loadModel(new File(("/Users/duke/Documents/dev/dukeboard/kevoree-extra/org.kevoree.extra.ecore.loader.test/src/test/resources/defaultlibs.kev")));
  localModel match {
    case Some(m) => {

      val serializer = new ModelSerializer

      println(serializer.serialize(m))
    }
    case None =>
  }
}