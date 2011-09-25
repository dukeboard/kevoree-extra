package org.kevoree.extra.model.loader

import org.kevoree.extra.model.{KevoreeDeserializer, KevoreeDeserializerFactory}
import processor.ContainerRootProcessor
import xml.NodeSeq

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 12:36
 */

object ContainerRootProcessorFactory extends KevoreeDeserializerFactory {

  override def getDeserializer (modelNamespace: String, ns : NodeSeq): KevoreeDeserializer = {
    modelNamespace match {
      case "http://kevoree/1.0" => new ContainerRootProcessor(ns)
      case _ => throw new UnsupportedOperationException("No deserializer found for namespace:" + modelNamespace)
    }
  }

}