package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 10/3/12
 * Time: 11:15 AM
 */
public class SearchSlideForm implements Serializable {
    private String keyword;

    public SearchSlideForm() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
