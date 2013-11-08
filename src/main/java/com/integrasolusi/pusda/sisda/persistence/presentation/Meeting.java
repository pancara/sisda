package com.integrasolusi.pusda.sisda.persistence.presentation;

import java.io.Serializable;
import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 7/22/13
 * Time       : 5:47 PM
 */
public class Meeting implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private Date date;
    private String location;

    public Meeting() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
