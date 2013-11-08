package com.integrasolusi.pusda.sisda.web.form;

import com.integrasolusi.pusda.sisda.web.form.component.DateComponent;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 12/2/12
 * Time       : 3:09 PM
 */
public class ProgressPekerjaanForm implements Serializable {
    private Long year;
    private Long satuanKerja;
    private DateComponent reportingDate = new DateComponent();
    private MultipartFile file;


    public ProgressPekerjaanForm() {
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getSatuanKerja() {
        return satuanKerja;
    }

    public void setSatuanKerja(Long satuanKerja) {
        this.satuanKerja = satuanKerja;
    }

    public DateComponent getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(DateComponent reportingDate) {
        this.reportingDate = reportingDate;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
