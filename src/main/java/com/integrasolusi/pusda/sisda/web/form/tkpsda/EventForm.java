package com.integrasolusi.pusda.sisda.web.form.tkpsda;

import com.integrasolusi.pusda.sisda.web.form.component.DateComponent;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 7/30/13
 * Time       : 9:58 AM
 */
public class EventForm implements Serializable {
    private String title;

    private DateComponent date = new DateComponent();

    public EventForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateComponent getDate() {
        return date;
    }

    public void setDate(DateComponent date) {
        this.date = date;
    }
}
