package com.microwise.smartservices.moroesst;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lee on 7/25/2017.
 */
@Component("statusCenter")
public class StatusCenter {
    private Set<String> unfinishedTask = new HashSet<String>();
    public void setUnfinishedTask(String taskKey){
        unfinishedTask.add(taskKey);
    }
    public void rmUnfinishedTask(String taskKey){
        unfinishedTask.remove(taskKey);
    }
    public boolean checkUnfinishedTask(String taskKey){
        return unfinishedTask.contains(taskKey);
    }

    public boolean commandsIsLoak(String commandsKey){
        return false;
    }
}
