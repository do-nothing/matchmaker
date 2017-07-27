package com.microwise.smartservices.moroesst;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lee on 7/25/2017.
 */
@Component("statusCenter")
public class StatusCenter {
    private Set<String> performingTask = new HashSet<String>();

    public void startTask(String commandsKey) {
        performingTask.add(commandsKey);
    }

    public void finishTask(String commandsKey) {
        performingTask.remove(commandsKey);
    }

    public boolean commandsIsLoak(String commandsKey) {
        for (String str : performingTask) {
            if (commandsKey.contains(str)) {
                return true;
            } else if (str.contains(commandsKey)) {
                return true;
            }
        }
        return false;
    }
}
