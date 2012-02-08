package org.kevoree.extra.kserial

/**
 * Created by IntelliJ IDEA.
 * User: duke
 * Date: 07/02/12
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */

object Tester extends App {
  KevoreeSharedCom.addObserver("/dev/tty.usbmodem621", new ContentListener {
    def recContent(content: String) {
      println("Rec=" + content)
    }
  })
  Thread.sleep(2000)
  //KevoreeSharedCom.send("/dev/tty.usbmodem621", "1")
 // KevoreeSharedCom.send("/dev/tty.usbmodem621", "1")
 // KevoreeSharedCom.send("/dev/tty.usbmodem621", "1")
 // KevoreeSharedCom.send("/dev/tty.usbmodem621", "1")

  
  for(i <- 0 until 10){
    KevoreeSharedCom.sendSynch("/dev/tty.usbmodem621", "1","J'ai recu : 10",1000)
    Thread.sleep(4000)
  }

}
