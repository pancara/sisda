package com.integrasolusi.pusda.sisda.web.form.tkpsda;

import com.integrasolusi.pusda.sisda.web.form.component.DateComponent;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 11/1/12
 * Time       : 10:29 PM
 */
public class ScheduleForm implements Serializable {
    private String title;
    private DateComponent holdDate = new DateComponent();
    private String content;


    public ScheduleForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateComponent getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(DateComponent holdDate) {
        this.holdDate = holdDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
 
}
