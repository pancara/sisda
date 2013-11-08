package com.integrasolusi.pusda.sisda.web.form.sda.hidrologi;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 10/26/12
 * Time       : 4:32 PM
 */
public class KlimatologiForm implements Serializable {
    private String description;
    private Long year;
    private Long wilayahSungai;
    private MultipartFile file;

    public KlimatologiForm() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getWilayahSungai() {
        return wilayahSungai;
    }

    public void setWilayahSungai(Long wilayahSungai) {
        this.wilayahSungai = wilayahSungai;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}