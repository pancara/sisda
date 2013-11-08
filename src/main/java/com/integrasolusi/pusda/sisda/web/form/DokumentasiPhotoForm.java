package com.integrasolusi.pusda.sisda.web.form;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 11/1/12
 * Time       : 10:29 PM
 */
public class DokumentasiPhotoForm implements Serializable {
    private String title;
    private MultipartFile file;

    public DokumentasiPhotoForm() {
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
