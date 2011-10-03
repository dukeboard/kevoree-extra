package org.kevoree.extra.model.loader

import org.kevoree.extra.model.ModelLoader
import org.kevoree.ContainerRoot
import java.io.File
import java.lang.System
import xml.{NodeSeq, XML}

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 09:43
 */

class XmiLoaderPureScala extends ModelLoader {



  def loadModel(file: File) : Option[ContainerRoot] = {

    val xmlStream = XML.loadFile(file)

    //System.out.println(xmlStream)
    //System.out.println("XmlStream:" + xmlStream.getClass)
    //System.out.println("XmlStreamChild:" + xmlStream.child.getClass)

    val document = NodeSeq fromSeq xmlStream

    document.headOption match {
    case Some(rootNode) =>
        Some(ContainerRootProcessorFactory.getDeserializer(rootNode.namespace,document).deserialize())
      case None => System.out.println("ContainerRootHandler::Noting at the root !");None
    }
  }

}

