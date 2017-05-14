package com.microwise.matchmaker.netconn;

/**
 * Created by John on 2017/5/14.
 */
public class MessageBean {
    private String id;
    private String target;
    private String command;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "id='" + id + '\'' +
                ", target='" + target + '\'' +
                ", command='" + command + '\'' +
                '}';
    }
}
