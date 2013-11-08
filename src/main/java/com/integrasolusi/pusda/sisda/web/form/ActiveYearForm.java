package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 12/3/12
 * Time       : 1:45 PM
 */
public class ActiveYearForm implements Serializable {
    private Integer year;

    public ActiveYearForm() {
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
