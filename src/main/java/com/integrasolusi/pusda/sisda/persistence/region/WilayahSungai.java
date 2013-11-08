package com.integrasolusi.pusda.sisda.persistence.region;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/1/12
 * Time: 11:39 AM
 */
public class WilayahSungai implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String shortName;

    public WilayahSungai() {
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

    @Override
    public String toString() {
        return "WilayahSungai{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }
}
