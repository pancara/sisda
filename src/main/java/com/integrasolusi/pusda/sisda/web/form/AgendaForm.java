package com.integrasolusi.pusda.sisda.web.form;

import com.integrasolusi.pusda.sisda.web.form.component.DateTimeComponent;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 8:29 PM
 */
public class AgendaForm implements Serializable {
    private String title;
    private String description;
    private String fullContent;
    private DateTimeComponent holdDate = new DateTimeComponent();
    private Boolean published = false;

    public AgendaForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFullContent() {
        return fullContent;
    }

    public void setFullContent(String fullContent) {
        this.fullContent = fullContent;
    }

    public DateTimeComponent getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(DateTimeComponent holdDate) {
        this.holdDate = holdDate;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
}
