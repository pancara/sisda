package com.integrasolusi.pusda.sisda.persistence.sda.hidrologi;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 8:38 PM
 */
public class PosHidrologi implements Serializable {
    private Long id;
    private Integer version;
    private String description;
    private String filename;
    private Boolean active;

    public PosHidrologi() {
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
