package com.integrasolusi.pusda.sisda.persistence.region;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/1/12
 * Time: 11:31 AM
 */
public class Kabupaten implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String shortName;
    private Propinsi propinsi;

    public Kabupaten() {
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Propinsi getPropinsi() {
        return propinsi;
    }

    public void setPropinsi(Propinsi propinsi) {
        this.propinsi = propinsi;
    }
}
