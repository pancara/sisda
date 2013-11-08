package com.integrasolusi.pusda.sisda.web.form;

import com.integrasolusi.pusda.sisda.web.form.component.DateTimeComponent;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 8:29 PM
 */
public class PublicationForm implements Serializable {
    private String title;
    private String shortDescription;
    private String fullContent;
    private DateTimeComponent publishedDate = new DateTimeComponent();

    public PublicationForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullContent() {
        return fullContent;
    }

    public void setFullContent(String fullContent) {
        this.fullContent = fullContent;
    }

    public DateTimeComponent getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(DateTimeComponent publishedDate) {
        this.publishedDate = publishedDate;
    }
}
