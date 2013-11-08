package com.integrasolusi.pusda.sisda.web.form.tkpsda;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 7/31/13
 * Time       : 2:18 PM
 */
public class MiscDocumentForm implements Serializable {
    private String name;

    private Integer index;

    private MultipartFile file;

    public MiscDocumentForm() {
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
