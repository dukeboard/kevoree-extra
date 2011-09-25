package org.kevoree.tools.ecore.gencode.loader

//EClass, EClassifier,

import scala.collection.JavaConversions._
import org.eclipse.emf.ecore.{EClassifier, EClass, EPackage}
import collection.mutable.Buffer

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 18:09
 */

object LoaderGenerator {
  var rootXmiPackage : EPackage = null

  def getConcreteSubTypes(iface : EClass) : List[EClass] = {
    var res = List[EClass]()
    rootXmiPackage.getEClassifiers.filter(cl => cl.isInstanceOf[EClass]).foreach{cls=>
      if(!cls.asInstanceOf[EClass].isInterface
        && !cls.asInstanceOf[EClass].isAbstract
        && cls.asInstanceOf[EClass].getEAllSuperTypes.contains(iface)) {
        res = res ++ List(cls.asInstanceOf[EClass])
      }
    }
    res
  }
}

class LoaderGenerator(location: String, rootPackage: String, rootXmiPackage: EPackage) {

  LoaderGenerator.rootXmiPackage = rootXmiPackage

  def generateLoader() {
    lookForRootElement() match {
      case cls : EClass => {
        val el = new RootLoader(location+ "/"+ rootXmiPackage.getName, rootPackage + "."+ rootXmiPackage.getName, rootXmiPackage.getName+ ":" + cls.getName, cls,rootXmiPackage)
        el.generateLoader()
      }
      case _@e => throw new UnsupportedOperationException("Root container not found. Returned:" + e)
    }

    /*
      val elemGen = new ElementLoader()
      elemGen.generateLoader(elementNameInParent: String, elementType: EClass, parentType: EClass)
      */

  }


  private def lookForRootElement() : EClassifier = {

    var referencedElements: List[String] = List[String]()

    rootXmiPackage.getEClassifiers.foreach {
      classifier => classifier match {
        case cls: EClass => {
          //System.out.println("Class::" + cls.getName)
          cls.getEAllContainments.foreach {
            reference =>
              if (!referencedElements.contains(reference.getEReferenceType.getName)) {
                referencedElements = referencedElements ++ List(reference.getEReferenceType.getName)
                reference.getEReferenceType.getEAllSuperTypes.foreach{st =>
                  if (!referencedElements.contains(st.getName)) {
                    referencedElements = referencedElements ++ List(st.getName)
                  }
                }
              }
              //System.out.println("\t\tReference::[name:" + reference.getName + ", type:" + reference.getEReferenceType.getName + ", isContainement:" + reference.isContainment + ", isContainer:" + reference.isContainer + "]")
          }
        }
        case _@e => throw new UnsupportedOperationException(e.getClass.getName + " did not match anything while looking for root element.")
      }
    }

    //System.out.println("References:" + referencedElements.mkString(","))

    rootXmiPackage.getEClassifiers.filter {
      classif =>
        classif match {
          case cls: EClass => {

            if(cls.getEAllContainments.size() == 0) {
              false
            } else if (referencedElements.contains(cls.getName)) {
              false
            } else {
             // System.out.println(cls.getEAllSuperTypes.mkString(cls.getName +" supertypes [\n\t\t",",\n\t\t","]"))
              cls.getEAllSuperTypes.find{st =>
                val mat = referencedElements.contains(st.getName)
               // System.out.println(st.getName + " Match:: " + mat)
                mat
              } match {
                case Some(s) => false
                case None => true
              }
            }
          }
          case _ => false
        }
    } match {
      case b: Buffer[EClassifier] => {
        if (b.size != 1) {
          b.foreach(classifier => System.out.println("Possible root:" + classifier.getName))
          null
        } else {
          //System.out.println("RootElement:" + b.get(0).getName)
          b.get(0)
        }
      }
      case _@e => System.out.println("Root element not found. Returned:" + e.getClass);null
    }


  }
}