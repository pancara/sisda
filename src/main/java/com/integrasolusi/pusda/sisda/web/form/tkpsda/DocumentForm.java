package com.integrasolusi.pusda.sisda.web.form.tkpsda;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 7/29/13
 * Time       : 1:30 AM
 */
public class DocumentForm implements Serializable {
    
    private String name;

    private Integer index;

    private MultipartFile file;

    public DocumentForm() {
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
