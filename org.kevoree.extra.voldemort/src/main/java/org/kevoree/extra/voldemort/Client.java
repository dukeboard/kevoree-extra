package org.kevoree.extra.voldemort;

import voldemort.cluster.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 17/04/12
 * Time: 13:45
 */
public class Client {

    public static void  main(String[] args){

        /*
        String bootstrapUrl = "tcp://localhost:6666";
        StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(bootstrapUrl));


        String bootstrapUrl2 = "tcp://localhost:6667";
        StoreClientFactory factory2 = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(bootstrapUrl2));


        String bootstrapUrl3 = "tcp://localhost:6668";
        StoreClientFactory factory3 = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(bootstrapUrl3));


        // create a client that executes operations on a single store
        StoreClient client = factory.getStoreClient("kevoree");
        StoreClient client2 = factory2.getStoreClient("kevoree");
        StoreClient client3 = factory3.getStoreClient("kevoree");





        System.out.println(client.get("id"));
        System.out.println(client2.get("id"));

        System.out.println(client3.get("id"));

        factory.close();

        factory2.close();

        factory3.close();


        //System.out.println(client.get("some_key"));

        */


        List<Node>  nodes = new ArrayList<Node>();

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




    }



}
