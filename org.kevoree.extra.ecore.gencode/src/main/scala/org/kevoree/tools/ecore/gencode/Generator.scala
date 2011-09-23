package org.kevoree.tools.ecore.gencode

import java.io.File
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl
import org.eclipse.emf.common.util.URI
import scala.collection.JavaConversions._
import org.eclipse.emf.ecore.EPackage
import sub.Processor

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 21/09/11
 * Time: 23:05
 */

class Generator(rootDir: File, rootPackage: String) {//, log : Log) {

  def generate(ecoreFile: File) {
    val resource = new XMIResourceImpl(URI.createFileURI(ecoreFile.getAbsolutePath));
    resource.load(null);
    val location = rootDir.getAbsolutePath + "/" + rootPackage.replace(".","/")
    ProcessorHelper.checkOrCreateFolder(location)
    resource.getContents.foreach {
      elem => elem match {
        case pack:EPackage => Processor.process(location,pack,Some(rootPackage))
        case _ => println("No processor for class: " + elem.getClass)
      }
    }
  }

  /*
  private def process(pack: EPackage, classif: EClassifier) {
    System.out.println("Processing classifier:" + classif.getName)
  }
*/


}