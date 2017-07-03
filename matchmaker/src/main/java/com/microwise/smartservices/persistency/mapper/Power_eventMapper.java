package com.microwise.smartservices.persistency.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 6/30/2017.
 */
@Mapper
@Component
public interface Power_eventMapper {
    void insertPowerEvent(@Param("pid") String pid, @Param("port") Integer port, @Param("status") String status);
}
