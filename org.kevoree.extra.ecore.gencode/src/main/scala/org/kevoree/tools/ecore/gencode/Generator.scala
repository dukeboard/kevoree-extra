package org.kevoree.tools.ecore.gencode

import java.io.File
import loader.LoaderGenerator
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EPackage
import scala.collection.JavaConversions._

import model.Processor

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 21/09/11
 * Time: 23:05
 */

class Generator(rootDir: File, rootPackage: String) {//, log : Log) {

  def generateModel(ecoreFile: File) {
    val resource = new XMIResourceImpl(URI.createFileURI(ecoreFile.getAbsolutePath));
    resource.load(null);
    val location = rootDir.getAbsolutePath + "/" + rootPackage.replace(".","/")
    ProcessorHelper.checkOrCreateFolder(location)
    resource.getContents.foreach {
      elem =>
        elem match {
        case pack:EPackage => Processor.process(location,pack,Some(rootPackage))
        case _ => println("No model generator for root element of class: " + elem.getClass)
      }
    }
  }

  def generateLoader(ecoreFile: File) {
    val resource = new XMIResourceImpl(URI.createFileURI(ecoreFile.getAbsolutePath));
    resource.load(null);
    val location = rootDir.getAbsolutePath + "/" + rootPackage.replace(".","/")
    ProcessorHelper.checkOrCreateFolder(location)
    System.out.println("Launching loader generation in:" + location)
    resource.getContents.foreach {
      elem => elem match {
        case pack:EPackage => {
          val loaderGenerator = new LoaderGenerator(location,rootPackage, pack)
          loaderGenerator.generateLoader()
        }
        case _ => println("No loader generator for root element of class: " + elem.getClass)
      }
    }
  }


}