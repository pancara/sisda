package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 6/19/11
 * Time         : 6:43 PM
 */
public class GuestMessageForm implements Serializable {
    private String name;
    private String email;
    private String phone;
    private String message;
    private String captcha;

    public GuestMessageForm() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
