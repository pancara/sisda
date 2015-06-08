package com.integrasolusi.pusda.sisda.persistence.patternplanning;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/30/12
 * Time: 1:02 PM
 */
public class PatternPlanSdaFolder implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String description;
    private Integer index;

    private PatternPlanSdaFolder parent;

    public PatternPlanSdaFolder() {
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public PatternPlanSdaFolder getParent() {
        return parent;
    }

    public void setParent(PatternPlanSdaFolder parent) {
        this.parent = parent;
    }
}
