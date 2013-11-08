package com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;

import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 7/28/13
 * Time       : 11:58 AM
 */
public class Activity {
    private Long id;
    private Integer version;
    private String name;
    private Date date;
    
    private WilayahSungai ws;
    private Year year;

    public Activity() {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
