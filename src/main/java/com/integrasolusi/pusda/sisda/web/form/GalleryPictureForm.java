package com.integrasolusi.pusda.sisda.web.form;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 6/23/11
 * Time         : 5:56 PM
 */
public class GalleryPictureForm implements Serializable {
    private Long gallery;
    private String description;
    private MultipartFile file;

    public GalleryPictureForm() {
    }

    public Long getGallery() {
        return gallery;
    }

    public void setGallery(Long gallery) {
        this.gallery = gallery;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
