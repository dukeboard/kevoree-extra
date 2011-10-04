package org.kevoree.tools.ecore.gencode.loader

import org.eclipse.emf.ecore.{EPackage, EClass}
import org.kevoree.tools.ecore.gencode.ProcessorHelper
import java.io.{FileOutputStream, PrintWriter, File}
import scala.collection.JavaConversions._

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 29/09/11
 * Time: 17:24
 */

class BasicElementLoader(genDir: String, genPackage: String, elementType: EClass, context: String, factory: String, modelingPackage: EPackage, modelPackage : String) {

  def generateLoader() {
    //System.out.println("Generation of loader for " + elementType.getName)

    //Creation of the generation dir
    ProcessorHelper.checkOrCreateFolder(genDir)
    val file = new File(genDir + "/" + elementType.getName + "Loader.scala")

    if (!file.exists()) {
      //Does not override existing file. Should have been removed before if required.

      val subLoaders = generateSubs(elementType)

      val pr = new PrintWriter(new FileOutputStream(file))
      //System.out.println("Classifier class:" + cls.getClass)

      pr.println("package " + genPackage + ";")
      pr.println()
      pr.println("import xml.NodeSeq")
      pr.println("import scala.collection.JavaConversions._")
      //Import parent package (org.kevoree.sub => org.kevoree._)
      pr.println("import " + modelPackage + "._")
      pr.println("import " + genPackage.substring(0,genPackage.lastIndexOf(".")) + "._")
      pr.println()

      //Generates the Trait
      pr.print("trait " + elementType.getName + "Loader")
      if (subLoaders.size > 0) {
        var stringListSubLoaders = List[String]()
        subLoaders.foreach(sub => stringListSubLoaders = stringListSubLoaders ++ List(sub.getName + "Loader"))
        pr.println(stringListSubLoaders.mkString(" extends ", " with ", " {"))
      } else {
        pr.println("{")
      }

      pr.println()

      generateCollectionLoadingMethod(pr)
      pr.println("")
      generateElementLoadingMethod(pr)
      pr.println("")
      generateCollectionResolutionMethod(pr)
      pr.println("")
      generateElementResolutionMethod(pr)
      pr.println("")

      pr.println("}")

      pr.flush()
      pr.close()

    }
  }

  private def generateSubs(currentType: EClass): List[EClass] = {
    //var factory = genPackage.substring(genPackage.lastIndexOf(".") + 1)
    //factory = factory.substring(0, 1).toUpperCase + factory.substring(1) + "Package"
    //modelingPackage.getEClassifiers.filter(cl => !cl.equals(elementType)).foreach{ ref =>

    var listContainedElementsTypes = List[EClass]()

    currentType.getEAllContainments.foreach {
      ref =>
        if (!ref.getEReferenceType.isInterface) {
          //Generates loaders for simple elements
          val el = new BasicElementLoader(genDir, genPackage, ref.getEReferenceType, context, factory, modelingPackage, modelPackage)
          el.generateLoader()

        } else {
          //System.out.println("ReferenceType of " + ref.getName + " is an interface. Not supported yet.")
          val el = new InterfaceElementLoader(genDir + "/sub/", genPackage + ".sub", ref.getEReferenceType, context, factory, modelingPackage, modelPackage)
          el.generateLoader()
        }
        if (!listContainedElementsTypes.contains(ref.getEReferenceType)) {
          listContainedElementsTypes = listContainedElementsTypes ++ List(ref.getEReferenceType)
        }
    }

    //System.out.print(currentType.getName + " Uses:{")
    //listContainedElementsTypes.foreach(elem => System.out.print(elem.getName + ","))
    //System.out.println()
    listContainedElementsTypes
  }

  private def generateCollectionLoadingMethod(pr: PrintWriter) {
    pr.println("\t\tdef load" + elementType.getName + "(parentId: String, parentNode: NodeSeq, refNameInParent : String) : List[" + elementType.getName + "] = {")
    pr.println("\t\t\t\tvar loadedElements = List[" + elementType.getName + "]()")
    pr.println("\t\t\t\tvar i = 0")
    pr.println("\t\t\t\tval " + elementType.getName.substring(0, 1).toLowerCase + elementType.getName.substring(1) + "List = (parentNode \\\\ refNameInParent)") //\"" + elementNameInParent + "\")")
    pr.println("\t\t\t\t" + elementType.getName.substring(0, 1).toLowerCase + elementType.getName.substring(1) + "List.foreach { xmiElem =>")
    pr.println("\t\t\t\t\t\tval currentElementId = parentId + \"/@\" + refNameInParent + \".\" + i")
    pr.println("\t\t\t\t\t\tloadedElements = loadedElements ++ List(load" + elementType.getName + "Element(currentElementId,xmiElem))")
    pr.println("\t\t\t\t\t\ti += 1")
    pr.println("\t\t\t\t}")
    pr.println("\t\t\t\tloadedElements")
    pr.println("\t\t}")
  }

