package com.integrasolusi.pusda.sisda.persistence.tkpsda.documentation;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;

import java.io.Serializable;
import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 11/12/12
 * Time       : 3:26 PM
 */
public class Event implements Serializable {
    private Long id;
    private Integer version;
    private String title;
    private WilayahSungai wilayahSungai;

    private Date date;
    
    private Year year;
    private Boolean active;

    public Event() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
