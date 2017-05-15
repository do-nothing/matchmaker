package com.microwise.matchmaker.netconn;

import java.util.Arrays;

/**
 * Created by lee on 5/15/2017.
 */
public class ContentBean {
    private String command;
    private Object[] args;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "ContentBean{" +
                "command='" + command + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
