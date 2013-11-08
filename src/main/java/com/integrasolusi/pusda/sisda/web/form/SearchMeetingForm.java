package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 7/25/13
 * Time       : 6:07 PM
 */
public class SearchMeetingForm implements Serializable {
    private String keyword;

    public SearchMeetingForm() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
