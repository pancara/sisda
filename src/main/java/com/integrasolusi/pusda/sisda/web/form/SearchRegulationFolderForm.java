package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 7/12/11
 * Time         : 2:04 PM
 */
public class SearchRegulationFolderForm implements Serializable {
    private String keyword;

    public SearchRegulationFolderForm() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
