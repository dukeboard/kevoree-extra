package org.kevoree.extra.model.loader

import org.kevoree.extra.model.ModelLoader
import org.kevoree.ContainerRoot
import java.io.File
import javax.xml.parsers.{SAXParserFactory, SAXParser}

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 09:43
 */

class XmiLoader extends ModelLoader {

  def loadModel(file : File) : Option[ContainerRoot] = {

    val factory = SAXParserFactory.newInstance();
	  val saxParser = factory.newSAXParser();
    /*
    var handler = new KevModelHandler
    saxParser.parse(file.getAbsolutePath, handler);
    */
    None
  }

}

