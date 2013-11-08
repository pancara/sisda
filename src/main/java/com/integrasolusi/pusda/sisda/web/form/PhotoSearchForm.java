package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 11/17/12
 * Time       : 9:09 AM
 */
public class PhotoSearchForm implements Serializable {

    private String keyword;

    public PhotoSearchForm() {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
