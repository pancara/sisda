package com.integrasolusi.pusda.sisda.web.form.sda;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/6/12
 * Time: 1:09 AM
 */
public class BendunganForm implements Serializable {
    private String name;
    private String description;
    private String fullContent;
    private MultipartFile picture;

    public BendunganForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }
}
