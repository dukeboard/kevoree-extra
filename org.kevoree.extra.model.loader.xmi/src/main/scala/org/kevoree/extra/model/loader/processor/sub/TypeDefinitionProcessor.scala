package org.kevoree.extra.model.loader.processor.sub

import org.kevoree.extra.model.loader.processor.ProcessingContext
import tdp._
/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 17:58
 */

trait TypeDefinitionProcessor
  extends ComponentTypeDefinitionProcessor
  with ChannelTypeDefinitionProcessor
  with NodeTypeDefinitionProcessor
  with GroupTypeDefinitionProcessor
  with ServicePortTypeDefinitionProcessor
  with MessagePortTypeDefinitionProcessor {

  def loadTypeDefinitions(): Int = {

    val typeDefinitions = (ProcessingContext.xmiContent \\ "typeDefinitions")
    var i = 0
    typeDefinitions.foreach {
      //TODO: filter and send a model-collection of nodes to each model processor
      typeDef =>
        val typeDefId = "//@" + typeDef.label + "." + i
        typeDef.attributes.find(att => att.key.equals("type")) match {
          case Some(s) => {
            s.value.text match {
              case "kevoree:ComponentType" => loadComponentTypeDefinition(typeDefId, typeDef)
              case "kevoree:NodeType" => loadNodeTypeDefinition(typeDefId, typeDef)
              case "kevoree:ChannelType" => loadChannelTypeDefinition(typeDefId, typeDef)
              case "kevoree:MessagePortType" => loadMessagePortTypeDefinition(typeDefId, typeDef)
              case "kevoree:ServicePortType" => loadServicePortTypeDefinition(typeDefId, typeDef)
              case "kevoree:GroupType" => loadGroupTypeDefinition(typeDefId, typeDef)
              case _@e => throw new UnsupportedOperationException("Processor for TypeDefinitions has no mapping for type:" + e)
            }

          }
          case None => System.out.println("Galeere")
        }

        i += 1
    }
    i
  }

  def resolveTypeDefinitions() {

    val typeDefinitions = (ProcessingContext.xmiContent \\ "typeDefinitions")
    var i = 0
    typeDefinitions.foreach {
      //TODO: filter and send a model-collection of nodes to each model processor
      typeDef =>
        val typeDefId = "//@" + typeDef.label + "." + i
        typeDef.attributes.find(att => att.key.equals("type")) match {
          case Some(s) => {
            s.value.text match {
              case "kevoree:ComponentType" => resolveComponentTypeDefinition(typeDefId, typeDef)
              case "kevoree:NodeType" => resolveNodeTypeDefinition(typeDefId, typeDef)
              case "kevoree:ChannelType" => resolveChannelTypeDefinition(typeDefId, typeDef)
              case "kevoree:MessagePortType" => resolveMessagePortTypeDefinition(typeDefId, typeDef)
              case "kevoree:ServicePortType" => resolveServicePortTypeDefinition(typeDefId, typeDef)
              case "kevoree:GroupType" => resolveGroupTypeDefinition(typeDefId, typeDef)
              case _@e => throw new UnsupportedOperationException("Processor for TypeDefinitions has no mapping for type:" + e)
            }

          }
          case None => System.out.println("Galeere")
        }
        i += 1
    }
    i
  }


}