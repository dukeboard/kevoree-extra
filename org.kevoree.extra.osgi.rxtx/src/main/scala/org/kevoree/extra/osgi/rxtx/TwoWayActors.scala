package org.kevoree.extra.osgi.rxtx

import actors.{TIMEOUT, DaemonActor}
import gnu.io._
import java.lang.Thread

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
      serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
      serialPort.disableReceiveTimeout();
      serialPort.disableReceiveThreshold();
      serialPort.disableReceiveFraming();
      serialPort.enableReceiveThreshold(1);
      serialPort.addEventListener(this)
      serialPort.notifyOnDataAvailable(true);


    } else {
      System.out.println("Error: Only serial ports are handled by this example.");
    }
  }
//  var continueRead = true
  //val readThread = new Thread(this)
  //readThread.setDaemon(true)
 // readThread.start()




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

  case class CONTENTREC(content : String,lastChar: Char)

  def killConnection() {
    replyActor ! CLOSEPORT()
    if (serialPort != null) {
      serialPort.close()
    }
  }

  var replyActor = new DaemonActor {
    def act() {
      loop {
        react {
          case CONTENTREC(recString,last) => {
            if (last == '\n' && recString != "") {
              KevoreeSharedCom.notifyObservers(portName, recString)
            }
          }
          case CLOSEPORT() => exit()
          case msg: Tuple3[String, String, Long] => {
            val originalSender = sender
            serialPort.getOutputStream.write(msg._1.getBytes)
            reactWithin(msg._3) {
              case CONTENTREC(recString,last) if (recString.contains(msg._2)) => {
                //if (recString.contains(msg._2)) {
                //  println("YZPEE")
                originalSender ! true
                //}

                if (last == '\n' && recString != "") {
                  KevoreeSharedCom.notifyObservers(portName, recString)
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
      val buffer = new StringBuilder(1024)
      try {
        var data : Int = 0
        val input = serialPort.getInputStream
        data = input.read()
        while(data != '\n'){
          buffer += data.toChar
          data = input.read()
        }
        replyActor ! CONTENTREC(buffer.toString(),data.toChar)
      } catch {
        case _ @ e => //IGNORE
      }
    }
  }
}


}