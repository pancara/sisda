package com.integrasolusi.pusda.sisda.persistence.sda.bendung;

import com.integrasolusi.pusda.sisda.persistence.region.Das;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 6:18 PM
 */
public class Bendung implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private Das das;
    private String description;
    private String filename;

    private String mapUrl;

    public Bendung() {
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

    public Das getDas() {
        return das;
    }

    public void setDas(Das das) {
        this.das = das;
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


    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }
}
