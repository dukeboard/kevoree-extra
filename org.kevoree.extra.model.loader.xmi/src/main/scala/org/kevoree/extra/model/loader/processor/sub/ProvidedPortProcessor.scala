package org.kevoree.extra.model.loader.processor.sub

import xml.NodeSeq
import org.kevoree.extra.model.loader.processor.ProcessingContext
import org.kevoree._

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 25/09/11
 * Time: 09:54
 */

trait ProvidedPortProcessor {

  def loadProvidedPorts(parentId: String, parentNode: NodeSeq): Int = {
    val providedPortList = (parentNode \\ "provided")
    var i = 0
    providedPortList.foreach {
      port =>
        val portElem = KevoreePackage.createPortTypeRef
        ProcessingContext.map += parentId + "/@" + port.label + "." + i -> portElem
        i += 1
    }
    i
  }

  def resolveProvidedPorts(parentId: String, parentNode: NodeSeq, owner: ComponentType) {
    val providedPortList = (parentNode \\ "provided")
    var i = 0
    providedPortList.foreach {
      port =>
        val portElem = ProcessingContext.map(parentId + "/@" + port.label + "." + i).asInstanceOf[PortTypeRef]
        portElem.setName((port \ "@name").text)

         (port \ "@ref").headOption match {
          case Some(head) => {
            head.text.split(" ").foreach {
              typeDef =>
                ProcessingContext.map.get(typeDef) match {
                  case Some(s: PortType) => portElem.setRef(s)
                  case None => System.out.println("TypeDefinition not found in map ! lib:" + portElem.getName + " typeDef:" + typeDef)
                }
            }
          }
          case None => //No subtype for this library
        }

        owner.addProvided(portElem)
        i += 1

    }

  }
}