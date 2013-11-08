package com.integrasolusi.pusda.sisda.persistence.sda;

import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 6:18 PM
 */
public class AirTanah implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String description;
    private String filename;
    private WilayahSungai wilayahSungai;

    public AirTanah() {
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

    public WilayahSungai getWilayahSungai() {
        return wilayahSungai;
    }

    public void setWilayahSungai(WilayahSungai wilayahSungai) {
        this.wilayahSungai = wilayahSungai;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
