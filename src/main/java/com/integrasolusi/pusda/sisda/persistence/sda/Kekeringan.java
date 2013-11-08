package com.integrasolusi.pusda.sisda.persistence.sda;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.Kabupaten;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 8:34 PM
 */
public class Kekeringan implements Serializable {
    private Long id;
    private Integer version;
    private Year year;
    private WilayahSungai wilayahSungai;
    private String filename;
    private String description;

    public Kekeringan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public WilayahSungai getWilayahSungai() {
        return wilayahSungai;
    }

    public void setWilayahSungai(WilayahSungai wilayahSungai) {
        this.wilayahSungai = wilayahSungai;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
