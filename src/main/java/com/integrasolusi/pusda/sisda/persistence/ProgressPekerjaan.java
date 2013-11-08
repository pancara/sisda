package com.integrasolusi.pusda.sisda.persistence;

import java.io.Serializable;
import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 10/16/12
 * Time       : 1:27 PM
 */
public class ProgressPekerjaan implements Serializable {
    private Long id;
    private Integer version;

    private SatuanKerja satuanKerja;
    private Year year;
    private Date reportingDate;
    private String filename;


    public ProgressPekerjaan() {
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

    public SatuanKerja getSatuanKerja() {
        return satuanKerja;
    }

    public void setSatuanKerja(SatuanKerja satuanKerja) {
        this.satuanKerja = satuanKerja;
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

    public Date getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(Date reportingDate) {
        this.reportingDate = reportingDate;
    }
}
