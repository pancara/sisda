package com.integrasolusi.pusda.sisda.persistence.tkpsda.misc;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 7/31/13
 * Time       : 11:47 AM
 */
public class MiscFolder implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private WilayahSungai ws;
    private Year year;
    private Integer index;

    public MiscFolder() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public WilayahSungai getWs() {
        return ws;
    }

    public void setWs(WilayahSungai ws) {
        this.ws = ws;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}
