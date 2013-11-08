package com.integrasolusi.pusda.sisda.persistence.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.Year;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 8:36 PM
 */
public class TinggiMukaAir implements Serializable {
    private Long id;
    private Integer version;
    private String description;
    private PosTMA pos;
    private Year year;

    private String filename;


    public TinggiMukaAir() {
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

    public PosTMA getPos() {
        return pos;
    }

    public void setPos(PosTMA pos) {
        this.pos = pos;
    }
}
