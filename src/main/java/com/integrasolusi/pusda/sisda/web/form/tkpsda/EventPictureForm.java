package com.integrasolusi.pusda.sisda.web.form.tkpsda;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 7/30/13
 * Time       : 10:08 AM
 */
public class EventPictureForm implements Serializable {
    private String title;
    private Integer index;
    private MultipartFile file;

    public EventPictureForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
