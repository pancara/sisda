package com.integrasolusi.pusda.sisda.persistence.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.Das;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 8:37 PM
 */
public class KualitasAir implements Serializable {
    private Long id;
    private Integer version;
    private Das das;
    private Year year;
    private String filename;
    private String description;

    public KualitasAir() {
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

    public Das getDas() {
        return das;
    }

    public void setDas(Das das) {
        this.das = das;
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
