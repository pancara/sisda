package com.integrasolusi.pusda.sisda.persistence.sda.banjir;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.Das;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 8:34 PM
 */
public class Banjir implements Serializable {
    private Long id;
    private Integer version;
    private Year year;
    private Das das;
    private String filename;
    private String description;

    public Banjir() {
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
