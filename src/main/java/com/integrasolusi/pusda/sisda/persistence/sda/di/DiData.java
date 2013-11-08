package com.integrasolusi.pusda.sisda.persistence.sda.di;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 10:49 AM
 */
public class DiData implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String description;
    private String filename;
    private DiDi di;

    public DiData() {
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

    public DiDi getDi() {
        return di;
    }

    public void setDi(DiDi di) {
        this.di = di;
    }
}
