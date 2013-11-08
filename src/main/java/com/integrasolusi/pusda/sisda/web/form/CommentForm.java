package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 12:21 PM
 */
public class CommentForm implements Serializable {
    private String name;
    private String email;
    private String site;
    private String message;
    private String captcha;

    public CommentForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public void clear() {
        this.name = null;
        this.email = null;
        this.site = null;
        this.message = null;
        this.captcha = null;
    }
}
