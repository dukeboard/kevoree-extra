package org.kevoree.extra.ecore.loader.test

import java.io.File
import org.junit.{BeforeClass, Test}
import org.kevoree.{ ComponentType, ContainerRoot}
import org.junit.Assert._
import org.kevoree.ContainerRootLoader
import org.kevoree.serializer.ModelSerializer

/**
 * Created by IntelliJ IDEA.
 * User: Gregory NAIN
 * Date: 24/09/11
 * Time: 13:39
 */

object XmiLoaderTest {
  var model: ContainerRoot = null

  @BeforeClass
  def loadXmi() {

    val localModel = ContainerRootLoader.loadModel(new File(getClass.getResource("/defaultlibs.kev").toURI));
    localModel match {
      case Some(m) => {
        model = m
      }
      case None => fail("Model not loaded!")
    }
  }
}

class XmiLoaderTest {

     @Test
  def testSave() {
        val serializer = new ModelSerializer

       println(serializer.serialize(XmiLoaderTest.model))
     }


  /*
  @Test
  def checkRepositories() {
    val repList = XmiLoaderTest.model.getRepositories
    assertTrue("Wrong number of repositories in model." + repList.size, repList.size == 6);
    repList.foreach {
      elem =>
        assertNotNull("eContainer not set for Repository:" + elem.getName, elem.eContainer)
    }

  }

  @Test
  def checkLibraries() {
    val libList = XmiLoaderTest.model.getLibraries
    assertTrue("Wrong number of libraries in model:" + libList.size, libList.size == 9);
    libList.foreach {
      lib =>
        System.out.println("Lib[name:" + lib.getName + ", sub:" + lib.getSubTypes.mkString("[", ", ", "]") + "]")
      // assertFalse("Lib has no name.",lib.getName.equals(""))
      // assertNotNull("eContainer not set for Library:" + lib.getName,lib.eContainer)
    }

  }

  @Test
  def checkDeployUnits() {
    val duList = XmiLoaderTest.model.getDeployUnits
    assertTrue("Wrong number of DeployUnits in model." + duList.size, duList.size == 20);
    duList.foreach {
      du =>
        assertNotNull("DeployUnit name is null", du.getName)
        assertNotNull("eContainer not set for DeployUnit:" + du.getName, du.eContainer)
        System.out.println("DeployUnit[name:" + du.getName
          + ", groupName:" + du.getGroupName
          + ", unitName:" + du.getUnitName
          + ", version:" + du.getVersion
          + ", url:" + du.getUrl
          + "]") /*
         du.getName match {
           case "" => {
             assertFalse("DeployUnit.getName:"+du.getName+" du.groupName:" +du.getGroupName,du.getGroupName.equals(""))
             assertFalse("DeployUnit.getName:"+du.getName+" du.unitName:"+du.getUnitName,du.getUnitName.equals(""))
             assertFalse("DeployUnit.getName:"+du.getName+" du.version:"+du.getVersion,du.getVersion.equals(""))
           }
           case _ => {
              assertFalse("DeployUnit.getName:"+du.getName+" du.url:"+du.getUrl,du.getUrl.equals(""))
           }
         }
         */
    }
  }


  @Test
  def checkTypeDefinitions() {
    val tdList = XmiLoaderTest.model.getTypeDefinitions
    assertTrue("Wrong number of TypeDefinitions in model." + tdList.size, tdList.size == 33);

    tdList.foreach {
      typeDef =>
        assertNotNull("eContainer not set for TypeDef:" + typeDef.getName, typeDef.eContainer)
        typeDef match {
          case ct: ComponentType => {
            val provPortList = ct.getProvided

            provPortList.foreach(port => assertNotNull("eContainer not set for Port:" + port.getName + " in " + typeDef.getName, port.eContainer))


            if (ct.getName.equals("DigitalLight")) {
              assertTrue("No provided port in " + ct.getName, ct.getProvided != null)
              assertTrue("Not enough providedPorts in " + ct.getName + " : " + ct.getProvided.size, ct.getProvided.size == 3)
            }
          }
          case _ => //No test for other types for the moment//TODO: ADD TESTS
        }

    }
  }    */

}