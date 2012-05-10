package org.kevoree.extra.voldemort;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;
import voldemort.client.StoreClient;
import voldemort.client.StoreClientFactory;
import voldemort.versioning.Versioned;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 02/05/12
 * Time: 14:54
 */
public class Tester {


    public static void  main(String[] args){



        String bootstrapUrl = "tcp://localhost:6666";
        StoreClientFactory factory = new SocketStoreClientFactory(new ClientConfig().setBootstrapUrls(bootstrapUrl));


      // create a client that executes operations on a single store
            StoreClient store = factory.getStoreClient("kevoree");


        store.put("pompier1","capteurs");
        
        Versioned<String> data =        store.get("pompier1");
        
       System.out.println(data.getValue()+" "+data.getVersion());
        store.delete("pompier1",data.getVersion()) ;






    }
}
