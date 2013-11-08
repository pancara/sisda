package com.integrasolusi.pusda.sisda.web.form.sda.hidrologi;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 10/26/12
 * Time       : 4:32 PM
 */
public class PosCHForm implements Serializable {
    private String name;
    private Double longitude;
    private Double latitude;
    private String description;
    private MultipartFile file;
    private Long das;
    private String mapUrl;

    public PosCHForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public Long getDas() {
        return das;
    }

    public void setDas(Long das) {
        this.das = das;
    }
}
