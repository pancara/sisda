package com.integrasolusi.pusda.sisda.web.form.patternplanpsda;

import java.io.Serializable;

/**
 * Created by pancara on 21/06/15.
 */
public class SearchFolderForm implements Serializable {

    private String keyword;

    public SearchFolderForm() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
