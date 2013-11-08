package com.integrasolusi.pusda.sisda.persistence.sda;

import com.integrasolusi.pusda.sisda.persistence.region.SungaiSaboDam;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/1/12
 * Time: 11:45 AM
 */
public class SaboDam implements Serializable {
    private Long id;
    private Integer version;
    private String code;
    private String description;
    private SungaiSaboDam sungai;
    private Long x;
    private Long y;
    private String filename;

    public SaboDam() {
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


    public SungaiSaboDam getSungai() {
        return sungai;
    }

    public void setSungai(SungaiSaboDam sungai) {
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

    public String getFilename() { 
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
