package com.integrasolusi.pusda.sisda.web.form.presentation;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 7/26/13
 * Time       : 5:31 PM
 */
public class SearchModuleForm implements Serializable {
    private String keyword;

    public SearchModuleForm() {
    }

    public SearchModuleForm(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
