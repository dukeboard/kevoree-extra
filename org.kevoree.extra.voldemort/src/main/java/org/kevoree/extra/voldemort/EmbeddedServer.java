package org.kevoree.extra.voldemort;

import org.apache.commons.io.FileUtils;
import voldemort.cluster.Cluster;
import voldemort.cluster.Node;
import voldemort.server.VoldemortConfig;
import voldemort.server.VoldemortServer;
import voldemort.store.bdb.BdbStorageConfiguration;
import voldemort.utils.Props;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 17/04/12
 * Time: 14:23
 */
public class EmbeddedServer implements  Runnable{


    private Cluster cluster;
    private  List<Node> nodes;
    private  VoldemortConfig config = null;
    private  BdbStorageConfiguration bdbStorage;
    private Thread standalone;
    private VoldemortServer server;

    private VoldemortConfig createServerConfig(int nodeId) throws IOException
    {
        Props props = new Props();
        props.put("node.id", nodeId);
        props.put("voldemort.home", KUtils.createTempDir() + "/node-" + nodeId);

        props.put("bdb.cache.size", 1 * 1024 * 1024);
        props.put("jmx.enable", "false");
        VoldemortConfig config = new VoldemortConfig(props);


        // clean and reinit metadata dir.
        File tempDir = new File(config.getMetadataDirectory());
        tempDir.mkdirs();

        File tempDir2 = new File(config.getDataDirectory());
        tempDir2.mkdirs();

        //    FileUtils.copyFileToDirectory(new File(this.getClass().getClassLoader().getResource("config/one-node-cluster.xml").getPath()), tempDir);
        FileUtils.copyFileToDirectory(new File(this.getClass().getClassLoader().getResource("config/stores.xml").getPath()), tempDir);
        return config;
    }




    public  EmbeddedServer(int nodeid){

        try {

            nodes = new ArrayList<Node>();

            List<Integer> partitions =  new ArrayList<Integer>();
            partitions.add(0);
            partitions.add(1);
            partitions.add(2);
            partitions.add(3);
            partitions.add(4);

            List<Integer> partitions1 =  new ArrayList<Integer>();
            partitions1.add(5);
            partitions1.add(6);
            partitions1.add(7);


            List<Integer> partitions2 =  new ArrayList<Integer>();
            partitions2.add(8);
            partitions2.add(9);



            Node node0 = new Node(0,"localhost",8081,6666,9000,partitions);
            Node node1 = new Node(1,"localhost",8082,6667,9001,partitions1);
            Node node2 = new Node(2,"localhost",8083,6668,9002,partitions2);

            nodes.add(node0);
            nodes.add(node1);
            nodes.add(node2);


            cluster = new Cluster("Kcluster",nodes);


            config = createServerConfig(nodeid);
            standalone = new Thread(this);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void start(){
        standalone.start();
    }


    public void stop(){
        server.stop();

        standalone.interrupt();
    }


    @Override
    public void run() {
        server = new VoldemortServer(config,cluster);
        server.start();

    }

    public static void  main(String[] args) throws InterruptedException {

        EmbeddedServer node0 = new EmbeddedServer(0);
        node0.start();

        EmbeddedServer node1= new EmbeddedServer(1);
        node1.start();

        EmbeddedServer node2= new EmbeddedServer(2);
        node2.start();

        Thread.sleep(10000000);

    }

}


