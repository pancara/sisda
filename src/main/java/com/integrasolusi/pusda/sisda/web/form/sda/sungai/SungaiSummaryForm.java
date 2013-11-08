package com.integrasolusi.pusda.sisda.web.form.sda.sungai;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 10/12/12
 * Time       : 12:51 AM
 */
public class SungaiSummaryForm implements Serializable {
    private MultipartFile file;

    public SungaiSummaryForm() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
