package org.kevoree.extra.model.loader.processor.sub

import org.kevoree.extra.model.loader.processor.ProcessingContext
import org.kevoree._

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 16:06
 */

trait LibraryProcessor {

  def loadLibraries() : Int = {
    val libList = (ProcessingContext.xmiContent \\ "libraries")
    var i = 0
    libList.foreach {
      lib =>
        val libElem = KevoreePackage.createTypeLibrary
        ProcessingContext.map += "//@" + lib.label + "." + i -> libElem
        i += 1
    }
    i
  }

  def resolveLibraries() {
    val libList = (ProcessingContext.xmiContent \\ "libraries")
    var i = 0
    libList.foreach {
      lib =>
        val libElem = ProcessingContext.map("//@" + lib.label + "." + i).asInstanceOf[TypeLibrary]
        libElem.eContainer = ProcessingContext.containerRoot
        libElem.setName((lib \ "@name").text)

        (lib \ "@subTypes").headOption match {
          case Some(head) => {
            head.text.split(" ").foreach {
              typeDef =>
                ProcessingContext.map.get(typeDef) match {
                  case Some(s: TypeDefinition) => libElem.addSubTypes(s)
                  case None => System.out.println("TypeDefinition not found in map ! lib:" + libElem.getName + " typeDef:" + typeDef)
                }
            }
          }
          case None => //No subtype for this library
        }

        ProcessingContext.containerRoot.addLibraries(libElem)
        i += 1
    }

  }

}