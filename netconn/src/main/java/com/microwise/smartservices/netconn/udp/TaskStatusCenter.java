package com.microwise.smartservices.netconn.udp;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lee on 7/26/2017.
 */
@Component("taskStatusCenter")
public class TaskStatusCenter {
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

    private Set<String> receivedTask = new HashSet<String>();
    public void setReceivedTask(String taskKey){
        new Thread(()->{
            receivedTask.add(taskKey);
            try {
                Thread.sleep(35000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            receivedTask.remove(taskKey);
        }).start();
    }
    public boolean checkReceivedTask(String taskKey){
        return receivedTask.contains(taskKey);
    }
}
