package com.integrasolusi.pusda.sisda.persistence.sda.sungai;

import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 6:18 PM
 */
public class SungaiSummary implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String filename;
    
    private WilayahSungai ws;

    public SungaiSummary() {
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WilayahSungai getWs() {
        return ws;
    }

    public void setWs(WilayahSungai ws) {
        this.ws = ws;
    }
}
