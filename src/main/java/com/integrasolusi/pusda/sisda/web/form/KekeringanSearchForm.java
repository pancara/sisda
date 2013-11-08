package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 10/8/12
 * Time: 12:18 PM
 */
public class KekeringanSearchForm implements Serializable {
    private String keyword;
    private Long year;

    public KekeringanSearchForm() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }
}
