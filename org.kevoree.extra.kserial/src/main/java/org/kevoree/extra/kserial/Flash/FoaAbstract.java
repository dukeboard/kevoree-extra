package org.kevoree.extra.kserial.Flash;

/**
 * Created by jed
 * User: jedartois@gmail.com
 * Date: 06/02/12
 * Time: 10:04
 */
public abstract class FoaAbstract {

    protected  String device_name;
    protected  int device_target;
    protected  String node_target;
    protected int size;
    protected byte[] intel_hex;

   public abstract int  write_on_the_air_program( Byte[] raw_intel_hex_array);


    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public int getDevice_target() {
        return device_target;
    }

    public void setDevice_target(int device_target) {
        this.device_target = device_target;
    }

    public String getNode_target() {
        return node_target;
    }

    public void setNode_target(String node_target) {
        this.node_target = node_target;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte[] getIntel_hex() {
        return intel_hex;
    }

    public void setIntel_hex(byte[] intel_hex) {
        this.intel_hex = intel_hex;
    }
}
