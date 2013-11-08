package com.integrasolusi.pusda.sisda.web.form.tkpsda;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 11/1/12
 * Time       : 10:29 PM
 */
public class MeetingResultForm implements Serializable {
    private String name;
    private Long wilayahSungai;
    private MultipartFile file;

    public MeetingResultForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
