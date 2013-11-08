package com.integrasolusi.pusda.sisda.web.form.sda.bendung;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 10/12/12
 * Time       : 12:51 AM
 */
public class BendungForm implements Serializable {
    private String name;
    private String description;
    private Long das;
    private String mapUrl;
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


    public Long getDas() {
        return das;
    }

    public void setDas(Long das) {
        this.das = das;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }
}
