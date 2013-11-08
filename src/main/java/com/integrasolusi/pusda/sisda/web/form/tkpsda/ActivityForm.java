package com.integrasolusi.pusda.sisda.web.form.tkpsda;

import com.integrasolusi.pusda.sisda.web.form.component.DateComponent;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 7/29/13
 * Time       : 12:39 AM
 */
public class ActivityForm implements Serializable {
    private String name;
    private DateComponent date = new DateComponent();

    public ActivityForm() {
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
}
