package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 11/27/12
 * Time       : 11:28 PM
 */
public class RegulationSearchForm implements Serializable {
    private String keyword;

    public RegulationSearchForm() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
