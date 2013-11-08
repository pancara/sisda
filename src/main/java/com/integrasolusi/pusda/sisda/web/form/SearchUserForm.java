package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 11/29/12
 * Time       : 12:32 PM
 */
public class SearchUserForm implements Serializable {
    private String keyword;

    public SearchUserForm() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
