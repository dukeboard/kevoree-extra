package org.kevoree.extra.kserial;

import org.kevoree.extra.kserial.SerialPort.SerialPortException;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 06/02/12
 * Time: 09:58
 */
public abstract class Port {

    private int fd;
    private String port_name;
    private int port_bitrate;

    public abstract void open()throws SerialPortException;
    public abstract void close()throws SerialPortException;


    public int getFd() {
        return fd;
    }

    public void setFd(int fd) {
        this.fd = fd;
    }

    public String getPort_name() {
        return port_name;
    }

    public void setPort_name(String port_name) {
        this.port_name = port_name;
    }

    public int getPort_bitrate() {
        return port_bitrate;
    }

    public void setPort_bitrate(int port_bitrate) {
        this.port_bitrate = port_bitrate;
    }
}
