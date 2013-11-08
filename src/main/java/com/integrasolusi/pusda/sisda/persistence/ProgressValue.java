package com.integrasolusi.pusda.sisda.persistence;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 10/16/12
 * Time       : 1:29 PM
 */
public class ProgressValue implements Serializable {
    private Double value;
    private Double percentage;

    public ProgressValue() {
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}
