package org.kevoree.extra.model.loader

import java.io.File
import org.junit.Assert._
import org.junit.{BeforeClass, Test}
import org.kevoree.{ComponentType, ContainerRoot}

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
      val loader = new XmiLoaderPureScala();
      val localModel = loader.loadModel(new File(getClass.getResource("/defaultlibs.kev").toURI));
      localModel match {
        case Some(m) =>{
          model = m
        }
        case None => fail("Model not loaded!")
      }
    }
  }

class XmiLoaderTest {

  @Test
  def checkRepositories() {
    XmiLoaderTest.model.getRepositories match {
      case Some(l) => {
        assertTrue("Wrong number of repositories in model." + l.size, l.size == 6);
        l.foreach{elem =>
          assertNotNull("eContainer not set for Repository:" + elem.getName,elem.eContainer)
        }
      }
      case None => {
        fail("No repository in mmodel");
      }
    }
  }

  @Test
  def checkLibraries() {
    XmiLoaderTest.model.getLibraries match {
      case Some(l) => {
        assertTrue("Wrong number of libraries in model." + l.size, l.size == 9);
        l.foreach{lib =>
          assertFalse("Lib as no name.",lib.getName.equals(""))
          assertNotNull("eContainer not set for Library:" + lib.getName,lib.eContainer)
        }
      }
      case None => {
        fail("No library in mmodel");
      }
    }
  }

  @Test
  def checkDeployUnits() {
    XmiLoaderTest.model.getDeployUnits match {
      case Some(l) => {
        assertTrue("Wrong number of DeployUnits in model." + l.size, l.size == 20);
        l.foreach{ du =>
          assertNotNull("DeployUnit name is null", du.getName)
          assertNotNull("eContainer not set for DeployUnit:" + du.getName,du.eContainer)
         du.getName match {
           case "" => {
             assertFalse(du.getGroupName.equals(""))
             assertFalse(du.getUnitName.equals(""))
             assertFalse(du.getVersion.equals(""))
           }
           case _ => {
              assertFalse(du.getUrl.equals(""))
           }

         }
        }
      }
      case None => {
        fail("No DeployUnits in mmodel");
      }
    }
  }

  @Test
  def checkTypeDefinitions() {
    XmiLoaderTest.model.getTypeDefinitions match {
      case Some(l) => {
        assertTrue("Wrong number of TypeDefinitions in model." + l.size, l.size == 33);

        l.foreach{typeDef =>
          assertNotNull("eContainer not set for TypeDef:" + typeDef.getName,typeDef.eContainer)
          typeDef match {
            case ct : ComponentType => {
              ct.getProvided match {
                case Some(ports) => {
                  ports.foreach(port =>assertNotNull("eContainer not set for Port:" + port.getName + " in " + typeDef.getName,port.eContainer))
                }
                case None => //No port
              }

              if(ct.getName.equals("DigitalLight")) {
                assertTrue("Not enough providedPorts in " + ct.getName + " : " + ct.getProvided.get.size, ct.getProvided.get.size == 3)
              }
            }
            case _ => //No test for other types for the moment//TODO: ADD TESTS
          }

        }

        }
      case None => {
        fail("No TypeDefinition in mmodel");
      }
    }
  }


}