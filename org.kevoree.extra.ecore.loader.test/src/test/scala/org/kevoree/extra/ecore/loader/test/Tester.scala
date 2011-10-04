package org.kevoree

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

  val current = System.currentTimeMillis()
  val localModel = ContainerRootLoader.loadModel(new File(("/Users/duke/Documents/dev/dukeboard/kevoree-experiment/org.kevoree.experiment.smartForest/duke.irisa.fr-generated/models/Models580")));

  localModel match {
    case Some(m) => {


      val serializer = new ModelSerializer

      val result = serializer.serialize(m)
       println(System.currentTimeMillis() - current)
      val pp = new PrettyPrinter(3000,1)
      println(pp.format(result))
    }
    case None =>
  }
}