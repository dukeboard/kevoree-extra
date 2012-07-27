package org.kevoree.fota;


import org.kevoree.fota.api.FotaEventListener;
import org.kevoree.fota.events.FotaEvent;
import org.kevoree.fota.utils.Board;
import org.kevoree.kcl.KevoreeJarClassLoader;

public class Test {

    /**
     * @param args
     * @throws Exception
     */


    public static void main(String[] args) throws Exception {


        try
        {
            Fota fota = new Fota("*", Board.ATMEGA328);

            fota.upload("/tmp/arduinoGeneratednode0/target/uno/arduinoGeneratednode0.hex");

            fota.addEventListener(new FotaEventListener()
            {
                @Override
                public void progressEvent(FotaEvent evt) {
                    System.out.println(" Uploaded " + evt.getSize_uploaded()+"/"+evt.getProgram_size() + " octets");
                }

                @Override
                public void completedEvent(FotaEvent evt) {
                    System.out.println("Transmission completed successfully <" + evt.getProgram_size() + " octets "+evt.getDuree()+" secondes >");

                }
            });


            fota.waitingUpload(120);
          System.exit(0);

        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
