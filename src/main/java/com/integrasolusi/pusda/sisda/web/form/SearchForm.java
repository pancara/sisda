package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 6/21/11
 * Time         : 3:12 PM
 */
public class SearchForm implements Serializable {
    private String keyword;

    public SearchForm() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
