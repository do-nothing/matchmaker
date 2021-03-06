package com.microwise.smartservices.persistency.mapper;

import com.microwise.smartservices.persistency.domain.Power_controller;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 6/29/2017.
 */
@Mapper
@Component
public interface Power_controllerMapper {
    Power_controller selectByPid(String pid);
    void updateStatusByPid(@Param("pid") String pid, @Param("status") int status);
}
