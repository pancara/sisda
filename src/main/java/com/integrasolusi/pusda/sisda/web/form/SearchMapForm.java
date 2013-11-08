package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 10/3/12
 * Time: 11:15 AM
 */
public class SearchMapForm implements Serializable {
    private Long category;
    private String keyword;

    public SearchMapForm() {
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
