package com.integrasolusi.pusda.sisda.persistence.tkpsda;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;

import java.io.Serializable;
import java.util.Date;

/**
 * User: pancara
 * Date: 9/3/12
 * Time: 9:56 AM
 */
public class Schedule implements Serializable {
    private Long id;
    private Integer version;
    private String title;
    private Date holdDate;
    private String content;
    
    private Year year;
    private WilayahSungai wilayahSungai;
    private Boolean active = false;


    public Schedule() {
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

    public Date getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(Date holdDate) {
        this.holdDate = holdDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
