package com.integrasolusi.pusda.sisda.persistence.region;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/2/12
 * Time: 12:12 PM
 */
public class SungaiSaboDam implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String filename;

    public SungaiSaboDam() {
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
