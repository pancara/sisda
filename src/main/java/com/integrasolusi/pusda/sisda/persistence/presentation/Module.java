package com.integrasolusi.pusda.sisda.persistence.presentation;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 7/22/13
 * Time       : 5:44 PM
 */
public class Module implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String description;
    private String filename;
    private Meeting meeting;

    public Module() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
}
