package com.integrasolusi.pusda.sisda.web.form.sda;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 10/8/12
 * Time: 5:38 PM
 */
public class SabodamForm implements Serializable {
    private String code;
    private String description;
    private Long sungai;
    private Long x;
    private Long y;
    private MultipartFile file;

    public SabodamForm() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Long getSungai() {
        return sungai;
    }

    public void setSungai(Long sungai) {
        this.sungai = sungai;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
    }
}
