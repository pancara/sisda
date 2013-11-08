package com.integrasolusi.pusda.sisda.persistence;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 7/29/11
 * Time         : 4:26 PM
 */
public class Map implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String description;
    private Boolean published = false;
    private String filename;
    private Long size;
    private MapCategory category;

    public Map() {
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public MapCategory getCategory() {
        return category;
    }

    public void setCategory(MapCategory category) {
        this.category = category;
    }
}
