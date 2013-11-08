package com.integrasolusi.pusda.sisda.persistence.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.region.Das;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 9/19/13
 * Time       : 3:50 PM
 */
public class PosTMA implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private Double longitude;
    private Double latitude;
    private String description;
    private String filename;
    private Das das;

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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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

    public Das getDas() {
        return das;
    }

    public void setDas(Das das) {
        this.das = das;
    }
}
