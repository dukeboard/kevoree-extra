package org.kevoree.tools.ecore.gencode.loader

import org.kevoree.tools.ecore.gencode.ProcessorHelper
import java.io.{File, FileOutputStream, PrintWriter}
import scala.collection.JavaConversions._
import org.eclipse.emf.ecore.{EPackage, EClass}

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 25/09/11
 * Time: 14:20
 */



class RootLoader(genDir: String, genPackage: String, elementNameInParent: String, elementType: EClass, modelingPackage : EPackage) {

  def generateLoader() {
    ProcessorHelper.checkOrCreateFolder(genDir)
    val pr = new PrintWriter(new FileOutputStream(new File(genDir + "/" + elementType.getName + "Loader.scala")))
    //System.out.println("Classifier class:" + cls.getClass)

    generateContext()
    generateSubs(elementType)

    pr.println("package " + genPackage + ";")
    pr.println()
    pr.println("import xml.{XML,NodeSeq}")
    pr.println("import java.io.File")
    pr.println("import " + genPackage + "._")
    pr.println("import " + genPackage + ".sub._")
    pr.println()

    pr.print("object " + elementType.getName + "Loader")
    var extentions : String = ""
    elementType.getEAllContainments.foreach{ ref =>
      extentions match {
        case "" => extentions += "\n\t\t extends " + ref.getEReferenceType.getName + "Loader"
        case _ =>  extentions += "\n\t\t with " + ref.getEReferenceType.getName + "Loader"
      }
    }
    pr.println(extentions +" {")

    pr.println("")

    generateLoadMethod(pr)
    generateDeserialize(pr)

    generateLoadElementsMethod(pr)
    generateResolveElementsMethod(pr)

    pr.println("")
    pr.println("}")

    pr.flush()
    pr.close()
  }

  private def generateContext() {
    val el = new ContextGenerator(genDir, genPackage, elementType)
    el.generateContext()
  }

  private def generateSubs(currentType:EClass) {
    var factory = genPackage.substring(genPackage.lastIndexOf(".") + 1)
    factory = factory.substring(0,1).toUpperCase + factory.substring(1) +"Package"
    //modelingPackage.getEClassifiers.filter(cl => !cl.equals(elementType)).foreach{ ref =>
    currentType.getEAllContainments.foreach{ ref =>
      val el = new ElementLoader(genDir + "/sub/", genPackage + ".sub", ref.getName, ref.getEReferenceType, currentType, elementType.getName + "LoadContext",factory,modelingPackage)
      el.generateLoader()

      if (ref.getEReferenceType.isInterface) {
        LoaderGenerator.getConcreteSubTypes(ref.getEReferenceType).foreach {
          concreteType =>
            val el = new ElementLoader(genDir + "/sub/", genPackage + ".sub",  ref.getName, concreteType, currentType, elementType.getName + "LoadContext", factory, modelingPackage)
            el.generateLoader()
        }
      }
      generateSubs(ref.getEReferenceType)
    }
  }

  private def generateLoadMethod(pr: PrintWriter) {
    pr.println("\t\tdef loadModel(file: File) : Option[" + elementType.getName + "] = {")
    pr.println("\t\t\t\tval xmlStream = XML.loadFile(file)")
    pr.println("\t\t\t\tval document = NodeSeq fromSeq xmlStream")
    pr.println("\t\t\t\tdocument.headOption match {")
    pr.println("\t\t\t\t\t\tcase Some(rootNode) => Some(deserialize(rootNode))")
    pr.println("\t\t\t\t\t\tcase None => System.out.println(\"" + elementType.getName + "Loader::Noting at the root !\");None")
    pr.println("\t\t\t\t}")
    pr.println("\t\t}")
  }


  private def generateDeserialize(pr: PrintWriter) {

    val context = elementType.getName + "LoadContext"
    val rootContainerName = elementType.getName.substring(0,1).toLowerCase + elementType.getName.substring(1)
    var factory = genPackage.substring(genPackage.lastIndexOf(".") + 1)
    factory = factory.substring(0,1).toUpperCase + factory.substring(1) +"Package"

    pr.println("\t\tprivate def deserialize(rootNode: NodeSeq): ContainerRoot = {")

    pr.println(context + "." + rootContainerName + " = " + factory + ".create"+elementType.getName)
    pr.println(context + ".xmiContent = rootNode")
    pr.println(context + ".map = Map[String, Any]()")
    pr.println(context + ".stats = Map[String, Int]()")

    pr.println("\t\t\t\tloadElements(rootNode)")
    pr.println("\t\t\t\tresolveElements(rootNode)")
    pr.println("\t\t\t\t"+context+"."+rootContainerName)

    pr.println("\t\t}")
  }


  private def generateLoadElementsMethod(pr: PrintWriter) {

    pr.println("\t\tprivate def loadElements(rootNode: NodeSeq) {")

    elementType.getEAllContainments.foreach{ ref =>
       pr.println("\t\t\t\tload" + ref.getEReferenceType.getName + "s(\"/\", rootNode)")
    }
    pr.println("\t\t}")
  }

  private def generateResolveElementsMethod(pr: PrintWriter) {
    val context = elementType.getName + "LoadContext"
    val rootContainerName = elementType.getName.substring(0,1).toLowerCase + elementType.getName.substring(1)
    pr.println("\t\tprivate def resolveElements(rootNode: NodeSeq) {")
    //ProcessingContext.containerRoot.eContainer = ProcessingContext.containerRoot
    elementType.getEAllContainments.foreach{ ref =>
       pr.println("\t\t\t\tresolve" + ref.getEReferenceType.getName + "s(\"/\", rootNode,"+context + "." + rootContainerName +")")
    }
   pr.println("\t\t}")
  }

}