package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;
import java.util.Date;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 8:29 PM
 */
public class PotensiSdaForm implements Serializable {
    private String title;
    private String shortDescription;
    private String fullContent;
    private Date publishedDate;

    public PotensiSdaForm() {
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

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
}
