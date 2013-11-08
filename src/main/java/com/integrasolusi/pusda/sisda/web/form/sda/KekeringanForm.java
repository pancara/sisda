package com.integrasolusi.pusda.sisda.web.form.sda;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 10/17/12
 * Time       : 1:38 PM
 */
public class KekeringanForm implements Serializable {
    private Long wilayahSungai;
    private Long year;
    private String description;
    private MultipartFile file;

    public KekeringanForm() {
    }

    public Long getWilayahSungai() {
        return wilayahSungai;
    }

    public void setWilayahSungai(Long wilayahSungai) {
        this.wilayahSungai = wilayahSungai;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
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
