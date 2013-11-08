package com.integrasolusi.pusda.sisda.web.form.presentation;

import com.integrasolusi.pusda.sisda.web.form.component.DateComponent;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 7/25/13
 * Time       : 6:12 PM
 */
public class MeetingForm implements Serializable {
    private String name;
    private DateComponent date = new DateComponent();
    private String location;

    public MeetingForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateComponent getDate() {
        return date;
    }

    public void setDate(DateComponent date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
