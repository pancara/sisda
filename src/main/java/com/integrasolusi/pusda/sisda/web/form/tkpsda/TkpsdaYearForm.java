package com.integrasolusi.pusda.sisda.web.form.tkpsda;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 12/3/12
 * Time       : 1:45 PM
 */
public class TkpsdaYearForm implements Serializable {
    
    private Long year;

    public TkpsdaYearForm() {
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }
}
