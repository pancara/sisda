package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 10/8/12
 * Time: 12:18 PM
 */
public class SaboDamSearchForm implements Serializable {
    private String keyword;
    private Long sungai;

    public SaboDamSearchForm() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getSungai() {
        return sungai;
    }

    public void setSungai(Long sungai) {
        this.sungai = sungai;
    }
}
