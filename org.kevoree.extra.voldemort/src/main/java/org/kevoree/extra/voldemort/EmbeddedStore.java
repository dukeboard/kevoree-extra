package org.kevoree.extra.voldemort;

import org.apache.commons.io.FileDeleteStrategy;
import voldemort.server.VoldemortConfig;
import voldemort.store.bdb.BdbStorageConfiguration;
import voldemort.utils.Props;

import java.io.File;
import java.io.IOException;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 17/04/12
 * Time: 13:45
 */
public class EmbeddedStore {


    private File bdbMasterDir;

    private BdbStorageConfiguration bdbStorage;
    public EmbeddedStore(){



        bdbMasterDir = KUtils.createTempDir();
        try {
            FileDeleteStrategy.FORCE.delete(bdbMasterDir);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        Props props = new Props();
        props.put("node.id", 1);
        props.put("voldemort.home", "ressources/config");
        VoldemortConfig voldemortConfig = new VoldemortConfig(props);
        voldemortConfig.setBdbCacheSize(1 * 1024 * 1024);
        voldemortConfig.setBdbDataDirectory(bdbMasterDir.toURI().getPath());
        voldemortConfig.setBdbOneEnvPerStore(false);

        /*
        bdbStorage = new BdbStorageConfiguration(voldemortConfig);


        BdbStorageEngine storeA = (BdbStorageEngine) bdbStorage.getStore("storeA");

        storeA.put(KUtils.toByteArray("testKey1"),
                   new Versioned<byte[]>("value".getBytes()),
                   null);

        storeA.put(KUtils.toByteArray("testKey2"),
                   new Versioned<byte[]>("value".getBytes()),
                   null);



        storeA.close();

        */





    }


    public static void  main(String[] args){
        EmbeddedStore t = new EmbeddedStore();
    }
}
