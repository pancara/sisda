package com.integrasolusi.pusda.sisda.persistence;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 11/29/12
 * Time       : 2:40 PM
 */
public class Ticker implements Serializable {
    private Long id;
    private Integer version;
    private String title;
    private String url;
    private Boolean publish;

    public Ticker() {
    }

    public Ticker(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }
}
