package org.kevoree.tools.ecore.gencode.cloner

import scala.collection.JavaConversions._
import java.io.{File, FileOutputStream, PrintWriter}
import org.eclipse.emf.ecore.{EPackage, EClass}
import org.kevoree.tools.ecore.gencode.ProcessorHelper

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 02/10/11
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */

class ClonerGenerator(location: String, rootPackage: String, rootXmiPackage: EPackage) {


  def generateCloner() {
    ProcessorHelper.lookForRootElement(rootXmiPackage) match {
      case cls: EClass => {
        generateCloner(location + "/" + rootXmiPackage.getName, rootPackage + "." + rootXmiPackage.getName, rootXmiPackage.getName + ":" + cls.getName, cls, rootXmiPackage, true)
        generateDefaultCloner(location + "/" + rootXmiPackage.getName, rootPackage + "." + rootXmiPackage.getName, cls, rootXmiPackage)
      }
      case _@e => throw new UnsupportedOperationException("Root container not found. Returned:" + e)
    }
  }

  def generateDefaultCloner(genDir: String, packageName: String, root: EClass, rootXmiPackage: EPackage) {
    ProcessorHelper.checkOrCreateFolder(genDir + "/cloner")
    val pr = new PrintWriter(new FileOutputStream(new File(genDir + "/cloner/" + "ModelCloner.scala")))
    pr.println("package " + packageName + ".cloner")
    pr.println("class ModelCloner extends " + root.getName + "Cloner {")

    pr.println("def clone[A](o : A) : A = {")

    pr.println("o match {")
    pr.println("case o : " + packageName + "." + root.getName + " => {")
    pr.println("val context = get" + root.getName + "clonelazy(o)")
    pr.println(root.getName + "resolve(o,context).asInstanceOf[A]")
    pr.println("}")
    pr.println("case _ => null.asInstanceOf[A]")
    pr.println("}") //END MATCH
    pr.println("}") //END serialize method
    pr.println("}") //END TRAIT
    pr.flush()
    pr.close()
  }


  def generateCloner(genDir: String, packageName: String, refNameInParent: String, root: EClass, rootXmiPackage: EPackage, isRoot: Boolean = false): Unit = {
    ProcessorHelper.checkOrCreateFolder(genDir + "/cloner")
    //PROCESS SELF
    val pr = new PrintWriter(new FileOutputStream(new File(genDir + "/cloner/" + root.getName + "Cloner.scala")))
    pr.println("package " + packageName + ".cloner")
    generateToHashMethod(root, pr, rootXmiPackage, isRoot)
    pr.flush()
    pr.close()

    //PROCESS SUB
    root.getEAllContainments.foreach {
      sub =>
        val subpr = new PrintWriter(new FileOutputStream(new File(genDir + "/cloner/" + sub.getEReferenceType.getName + "Cloner.scala")))
        subpr.println("package " + packageName + ".cloner")
        generateToHashMethod(sub.getEReferenceType, subpr, rootXmiPackage)
        subpr.flush()
        subpr.close()

        //¨PROCESS ALL SUB TYPE
        ProcessorHelper.getConcreteSubTypes(sub.getEReferenceType).foreach {
          subsubType =>
            generateCloner(genDir, packageName, sub.getName, subsubType, rootXmiPackage)
        }
        generateCloner(genDir, packageName, sub.getName, sub.getEReferenceType, rootXmiPackage)

    }
  }

  private def getGetter(name: String): String = {
    "get" + name.charAt(0).toUpper + name.substring(1)
  }

  private def getSetter(name: String): String = {
    "set" + name.charAt(0).toUpper + name.substring(1)
  }


