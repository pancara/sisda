package com.integrasolusi.pusda.sisda.web.form.sda;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/6/12
 * Time: 1:09 AM
 */
public class BendungForm implements Serializable {
    private String name;
    private Long wilayahSungai;
    private String description;
    private MultipartFile file;

    public BendungForm() {
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

    public Long getWilayahSungai() {
        return wilayahSungai;
    }

    public void setWilayahSungai(Long wilayahSungai) {
        this.wilayahSungai = wilayahSungai;
    }
}
