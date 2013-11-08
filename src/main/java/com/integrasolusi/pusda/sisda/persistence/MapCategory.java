package com.integrasolusi.pusda.sisda.persistence;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 7/29/11
 * Time         : 4:26 PM
 */
public class MapCategory implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String description;
    private Boolean published = false;
    private MapCategory parent = null;

    public MapCategory() {
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


    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public MapCategory getParent() {
        return parent;
    }

    public void setParent(MapCategory parent) {
        this.parent = parent;
    }
}
