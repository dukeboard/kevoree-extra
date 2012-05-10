package org.daum.hajdbc;

import org.apache.derby.drda.NetworkServerControl;

import java.io.PrintWriter;
import java.net.InetAddress;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 09/05/12
 * Time: 18:34
 */
public class Tester3 {

    public static void  main(String []argv){


        try {
            NetworkServerControl   serverControl = new NetworkServerControl(InetAddress.getByName("localhost"),1527);
            

            serverControl.start(new PrintWriter(System.out, true));
            Thread.sleep(100000);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
