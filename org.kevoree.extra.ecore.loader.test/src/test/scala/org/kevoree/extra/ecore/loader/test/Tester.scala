package org.kevoree

import cloner.ModelCloner
import java.io.File
import loader.ContainerRootLoader
import org.kevoree.serializer.ModelSerializer
import xml.PrettyPrinter

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 03/10/11
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */

object Tester extends App {

  val cloner = new ModelCloner
  val serializer = new ModelSerializer

  val current = System.currentTimeMillis()
  val localModel = ContainerRootLoader.loadModel(new File(("/Users/duke/Documents/dev/dukeboard/kevoree/kevoree-platform/org.kevoree.platform.osgi.standalone/src/main/resources/defaultLibrary.kev")));

  localModel match {
    case Some(m) => {
      val bclonecurrent = System.currentTimeMillis()
      val clonedModel = cloner.clone(m)
      println(System.currentTimeMillis() - bclonecurrent)

      val result = serializer.serialize(clonedModel)
      
      println(result == serializer.serialize(m))
      
      println(System.currentTimeMillis() - current)
      val pp = new PrettyPrinter(3000,1)
      println(pp.format(result))
    }
    case None =>
  }
}