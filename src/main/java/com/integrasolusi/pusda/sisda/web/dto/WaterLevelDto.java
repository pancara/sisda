package com.integrasolusi.pusda.sisda.web.dto;

import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 6/20/13
 * Time       : 6:52 PM
 */
public class WaterLevelDto {
    private Date samplingAt;
    private Double value;

    public WaterLevelDto() {
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
