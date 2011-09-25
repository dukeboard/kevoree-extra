package org.kevoree.tools.ecore.gencode.model

import scala.collection.JavaConversions._
import org.kevoree.tools.ecore.gencode.ProcessorHelper
import org.eclipse.emf.ecore.{EClass, EClassifier, EPackage}
import scala.None

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 22/09/11
 * Time: 08:32
 */

object Processor extends TraitGenerator with PackageFactoryGenerator with ClassGenerator{

  def process(location: String, pack: EPackage, containerPack: Option[String]) {
    //log.debug("Processing package: " + containerPack + "." + pack.getName)
    val dir = location + "/" + pack.getName
    val thisPack =
      containerPack match {
        case None => pack.getName
        case Some(container) => container + "." + pack.getName
      }

    ProcessorHelper.checkOrCreateFolder(dir)
    ProcessorHelper.checkOrCreateFolder(dir + "/impl")
    generatePackageFactory(dir, thisPack, pack)
    generateContainerTrait(dir, thisPack, pack)
    //generateMutableTrait(dir, thisPack, pack)
    pack.getEClassifiers.foreach(c => process(dir, thisPack, c, pack))
    pack.getESubpackages.foreach(p => process(dir, pack, Some(thisPack)))
  }


  private def process(location: String, pack: String, cls: EClassifier, packElement: EPackage) {
    //log.debug("Processing classifier:" + cls.getName)
    cls match {
      case cl: EClass => {
        generateClass(location, pack, cl, packElement)
        generateCompanion(location, pack, cl, packElement)
      }
      case _ => println("No processor found for classifier: " + cls.getClass)
    }

  }



}