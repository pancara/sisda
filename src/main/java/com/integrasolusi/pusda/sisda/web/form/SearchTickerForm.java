package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 11/29/12
 * Time       : 2:58 PM
 */
public class SearchTickerForm implements Serializable {
    private String keyword;

    public SearchTickerForm() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
