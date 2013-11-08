package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 10/8/12
 * Time: 12:18 PM
 */
public class EmbungSearchForm implements Serializable {
    private String keyword;
    private Long das;

    public EmbungSearchForm() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getDas() {
        return das;
    }

    public void setDas(Long das) {
        this.das = das;
    }

}
