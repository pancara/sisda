package com.integrasolusi.pusda.sisda.web.form.sda.di;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/6/12
 * Time: 1:09 AM
 */
public class TypeForm implements Serializable {
    private String name;
    private MultipartFile file;

    public TypeForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
