package com.integrasolusi.pusda.sisda.web.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Programmer   : pancara
 * Date         : 6/12/11
 * Time         : 10:12 AM
 */
public class Menu {
    private String key;
    private String text;
    private String url;

    public Menu(String key, String text, String url) {
        this.text = text;
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
