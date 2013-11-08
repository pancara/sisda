package com.integrasolusi.pusda.sisda.web.form.sda;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 10/10/12
 * Time: 2:54 AM
 */
public class SungaiSabodamForm implements Serializable {
    private String name;
    private MultipartFile map;

    public SungaiSabodamForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getMap() {
        return map;
    }

    public void setMap(MultipartFile map) {
        this.map = map;
    }
}
