package com.microwise.smartservices.moroesst.PowerLogic;

/**
 * Created by lee on 7/26/2017.
 */
public class CommandBean {
    private String command;
    private String power_id;
    private Integer device_id;
    private Integer port;
    private Integer second;

    @Override
    public String toString() {
        return "CommandBean{" +
                "command='" + command + '\'' +
                ", power_id='" + power_id + '\'' +
                ", device_id=" + device_id +
                ", port=" + port +
                ", second=" + second +
                '}';
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getPower_id() {
        return power_id;
    }

    public void setPower_id(String power_id) {
        this.power_id = power_id;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }
}
