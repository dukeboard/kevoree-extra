package org.kevoree.extra.model.loader.processor.sub

import xml.NodeSeq
import org.kevoree.extra.model.loader.processor.ProcessingContext
import org.kevoree.{ServicePortType, MessagePortType, KevoreePackage}

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 18:17
 */

trait ServicePortTypeDefinitionProcessor {

  def loadServicePortTypeDefinition(typeDefId: String, typeDef: NodeSeq) {

    val typeDefElem = KevoreePackage.createServicePortType

    ProcessingContext.map += typeDefId -> typeDefElem
  }

  def resolveServicePortTypeDefinition(typeDefId: String, typeDef: NodeSeq) {
    val typeElem = ProcessingContext.map(typeDefId).asInstanceOf[ServicePortType]
    typeElem.eContainer = ProcessingContext.containerRoot
    typeElem.setName((typeDef \ "@name").text)
    
    ProcessingContext.containerRoot.addTypeDefinitions(typeElem)
  }


}