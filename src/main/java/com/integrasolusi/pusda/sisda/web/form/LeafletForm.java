package com.integrasolusi.pusda.sisda.web.form;

import com.integrasolusi.pusda.sisda.web.form.component.DateTimeComponent;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 8:29 PM
 */
public class LeafletForm implements Serializable {
    private String title;
    private String description;
    private DateTimeComponent publishedDate = new DateTimeComponent();
    private MultipartFile file;

    public LeafletForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTimeComponent getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(DateTimeComponent publishedDate) {
        this.publishedDate = publishedDate;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
