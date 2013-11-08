package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 12/2/12
 * Time       : 2:25 PM
 */
public class SearchProgressPekerjaanForm implements Serializable {
    private Long year;

    public SearchProgressPekerjaanForm() {
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }
}
