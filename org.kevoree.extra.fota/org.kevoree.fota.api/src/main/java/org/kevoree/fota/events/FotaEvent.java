package org.kevoree.fota.events;


/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 06/02/12
 * Time: 11:32
 */
public interface FotaEvent {
    public void dispatchEvent(int msg);
    public long getDuree();
    public int getSize_uploaded();
    public int getProgram_size();
}
