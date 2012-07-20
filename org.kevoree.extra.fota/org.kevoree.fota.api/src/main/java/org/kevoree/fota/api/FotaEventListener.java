package org.kevoree.fota.api;


import org.kevoree.fota.events.FotaEvent;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 06/02/12
 * Time: 11:49
 */
public interface FotaEventListener extends java.util.EventListener
{
    void progressEvent(FotaEvent evt);
    void completedEvent(FotaEvent evt);
}
