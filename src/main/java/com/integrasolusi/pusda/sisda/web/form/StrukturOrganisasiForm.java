package com.integrasolusi.pusda.sisda.web.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * Programmer : pancara
 * Date       : 11/27/12
 * Time       : 4:28 PM
 */
public class StrukturOrganisasiForm {
    private String name;
    private String description;
    private MultipartFile file;

    public StrukturOrganisasiForm() {
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
