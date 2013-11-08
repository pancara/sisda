package com.integrasolusi.pusda.sisda.persistence.ffws;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 6/11/13
 * Time       : 6:46 PM
 */
public class Station implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String sourceTable;

    public Station() {
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", sourceTable='" + sourceTable + '\'' +
                '}';
    }
}
