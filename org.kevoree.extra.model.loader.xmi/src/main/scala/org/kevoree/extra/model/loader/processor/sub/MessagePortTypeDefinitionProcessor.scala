package org.kevoree.extra.model.loader.processor.sub

import xml.NodeSeq
import org.kevoree.extra.model.loader.processor.ProcessingContext
import org.kevoree.{MessagePortType, ChannelType, KevoreePackage}

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 18:17
 */

trait MessagePortTypeDefinitionProcessor {

  def loadMessagePortTypeDefinition(typeDefId: String, typeDef: NodeSeq) {

    val typeDefElem = KevoreePackage.createMessagePortType

    ProcessingContext.map += typeDefId -> typeDefElem
  }

  def resolveMessagePortTypeDefinition(typeDefId: String, typeDef: NodeSeq) {
    val typeElem = ProcessingContext.map(typeDefId).asInstanceOf[MessagePortType]
    typeElem.eContainer = ProcessingContext.containerRoot
    typeElem.setName((typeDef \ "@name").text)

    ProcessingContext.containerRoot.addTypeDefinitions(typeElem)
  }


}