package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 7/29/11
 * Time         : 8:46 AM
 */
public class UserForm implements Serializable {
    private String name;
    private String userId;
    private String password;
    private Boolean active;

    public UserForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
