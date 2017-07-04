package com.microwise.smartservices.persistency.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 6/30/2017.
 */
@Mapper
@Component
public interface DeviceMapper {
    void updateDeviceInfo(@Param("id") String id, @Param("status") int eventId, @Param("note") String note);
    void updateAppInfo(@Param("id") String id,
                       @Param("appName") String appName, @Param("appVersion") String appVersion,
                       @Param("status") int eventId, @Param("note") String note);

    void insertDeviceInfo(@Param("id") String id, @Param("status") int eventId);
    void insertAppInfo(@Param("id") String id, @Param("appName") String appName,
                       @Param("appVersion") String appVersion, @Param("status") int eventId);

}
