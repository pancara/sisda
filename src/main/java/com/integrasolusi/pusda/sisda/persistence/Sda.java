package com.integrasolusi.pusda.sisda.persistence;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 1:49 PM
 */
public class Sda implements Serializable {
    private Long id;
    private Integer version;
    private String title;
    private String description;
    private boolean published;
    private Integer index;


    public Sda() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
