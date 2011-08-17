package org.kevoree.extra.osgi.rxtx

import actors.{TIMEOUT, DaemonActor}
import gnu.io._

/**
 * User: ffouquet
 * Date: 06/06/11
 * Time: 15:27
 */

class TwoWayActors(portName: String) extends SerialPortEventListener {

  var serialPort: SerialPort = null
  var portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
  if (portIdentifier.isCurrentlyOwned) {
    System.out.println("Error: Port is currently in use");
  } else {
    val commPort = portIdentifier.open(portName, 2000);
    if (commPort.isInstanceOf[SerialPort]) {
      serialPort = commPort.asInstanceOf[SerialPort];
      //serialPort.setDTR(false)
      serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
      serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
      //serialPort.disableReceiveTimeout();
      //serialPort.enableReceiveThreshold(1);
      serialPort.addEventListener(this)
      serialPort.notifyOnDataAvailable(true);

    } else {
      System.out.println("Error: Only serial ports are handled by this example.");
    }
  }
  Thread.sleep(2000)


  def sendAndWait(msg: String, waitMsg: String, timeout: Long): java.lang.Boolean = {
    (replyActor !?(timeout, Tuple3(msg, waitMsg, timeout))) match {
      case Some(e) => true
      case None => println("TimeOut"); false
    }
  }

  def send(msg: String) {
    replyActor ! msg
  }


  case class CLOSEPORT()

  case class CONTENTREC(lastChar: Char)

  def killConnection() {
    readerActor ! CLOSEPORT()
    replyActor ! CLOSEPORT()
    if (serialPort != null) {
      serialPort.close()
    }
  }

  var recString = ""
  var readerActor = new DaemonActor {
    def act() {
      loop {
        react {
          case CLOSEPORT() => exit()
          case _ => {
            if (serialPort.getInputStream.available() > 0) {
              recString = recString + (serialPort.getInputStream.read().toChar);
              replyActor ! CONTENTREC(recString.last)
            }
          }
        }
      }

    }

  }.start()
  var replyActor = new DaemonActor {
    def act() {
      loop {
        react {
          case CONTENTREC(last) => {
            if (last == '\n' && recString != "") {
              KevoreeSharedCom.notifyObservers(portName, recString)
              recString = ""
            }
          }
          case CLOSEPORT() => exit()
          case msg: Tuple3[String, String, Long] => {
            val originalSender = sender
            serialPort.getOutputStream.write(msg._1.getBytes)
            reactWithin(msg._3) {
              case CONTENTREC(last) if (recString.contains(msg._2)) => {
                //if (recString.contains(msg._2)) {
                //  println("YZPEE")
                  originalSender ! true
                //}

                if (last == '\n' && recString != "") {
                  KevoreeSharedCom.notifyObservers(portName, recString)
                  recString = ""
                }
              }
              case TIMEOUT => println("TimeOut internal") //LOST NEXT MESSAGE
              case CLOSEPORT() => exit()
            }
          }
          case simpleMsg: String => {
            serialPort.getOutputStream.write(simpleMsg.getBytes)
          }

        }
      }
    }
  }.start()


  def serialEvent(p1: SerialPortEvent) {
    p1.getEventType match {
      case SerialPortEvent.DATA_AVAILABLE => {
        readerActor ! "trigger"
      }
    }

  }
}