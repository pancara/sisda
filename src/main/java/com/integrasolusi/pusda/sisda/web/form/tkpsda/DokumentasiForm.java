package com.integrasolusi.pusda.sisda.web.form.tkpsda;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 11/1/12
 * Time       : 10:29 PM
 */
public class DokumentasiForm implements Serializable {
    private String title;
    private Long wilayahSungai;

    public DokumentasiForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getWilayahSungai() {
        return wilayahSungai;
    }

    public void setWilayahSungai(Long wilayahSungai) {
        this.wilayahSungai = wilayahSungai;
    }
 
}
