package org.kevoree.extra.voldemort;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;
import voldemort.cluster.Node;

import java.util.List;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 17/04/12
 * Time: 13:45
 */
public class Client {

    public static void  main(String[] args){

        /*              */
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

            /*
        AdminClientConfig config =  new AdminClientConfig();


        AdminClient t2 = new AdminClient(bootstrapUrl,config);
        Cluster current = t2.getAdminClientCluster();
        System.out.println("Number of node "+current.getNodes().size());


        Node newNode = new Node(11,"localhost",8081,6666,9000, new ArrayList<Integer>());
        List<Node> nodes = Lists.newArrayList();



        Cluster newCluster = new Cluster(current.getName(),nodes,Lists.newArrayList(current.getZones()));



        Cluster generatedCluster = RebalanceUtils.getClusterWithNewNodes(current, newCluster);




        RebalanceClientConfig config2 = new RebalanceClientConfig();
        config2.setMaxParallelRebalancing(1);
        config2.setDeleteAfterRebalancingEnabled(false);
        config2.setEnableShowPlan(false);
        config2.setMaxTriesRebalancing(100);
        config2.setRebalancingClientTimeoutSeconds(500);
        config2.setPrimaryPartitionBatchSize(1000);
        config2.setStealerBasedRebalancing(false);


        RebalanceController   rebalanceController = new RebalanceController(bootstrapUrl, config2);

        rebalanceController.rebalance(generatedCluster);
        rebalanceController.stop();

        AdminClient t3 = new AdminClient(bootstrapUrl,config);
        Cluster current3 = t2.getAdminClientCluster();
        System.out.println("Number of node "+current3.getNodes().size());

           */

        client2.put("pompier1", "jed2");

        List<Node> t =       client2.getResponsibleNodes("id2");



        System.out.println(t);




        factory.close();

        factory2.close();

        factory3.close();


        //System.out.println(client.get("some_key"));









    }



}
