package com.integrasolusi.pusda.sisda.web.form.patternplanpsda;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 7/12/11
 * Time         : 2:04 PM
 */
public class PolaRencanaPsdaFileForm implements Serializable {
    private String title;
    private String description;
    private Long folder;
    private MultipartFile file;

    public PolaRencanaPsdaFileForm() {
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

    public Long getFolder() {
        return folder;
    }

    public void setFolder(Long folder) {
        this.folder = folder;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
