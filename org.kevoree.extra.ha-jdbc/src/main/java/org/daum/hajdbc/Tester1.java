package org.daum.hajdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 07/05/12
 * Time: 14:13
 */
public class Tester1 {



    public static void  main(String []argv){


        Connection conn1 = null;
        Connection conn2 = null;
        Statement stmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();


            // création d'une connexion

            conn1 =   DriverManager.getConnection("jdbc:derby://127.0.0.1:1528/node1", "daum", "daum");

            stmt = conn1.createStatement();

        } catch (Exception e) {
            System.out.println("init");
        }

        try {
            conn2 =   DriverManager.getConnection("jdbc:derby://127.0.0.1:1527/node0", "daum", "daum");
        } catch (Exception e) {
            System.out.println("init");
        }


        int i=0;

        String selectSQL = "SELECT id, name FROM test";
        try {

            ResultSet rs1 = stmt.executeQuery(selectSQL);
            while(rs1.next())
            {
                //   System.out.println("node 0 ->"+rs1.getInt(1)+" "+rs1.getString(2));
                i++;
            }

            System.out.println("Node 0->"+i);

        } catch (Exception e) {
            System.out.println("Fail node 0");
        }

        try {
            stmt = conn2.createStatement();

            i=0;

            ResultSet rs2 = stmt.executeQuery(selectSQL);
            while(rs2.next())
            {
                //  System.out.println("node 1 ->"+rs2.getInt(1)+" "+rs2.getString(2));
                i++;
            }

            System.out.println("Node 1->"+i);

        } catch (Exception e) {
            System.out.println("Fail node 1");
        }





    }
}
