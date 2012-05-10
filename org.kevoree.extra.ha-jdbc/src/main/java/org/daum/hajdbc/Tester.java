package org.daum.hajdbc;

import net.sf.hajdbc.DatabaseCluster;
import net.sf.hajdbc.SimpleDatabaseClusterConfigurationFactory;
import net.sf.hajdbc.cache.DatabaseMetaDataCacheFactoryEnum;
import net.sf.hajdbc.dialect.DialectFactoryEnum;
import net.sf.hajdbc.distributed.jgroups.DefaultChannelProvider;
import net.sf.hajdbc.sql.DataSource;
import net.sf.hajdbc.sql.DataSourceDatabase;
import net.sf.hajdbc.sql.DataSourceDatabaseClusterConfiguration;
import net.sf.hajdbc.sql.SQLProxy;
import net.sf.hajdbc.state.simple.SimpleStateManagerFactory;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 07/05/12
 * Time: 11:38
 */
public class Tester {


    private javax.sql.DataSource ds;
    private javax.sql.DataSource ds1;
    private javax.sql.DataSource ds2;


    public  Tester() throws SQLException {



        DataSourceDatabase db1 = new DataSourceDatabase();
        db1.setId("node0");
        db1.setName(UrlDataSource.class.getName());
        db1.setProperty("url", "jdbc:derby://127.0.0.1:1527/node0");

        db1.setUser("sa");
        db1.setPassword("sa");

        DataSourceDatabase db2 = new DataSourceDatabase();
        db2.setId("node1");
        db2.setName(UrlDataSource.class.getName());
        db2.setProperty("url", "jdbc:derby://127.0.0.1:1528/node1");
        db2.setUser("sa");
        db2.setPassword("sa");

        List<DataSourceDatabase> dataSourceDatabases = new ArrayList<DataSourceDatabase>();


        dataSourceDatabases.add(db1);
        dataSourceDatabases.add(db2);

        DataSourceDatabaseClusterConfiguration config = new DataSourceDatabaseClusterConfiguration();

        config.setDatabases(dataSourceDatabases);

        config.setDialectFactory(DialectFactoryEnum.DERBY);
        config.setDatabaseMetaDataCacheFactory(DatabaseMetaDataCacheFactoryEnum.SHARED_EAGER);

        DefaultChannelProvider channel = new DefaultChannelProvider();


        config.setDispatcherFactory(channel);
        SimpleStateManagerFactory state = new SimpleStateManagerFactory();
        state.start();

        config.setStateManagerFactory(state);

        DataSource ds = new DataSource();

        ds.setCluster("cluster");

        ds.setConfigurationFactory(new SimpleDatabaseClusterConfigurationFactory<javax.sql.DataSource, DataSourceDatabase>(config));

        this.ds = ds;




        SQLProxy<javax.sql.DataSource, DataSourceDatabase, javax.sql.DataSource, SQLException> proxy =            (SQLProxy<javax.sql.DataSource, DataSourceDatabase, javax.sql.DataSource, SQLException>) Proxy.getInvocationHandler(ds.getProxy());


        this.ds1 = proxy.getObject(db1);
        this.ds2 = proxy.getObject(db2);




    }

    public static void  main(String []argv){


        System.out.println(DatabaseCluster.class.getPackage().getName());
        try {
            Tester  t = new Tester();



            /*
 String createSQL = "CREATE TABLE test (id INTEGER NOT NULL, name VARCHAR(10) NOT NULL, PRIMARY KEY (id))";

 Connection c1 = t.ds.getConnection("daum", "daum");
 Statement s1 = c1.createStatement();
 s1.execute(createSQL);
 s1.close();

//   		Connection c2 = t.ds2.getConnection("sa", "sa");
 //		Statement s2 = c2.createStatement();
 //	s2.execute(createSQL);
 //s2.close();


 Connection c = t.ds.getConnection("daum", "daum");
 c.setAutoCommit(false);
 PreparedStatement ps = c.prepareStatement("INSERT INTO test (id, name) VALUES (?, ?)");
 ps.setInt(1,new Random().nextInt(200));
 ps.setString(2, "1");
 ps.addBatch();
 ps.setInt(1, new Random().nextInt(200));
 ps.setString(2, "2");
 ps.addBatch();
 ps.executeBatch();
 ps.close();
 c.commit();

            */
            /*
            String selectSQL = "SELECT id, name FROM test";

            s1 = c1.createStatement();
            ResultSet rs1 = s1.executeQuery(selectSQL);
            while(rs1.next())
            {
                System.out.println(rs1.getInt(1)+" "+rs1.getString(2));
            }
            rs1.close();
            s1.close();

            s2 = c2.createStatement();
            */

            /*

           ResultSet rs2 = s2.executeQuery(selectSQL);
           while(rs2.next())
           {
               System.out.println(rs2.getInt(1)+" "+rs2.getString(2));
           }
                         s2.close();          .
            c.close();
            */








            System.exit(0);


        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}
