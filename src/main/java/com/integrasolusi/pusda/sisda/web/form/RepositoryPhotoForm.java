package com.integrasolusi.pusda.sisda.web.form;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 4:00 PM
 */
public class RepositoryPhotoForm implements Serializable {
    private String title;
    private MultipartFile file;

    public RepositoryPhotoForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
