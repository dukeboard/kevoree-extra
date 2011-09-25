package org.kevoree.tools.ecore.gencode.loader

import java.io.{File, FileOutputStream, PrintWriter}
import org.kevoree.tools.ecore.gencode.ProcessorHelper
import scala.collection.JavaConversions._
import org.eclipse.emf.ecore.{EPackage, EClass}


//, EClassifier,
/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 25/09/11
 * Time: 10:38
 */

class ElementLoader(genDir: String, genPackage: String, elementNameInParent: String, elementType: EClass, parentType: EClass, context: String, factory: String, modelingPackage: EPackage) {

  def generateLoader() {

    //Creation of the generation dir
    ProcessorHelper.checkOrCreateFolder(genDir)
    val file = new File(genDir + "/" + elementType.getName + "Loader.scala")

    if (!file.exists()) {//Does not override existing file. Should have been removed before if required.
      val pr = new PrintWriter(new FileOutputStream(file))
      //System.out.println("Classifier class:" + cls.getClass)

      pr.println("package " + genPackage + ";")
      pr.println()
      pr.println("import xml.NodeSeq")
      pr.println("import scala.collection.JavaConversions._")
      //Import parent package (org.kevoree.sub => org.kevoree._)
      pr.println("import " + genPackage.substring(0, genPackage.lastIndexOf(".")) + "._")
      pr.println()

      pr.println()

      //Generates the Trait
      pr.print("trait " + elementType.getName + "Loader")

      var extentions : List[String] = List[String]()
      if (elementType.isInterface) {

        //If the EClass is an Interface, look for its concrete subClasses, add as extentions
        LoaderGenerator.getConcreteSubTypes(elementType).foreach {
          concreteType => if(!extentions.contains(concreteType.getName)) extentions = extentions ++ List(concreteType.getName)
        }

      } else {
        elementType.getEAllContainments.foreach {
          ref => if(!extentions.contains(ref.getEReferenceType.getName)) extentions = extentions ++ List(ref.getEReferenceType.getName)
        }
      }
      pr.println(extentions.mkString("\n\t\textends ","\n\t\twith ","") + " {")
      pr.println()

      generateLoadingMethod(pr)
      generateResolvingMethod(pr)

      pr.println("}")

      pr.flush()
      pr.close()
    }
  }


  private def generateLoadingMethod(pr: PrintWriter) {

    pr.println("\t\tdef load" + elementType.getName + "s(parentId: String, parentNode: NodeSeq): Int = {")
    pr.println("\t\t\t\tval " + elementNameInParent + "List = (parentNode \\\\ \"" + elementNameInParent + "\")")
    pr.println("\t\t\t\tvar i = 0")
    pr.println("\t\t\t\t" + elementNameInParent + "List.foreach { xmiElem =>")
    if (elementType.isInterface) {
      pr.println("\t\t\t\tval typeDefId = parentId + \"/@" + elementNameInParent + ".\" + i")
      pr.println("\t\t\t\txmiElem.attributes.find(att => att.key.equals(\"type\")) match {")
      pr.println("\t\t\t\t\t\tcase Some(s) => {")
      pr.println("\t\t\t\t\t\t\t\ts.value.text match {")
      LoaderGenerator.getConcreteSubTypes(elementType).foreach {
        concreteType =>
          pr.println("\t\t\t\t\t\t\t\t\t\tcase \"" + modelingPackage.getName + ":" + concreteType.getName + "\" => {")
          pr.println("\t\t\t\t\t\t\t\t\t\t\t\t\tval modelElem = " + factory + ".create" + concreteType.getName)
          pr.println("\t\t\t\t\t\t\t\t\t\t\t\t\t" + context + ".map += typeDefId -> modelElem")
          pr.println("\t\t\t\t\t\t\t\t\t\t\t}")
      }
      pr.println("\t\t\t\t\t\t\t\t\t\tcase _@e => throw new UnsupportedOperationException(\"Processor for TypeDefinitions has no mapping for type:\" + e)")
      pr.println("\t\t\t\t\t\t\t\t}")
      pr.println("\t\t\t\t\t\t}")
      pr.println("\t\t\t\t\t\tcase None => System.out.println(\"No 'type' attribute.\")")
      pr.println("\t\t\t\t}")

    } else {

      pr.println("\t\t\t\t\t\tval modelElem = " + factory + ".create" + elementType.getName)
      pr.println("\t\t\t\t\t\t" + context + ".map += parentId + \"/@\" + xmiElem.label + \".\" + i -> modelElem")

    }
    pr.println("\t\t\t\t\t\ti += 1")
    pr.println("\t\t\t\t}")
    pr.println("\t\t\t\ti")
    pr.println("\t\t}")
  }


