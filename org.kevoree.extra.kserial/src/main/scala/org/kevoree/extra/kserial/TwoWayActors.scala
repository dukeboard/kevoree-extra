package org.kevoree.extra.kserial

import SerialPort.{SerialPort => KSerialPort}
import SerialPort.{SerialPortDisconnectionEvent, SerialPortEvent, SerialPortEventListener}
import actors.{Actor, TIMEOUT, DaemonActor}

/**
 * User: ffouquet
 * Date: 06/06/11
 * Time: 15:27
 */

class TwoWayActors(portName: String) extends SerialPortEventListener {

  var replyActor = new DaemonActor {
    def act() {
      loop {
        react {
          case CONTENTREC(recString) => {
            KevoreeSharedCom.notifyObservers(portName, recString)
          }
          case CLOSEPORT() => exit()
          case msg: Tuple3[String, String, Long] => {
            val originalSender = sender
            if (serialPort != null) {
              try {
                serialPort.write(msg._1.getBytes)
                reactWithin(msg._3) {
                  case CONTENTREC(recString) if (recString.contains(msg._2)) => {
                    originalSender ! true
                    KevoreeSharedCom.notifyObservers(portName, recString)
                  }
                  case TIMEOUT => println("TimeOut internal") //LOST NEXT MESSAGE
                  case CLOSEPORT() => exit()
                }
              } catch {
                case _ => {originalSender ! false}
              }
            }
          }
          case simpleMsg: String => {
            if (serialPort != null) {
              serialPort.write(simpleMsg.getBytes)
            }
          }
        }
      }
    }
  }.start()

  var serialPort: KSerialPort = null
  serialPort = new KSerialPort(portName, 115200)
  serialPort.open()
  serialPort.addEventListener(this)

  def sendAndWait(msg: String, waitMsg: String, timeout: Long): java.lang.Boolean = {
    (replyActor !?(timeout, Tuple3(msg, waitMsg, timeout))) match {
      case Some(e) => true
      case None => println("timeout"); false
    }
  }

  def send(msg: String) {
    replyActor ! msg
  }

  case class CLOSEPORT()
  case class CONTENTREC(content: String)

  var closed = false

  def killConnection() {
    replyActor ! CLOSEPORT()
    if (serialPort != null) {
      serialPort.close()
    }
    closed = true
  }

  def incomingDataEvent(evt: SerialPortEvent) {
    replyActor ! CONTENTREC(new String(evt.read()))
  }



  def disconnectionEvent(evt: SerialPortDisconnectionEvent) {
    closed = true
    val selfListener : SerialPortEventListener = this
    new Actor(){
      def act() {
        loopWhile(closed){
          val result = serialPort.autoReconnect(1,selfListener)
          closed = !result
        }
      }
    }.start()
  }
}