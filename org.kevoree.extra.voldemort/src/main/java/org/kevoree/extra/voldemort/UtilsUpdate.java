package org.kevoree.extra.voldemort;

import com.google.common.collect.Lists;
import voldemort.client.protocol.admin.AdminClient;
import voldemort.client.protocol.admin.AdminClientConfig;
import voldemort.client.rebalance.RebalanceClientConfig;
import voldemort.client.rebalance.RebalanceController;
import voldemort.cluster.Cluster;
import voldemort.cluster.Node;
import voldemort.utils.RebalanceUtils;

import java.util.List;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 02/05/12
 * Time: 16:19
 */
public class UtilsUpdate {

    public  static void applyUpdateNode(Node currentNode,List<Node> nodes)
    {
        AdminClientConfig config =  new AdminClientConfig();
        String bootstrapUrl = "tcp://" + currentNode.getHost() + ":" + currentNode.getSocketPort();

        AdminClient t2 = new AdminClient(bootstrapUrl,config);
        Cluster current = t2.getAdminClientCluster();
        Cluster newCluster = new Cluster(current.getName(),nodes, Lists.newArrayList(current.getZones()));
        Cluster generatedCluster = RebalanceUtils.getClusterWithNewNodes(current, newCluster);
        RebalanceClientConfig config2 = new RebalanceClientConfig();
        config2.setMaxParallelRebalancing(1);
        config2.setDeleteAfterRebalancingEnabled(false);
        config2.setEnableShowPlan(false);
        config2.setMaxTriesRebalancing(100);
        config2.setRebalancingClientTimeoutSeconds(500);
        config2.setPrimaryPartitionBatchSize(1000);
        config2.setStealerBasedRebalancing(false);
        RebalanceController rebalanceController = new RebalanceController(bootstrapUrl, config2);
        rebalanceController.rebalance(generatedCluster);
        rebalanceController.stop();
    }


}
