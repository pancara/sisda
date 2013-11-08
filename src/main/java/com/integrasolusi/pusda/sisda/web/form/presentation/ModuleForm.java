package com.integrasolusi.pusda.sisda.web.form.presentation;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 7/26/13
 * Time       : 5:36 PM
 */
public class ModuleForm implements Serializable {
    private String name;
    private String description;
    private MultipartFile file;

    public ModuleForm() {
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
