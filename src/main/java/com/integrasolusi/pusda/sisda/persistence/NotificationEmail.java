package com.integrasolusi.pusda.sisda.persistence;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 4/15/13
 * Time       : 9:25 PM
 */
public class NotificationEmail implements Serializable {
    private Long id;
    private int version;
    private String address;
    private String name;

    public NotificationEmail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
