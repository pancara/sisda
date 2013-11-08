package com.integrasolusi.pusda.sisda.web.form;

import com.integrasolusi.pusda.sisda.web.form.component.DateTimeComponent;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 11/1/12
 * Time       : 10:29 PM
 */
public class DokumentasiForm implements Serializable {
    private String title;
    private DateTimeComponent publishedDate = new DateTimeComponent();

    public DokumentasiForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTimeComponent getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(DateTimeComponent publishedDate) {
        this.publishedDate = publishedDate;
    }
}
