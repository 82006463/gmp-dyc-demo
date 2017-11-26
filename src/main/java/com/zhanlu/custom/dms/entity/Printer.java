package com.zhanlu.custom.dms.entity;

import com.zhanlu.framework.common.entity.CodeEntity;

import javax.persistence.Column;

/**
 * 打印机
 */
public class Printer extends CodeEntity {

    //打印机IP
    private String ip;
    //打印机端口
    private int port;
    private int rmiPort;

    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "port")
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Column(name = "rmi_port")
    public int getRmiPort() {
        return rmiPort;
    }

    public void setRmiPort(int rmiPort) {
        this.rmiPort = rmiPort;
    }
}