  private def generateResolvingMethod(pr: PrintWriter) {

    pr.println("\t\tdef resolve" + elementType.getName + "s(parentId: String, parentNode: NodeSeq, owner: " + parentType.getName + ") {")
    pr.println("\t\t\t\tval " + elementNameInParent + "List = (parentNode \\\\ \"" + elementNameInParent + "\")")
    pr.println("\t\t\t\tvar i = 0")
    pr.println("\t\t\t\t" + elementNameInParent + "List.foreach { xmiElem =>")

    pr.println("\t\t\t\t\t\tval modelElem = " + context + ".map(parentId + \"/@\" + xmiElem.label + \".\" + i).asInstanceOf[" + elementType.getName + "]")
    pr.println("\t\t\t\t\t\tmodelElem.eContainer = owner")
    elementType.getEAllAttributes.foreach {
      att =>
        val methName = "set" + att.getName.substring(0, 1).toUpperCase + att.getName.substring(1)
        pr.println("\t\t\t\t\t\tmodelElem." + methName + "(" + ProcessorHelper.convertType(att.getEAttributeType.getInstanceClassName) + ".valueOf((xmiElem \\ \"@" + att.getName + "\").text))")
    }
    pr.println("")

    elementType.getEAllReferences.foreach {
      ref =>

        pr.println("\t\t\t\t\t\t(xmiElem \\ \"@" + ref.getName + "\").headOption match {")
        pr.println("\t\t\t\t\t\t\t\tcase Some(head) => {")
        pr.println("\t\t\t\t\t\t\t\t\t\thead.text.split(\" \").foreach {")
        pr.println("\t\t\t\t\t\t\t\t\t\t\t\txmiRef =>")
        pr.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + context + ".map.get(xmiRef) match {")
        var methName: String = ""
        if (ref.getUpperBound == 1) {
          methName = "set"
        } else {
          methName = "add"
        }
        methName += ref.getName.substring(0, 1).toUpperCase + ref.getName.substring(1)
        pr.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tcase Some(s: " + ref.getEReferenceType.getName + ") => modelElem." + methName + "(s)")
        pr.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tcase None => System.out.println(\"" + ref.getEReferenceType.getName + " not found in map ! xmiRef:\" + xmiRef)")
        pr.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t}")
        pr.println("\t\t\t\t\t\t\t\t\t\t\t\t}")
        pr.println("\t\t\t\t\t\t\t\t\t\t}")
        pr.println("\t\t\t\t\t\t\t\tcase None => //No subtype for this library")
        pr.println("\t\t\t\t\t\t}")

    }

    var methName: String = ""
    parentType.getEAllReferences.find(ref => ref.getName.equals(elementNameInParent)) match {
      case Some(elem) => {
        if (elem.getUpperBound == 1) {
          methName += "set"
        } else {
          methName += "add"
        }
        methName += elementNameInParent.substring(0, 1).toUpperCase + elementNameInParent.substring(1)
      }
      case None => {
        throw new Exception("Element " + elementNameInParent + " not found in parent. Can not set/add to owner.")
        null
      }
    }

    pr.println("")
    pr.println("\t\t\t\t\t\towner." + methName + "(modelElem)")
    pr.println("\t\t\t\t\t\ti += 1")
    pr.println("\t\t\t\t}")
    pr.println("\t\t}")


  }


}