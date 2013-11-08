package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 11/29/12
 * Time       : 3:09 PM
 */
public class TickerForm implements Serializable {
    private String title;
    private String url;

    public TickerForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
