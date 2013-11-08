package com.integrasolusi.pusda.sisda.persistence.ffws;

import java.io.Serializable;
import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 6/11/13
 * Time       : 6:48 PM
 */
public class WaterLevel implements Serializable {
    private Long id;
    private Integer version;
    private Station station;
    private Date samplingAt;
    private Double value;

    public WaterLevel() {
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

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Date getSamplingAt() {
        return samplingAt;
    }

    public void setSamplingAt(Date samplingAt) {
        this.samplingAt = samplingAt;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
