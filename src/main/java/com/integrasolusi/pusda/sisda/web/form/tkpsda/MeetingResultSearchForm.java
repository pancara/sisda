package com.integrasolusi.pusda.sisda.web.form.tkpsda;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 11/1/12
 * Time       : 10:17 PM
 */
public class MeetingResultSearchForm implements Serializable {
    private Long wilayahSungai;
    private String keyword;

    public MeetingResultSearchForm() {
    }

    public Long getWilayahSungai() {
        return wilayahSungai;
    }

    public void setWilayahSungai(Long wilayahSungai) {
        this.wilayahSungai = wilayahSungai;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}