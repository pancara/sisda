package com.integrasolusi.pusda.sisda.web.form.sda.bendung;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 10/12/12
 * Time       : 12:51 AM
 */
public class BendungSummaryForm implements Serializable {
    private MultipartFile file;

    public BendungSummaryForm() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
