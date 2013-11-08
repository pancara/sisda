package com.integrasolusi.pusda.sisda.persistence;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 7/20/11
 * Time         : 12:11 PM
 */
public class SatuanKerja implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String description;

    public SatuanKerja() {
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

}
