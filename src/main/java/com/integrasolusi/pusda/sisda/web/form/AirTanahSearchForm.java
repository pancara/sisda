package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 10/8/12
 * Time: 12:18 PM
 */
public class AirTanahSearchForm implements Serializable {
    private String keyword;

    public AirTanahSearchForm() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
