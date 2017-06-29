package com.microwise.smartservices.persistency.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lee on 6/29/2017.
 */
public class Power_controller implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String pid;
    private String name;
    private Integer museum_id;
    private String device_type;
    private String address;
    private Integer status;
    private String note;
    private Date created_at;
    private Date updated_at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMuseum_id() {
        return museum_id;
    }

    public void setMuseum_id(Integer museum_id) {
        this.museum_id = museum_id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Power_controller{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", museum_id=" + museum_id +
                ", device_type='" + device_type + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", note='" + note + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
