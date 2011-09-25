package org.kevoree.extra.model.loader.processor.sub

import xml.NodeSeq
import org.kevoree.extra.model.loader.processor.ProcessingContext
import org.kevoree.{DeployUnit, KevoreePackage, ComponentType}

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 18:17
 */

trait ComponentTypeDefinitionProcessor extends ProvidedPortProcessor {

  def loadComponentTypeDefinition(typeDefId: String, typeDef: NodeSeq) {

    val typeDefElem = KevoreePackage.createComponentType

    loadProvidedPorts(typeDefId, typeDef)

   ProcessingContext.map += typeDefId -> typeDefElem
  }

  def resolveComponentTypeDefinition(typeDefId: String, typeDef: NodeSeq) {

    val typeElem = ProcessingContext.map(typeDefId).asInstanceOf[ComponentType]
    typeElem.eContainer = ProcessingContext.containerRoot
    typeElem.setBean((typeDef \ "@bean").text)
    typeElem.setFactoryBean((typeDef \ "@factoryBean").text)
    typeElem.setName((typeDef \ "@name").text)
    typeElem.setStartMethod((typeDef \ "@startMethod").text)
    typeElem.setStopMethod((typeDef \ "@stopMethod").text)
    typeElem.setUpdateMethod((typeDef \ "@updateMethod").text)

    (typeDef \ "@deployUnits").headOption match {
          case Some(head) => {
            head.text.split(" ").foreach {
              du =>
                ProcessingContext.map.get(du) match {
                  case Some(s: DeployUnit) => typeElem.addDeployUnits(s)
                  case None => System.out.println("DeployUnit not found in map ! typeDef:" + typeElem.getName + " du:" + du)
                }
            }
          }
          case None => //No deployUnit for this repository
        }

    resolveProvidedPorts(typeDefId, typeDef, typeElem)

    ProcessingContext.containerRoot.addTypeDefinitions(typeElem)
  }


}