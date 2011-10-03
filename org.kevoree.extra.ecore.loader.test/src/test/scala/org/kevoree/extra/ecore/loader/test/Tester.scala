package org.kevoree.extra.ecore.loader.test

import org.kevoree.ContainerRootLoader
import java.io.File
import org.junit.Assert._
import org.kevoree.serializer.ModelSerializer

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 03/10/11
 * Time: 14:52
 * To change this template use File | Settings | File Templates.
 */

object Tester extends App {
  val localModel = ContainerRootLoader.loadModel(new File(getClass.getResource("/defaultlibs.kev").toURI));
  localModel match {
    case Some(m) => {
      
      m.getTypeDefinitions.foreach{ td =>
                                         td.getDictionaryType.map{ dt =>
                                             dt.getAttributes.foreach{ att =>
                                                 println("=>"+att.getName)
                                             }
                                         }
      }
      
      val serializer = new ModelSerializer

      println(serializer.serialize(m))
    }
    case None => fail("Model not loaded!")
  }
}