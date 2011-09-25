package org.kevoree.extra.model.loader.processor

import sub.{LibraryProcessor, TypeDefinitionProcessor, DeployUnitsProcessor, RepositoryProcessor}
import xml.{Node, NodeSeq}
import org.kevoree.extra.model.KevoreeDeserializer
import org.kevoree.{KevoreePackage, ContainerRoot}

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 12:30
 */

class ContainerRootProcessor(document: NodeSeq)
  extends KevoreeDeserializer
  with RepositoryProcessor
  with DeployUnitsProcessor
  with LibraryProcessor
  with TypeDefinitionProcessor {

  ProcessingContext.xmiContent = document
  ProcessingContext.containerRoot = KevoreePackage.createContainerRoot
  ProcessingContext.map = Map[String, Any]()

  override def deserialize(): ContainerRoot = {
    //debug(document.head)

    loadElements()

    //System.out.println(ProcessingContext.map.mkString("[",",\n ", "]"))

    resolveElements()

    ProcessingContext.containerRoot
  }

  private def loadElements() : Int = {
    val nbRepo = loadRepositories()
    val nbDu = loadDeployUnits()
    val nbType = loadTypeDefinitions()
    val nbLib = loadLibraries();
    val total = (nbDu + nbRepo + nbType + nbLib)
    System.out.println(total + " ModelElements loaded. " + nbRepo + ":Repositories " + nbDu +":DeployUnits " + nbType + ":TypeDefinitons " + nbLib + ":Libraries")
    total
  }

  private def resolveElements() {
    ProcessingContext.containerRoot.eContainer = ProcessingContext.containerRoot
    resolveRepositories()
    resolveDeployUnits()
    resolveLibraries()
    resolveTypeDefinitions()
  }


  private def debug(ns: Node) {
    System.out.println("RootLabel:" + ns.label)
    System.out.println("RootLabel:" + ns.namespace)
    System.out.println("RootLabel:" + ns.prefix)
    ns.attributes.asAttrMap.foreach {
      att =>
        System.out.println("RootAttribute:" + att.toString)
    }

  }


}