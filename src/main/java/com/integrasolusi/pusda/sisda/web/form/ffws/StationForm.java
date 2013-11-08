package com.integrasolusi.pusda.sisda.web.form.ffws;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 7/27/13
 * Time       : 8:34 PM
 */
public class StationForm implements Serializable {
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String sourceTable;

    public StationForm() {
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
}
