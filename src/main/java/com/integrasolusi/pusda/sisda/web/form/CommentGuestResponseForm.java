package com.integrasolusi.pusda.sisda.web.form;

import com.integrasolusi.pusda.sisda.web.form.component.DateTimeComponent;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 12:21 PM
 */
public class CommentGuestResponseForm implements Serializable {
    private String text;
    private String by;
    private DateTimeComponent postDate = new DateTimeComponent();

    public CommentGuestResponseForm() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public DateTimeComponent getPostDate() {
        return postDate;
    }

    public void setPostDate(DateTimeComponent postDate) {
        this.postDate = postDate;
    }
}