  private def generateElementLoadingMethod(pr: PrintWriter) {
    pr.println("\t\tdef load" + elementType.getName + "Element(elementId: String, elementNode: NodeSeq) : " + elementType.getName + " = {")
    pr.println("\t\t")
    pr.println("\t\t\t\tval modelElem = " + factory + ".create" + elementType.getName)
    pr.println("\t\t\t\t" + context + ".map += elementId -> modelElem")
    pr.println("")
    elementType.getEAllContainments.foreach {
      ref =>

        if (ref.getUpperBound == 1) {
          pr.println("\t\t\t\t(elementNode \\ \"" + ref.getName + "\").headOption.map{head =>")
          pr.println("\t\t\t\t\t\tval " + ref.getName + "ElementId = elementId + \"/@" + ref.getName + "\"")
          pr.println("\t\t\t\t\t\tval " + ref.getName + " = load" + ref.getEReferenceType.getName + "Element(" + ref.getName + "ElementId, head)")
          pr.println("\t\t\t\t\t\tmodelElem.set" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1) + "(" + ref.getName + ")")
          pr.println("\t\t\t\t\t\t" + ref.getName + ".eContainer = modelElem")
          pr.println("\t\t\t\t}")
        } else {
          pr.println("\t\t\t\tval " + ref.getName + " = load" + ref.getEReferenceType.getName + "(elementId, elementNode, \"" + ref.getName + "\")")
          pr.println("\t\t\t\tmodelElem.set" + ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1) + "(" + ref.getName + ")")
          pr.println("\t\t\t\t" + ref.getName + ".foreach{ e => e.eContainer = modelElem }")
        }
        pr.println("")
    }
    pr.println("\t\t\t\tmodelElem")
    pr.println("\t\t}")
  }


  private def generateCollectionResolutionMethod(pr: PrintWriter) {
    pr.println("\t\tdef resolve" + elementType.getName + "(parentId: String, parentNode: NodeSeq, refNameInParent : String) {")
    pr.println("\t\t\t\tvar i = 0")
    pr.println("\t\t\t\tval " + elementType.getName.substring(0, 1).toLowerCase + elementType.getName.substring(1) + "List = (parentNode \\\\ refNameInParent)") //\"" + elementNameInParent + "\")")
    pr.println("\t\t\t\t" + elementType.getName.substring(0, 1).toLowerCase + elementType.getName.substring(1) + "List.foreach { xmiElem =>")
    pr.println("\t\t\t\t\t\tval currentElementId = parentId + \"/@\" + refNameInParent + \".\" + i")
    pr.println("\t\t\t\t\t\tresolve" + elementType.getName + "Element(currentElementId,xmiElem)")
    pr.println("\t\t\t\t\t\ti += 1")
    pr.println("\t\t\t\t}")
    pr.println("\t\t}")
  }


  private def generateElementResolutionMethod(pr: PrintWriter) {
    pr.println("\t\tdef resolve" + elementType.getName + "Element(elementId: String, elementNode: NodeSeq) {")
    pr.println("")
    pr.println("\t\t\t\tval modelElem = " + context + ".map(elementId).asInstanceOf[" + elementType.getName + "]")
    pr.println("")
    elementType.getEAllAttributes.foreach {
      att =>
        val methName = "set" + att.getName.substring(0, 1).toUpperCase + att.getName.substring(1)
        pr.println("\t\t\t\tval " + att.getName + "Val = (elementNode \\ \"@" + att.getName + "\").text")
        pr.println("\t\t\t\tif(!" + att.getName + "Val.equals(\"\")){")
        pr.println("\t\t\t\t\t\tmodelElem." + methName + "(" + ProcessorHelper.convertType(att.getEAttributeType.getInstanceClassName) + ".valueOf(" + att.getName + "Val))")
        pr.println("\t\t\t\t}")
        pr.println("")
    }
    pr.println("")

    elementType.getEAllContainments.foreach {
      ref =>
        if (ref.getUpperBound == 1) {
          pr.println("\t\t\t\t(elementNode \\ \"" + ref.getName + "\").headOption.map{head => ")
          pr.println("\t\t\t\t\t\tresolve" + ref.getEReferenceType.getName + "Element(elementId + \"/@" + ref.getName + "\", head)")
          pr.println("\t\t\t\t}")
        } else {
          pr.println("\t\t\t\tresolve" + ref.getEReferenceType.getName + "(elementId, elementNode, \"" + ref.getName + "\")")
        }
        pr.println("")
    }


    elementType.getEAllReferences.filter(ref => !elementType.getEAllContainments.contains(ref)).foreach {
      ref =>
        pr.println("\t\t\t\t(elementNode \\ \"@" + ref.getName + "\").headOption match {")
        pr.println("\t\t\t\t\t\tcase Some(head) => {")
        pr.println("\t\t\t\t\t\t\t\thead.text.split(\" \").foreach {")
        pr.println("\t\t\t\t\t\t\t\t\t\txmiRef =>")
        pr.println("\t\t\t\t\t\t\t\t\t\t\t\t" + context + ".map.get(xmiRef) match {")
        var methName: String = ""
        if (ref.getUpperBound == 1) {
          methName = "set"
        } else {
          methName = "add"
        }
        methName += ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1)
        pr.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\tcase Some(s: " + ref.getEReferenceType.getName + ") => modelElem." + methName + "(s)")
        pr.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\tcase None => System.out.println(\"" + ref.getEReferenceType.getName + " not found in map ! xmiRef:\" + xmiRef)")
        pr.println("\t\t\t\t\t\t\t\t\t\t\t\t}")
        pr.println("\t\t\t\t\t\t\t\t\t\t}")
        pr.println("\t\t\t\t\t\t\t\t}")
        pr.println("\t\t\t\t\t\tcase None => //No subtype for this library")
        pr.println("\t\t\t\t}")
    }

    pr.println("\t\t}")
  }

}