  private def generateToHashMethod(cls: EClass, buffer: PrintWriter, pack: EPackage, isRoot: Boolean = false) = {
    buffer.println("import org.kevoree._")
    buffer.println("trait " + cls.getName + "Cloner ")

    var subTraits = (cls.getEAllContainments).map(sub => sub.getEReferenceType.getName + "Cloner").toSet
    subTraits = (subTraits ++ ProcessorHelper.getConcreteSubTypes(cls).map(sub => sub.getName + "Cloner")).toSet

    if (subTraits.size >= 1) {
      buffer.print(subTraits.mkString(" extends ", " with ", " "))
    }

    buffer.println("{")

    //GENERATE GET Hash ADDR
    buffer.println("def get" + cls.getName + "clonelazy(selfObject : " + cls.getName + "): Map[Object,Object] = {")
    buffer.println("var subResult = Map[Object,Object]()")

    var formatedFactoryName: String = pack.getName.substring(0, 1).toUpperCase
    formatedFactoryName += pack.getName.substring(1)
    formatedFactoryName += "Factory"

    var formatedName: String = cls.getName.substring(0, 1).toUpperCase
    formatedName += cls.getName.substring(1)
    buffer.println("val selfObjectClone = " + formatedFactoryName + ".create" + formatedName)
    cls.getEAllAttributes /*.filter(eref => !cls.getEAllContainments.contains(eref))*/ .foreach {
      att =>
        buffer.println("selfObjectClone." + getSetter(att.getName) + "(selfObject." + getGetter(att.getName) + ")")
    }

    buffer.println("selfObject match {")
    ProcessorHelper.getConcreteSubTypes(cls).foreach { subType =>
    buffer.println("case o : "+subType.getName+" =>subResult = subResult ++ get" + subType.getName + "clonelazy(o)")
    }
    buffer.println("case _ => subResult += selfObject -> selfObjectClone")
    buffer.println("}")


    cls.getEAllContainments.foreach {
      contained =>
        contained.getUpperBound match {
          case 1 => {
            buffer.println("selfObject." + getGetter(contained.getName) + ".map{ sub =>")
            buffer.println("subResult = subResult ++ get" + contained.getEReferenceType.getName + "clonelazy(sub)")
            buffer.println("}")
          }
          case -1 => {
            buffer.println("selfObject." + getGetter(contained.getName) + ".foreach{ sub => ")
            buffer.println("subResult = subResult ++ get" + contained.getEReferenceType.getName + "clonelazy(sub)")
            buffer.println("}")
          }
        }
    }

    buffer.println("subResult") //result
    buffer.println("}") //END METHOD

    //GENERATE CLONE METHOD

    //CALL SUB TYPE OR PROCESS OBJECT
    buffer.println("def " + cls.getName + "resolve(selfObject : " + cls.getName + ", addrs : Map[Object,Object]) : "+cls.getName+" = {")

    buffer.println("selfObject match {")
    ProcessorHelper.getConcreteSubTypes(cls).foreach { subType =>
    buffer.println("case o : "+subType.getName+" =>" + subType.getName + "resolve(o,addrs)")
    }
    buffer.println("case _ => {")

    //GET CLONED OBJECT
    buffer.println("val clonedSelfObject = addrs.get(selfObject).get.asInstanceOf["+cls.getName+"]")
    //SET ALL REFERENCE
    cls.getEAllReferences.foreach {
      ref =>
        
        ref.getUpperBound match {
          case 1 => {
            ref.getLowerBound match {
              case 0 => {   // 0 to 1 relationship . Test Optional result
                buffer.println("selfObject." + getGetter(ref.getName) + ".map{sub =>")
                buffer.println("clonedSelfObject."+getSetter(ref.getName)+"(Some(addrs.get(sub).get.asInstanceOf["+ref.getEReferenceType.getName+"]))" )
                buffer.println("}")
              }
              case 1 => {   // 1 to 1 relationship
                buffer.println("clonedSelfObject."+getSetter(ref.getName)+"(addrs.get(selfObject."+getGetter(ref.getName)+").get.asInstanceOf["+ref.getEReferenceType.getName+"])" )
              }
            }
          }
          case _ => {
            buffer.println("selfObject." + getGetter(ref.getName) + ".foreach{sub =>")
            var formatedName: String = ref.getName.substring(0, 1).toUpperCase
            formatedName += ref.getName.substring(1)
            buffer.println("clonedSelfObject.add"+formatedName+"(addrs.get(sub).get.asInstanceOf["+ref.getEReferenceType.getName+"])" )
            buffer.println("}")
          }
        }
    }
    //RECUSIVE CALL ON ECONTAINEMENT
    cls.getEAllContainments.foreach {
      contained =>
        contained.getUpperBound match {
          case 1 => {
            buffer.println("selfObject." + getGetter(contained.getName) + ".map{ sub =>")
            buffer.println( contained.getEReferenceType.getName + "resolve(sub,addrs)")
            buffer.println("}")
          }
          case -1 => {
            buffer.println("selfObject." + getGetter(contained.getName) + ".foreach{ sub => ")
            buffer.println( contained.getEReferenceType.getName + "resolve(sub,addrs)")
            buffer.println("}")
          }
        }
    }

    buffer.println("clonedSelfObject") //RETURN CLONED OBJECT
    buffer.println("}}") // END DEFAULT CASE // END TOP MATCHER
    buffer.println("}")  //END METHOD

    buffer.println("}") //END TRAIT
  }


}