package com.integrasolusi.pusda.sisda.web.form.sda;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 10/26/12
 * Time       : 4:32 PM
 */
public class DaerahRawanBanjirForm implements Serializable {
    private String name;
    private String description;
    private Long das;
    private MultipartFile file;

    public DaerahRawanBanjirForm() {
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

    public Long getDas() {
        return das;
    }

    public void setDas(Long das) {
        this.das = das;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
