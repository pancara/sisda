package com.integrasolusi.pusda.sisda.persistence.region;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/2/12
 * Time: 12:12 PM
 */
public class Das implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private WilayahSungai wilayahSungai;

    public Das() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WilayahSungai getWilayahSungai() {
        return wilayahSungai;
    }

    public void setWilayahSungai(WilayahSungai wilayahSungai) {
        this.wilayahSungai = wilayahSungai;
    }
}
