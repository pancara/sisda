package com.integrasolusi.pusda.sisda.persistence;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 3:44 PM
 */
public class Year implements Serializable {
    private Long id;
    private Integer version;
    private Integer value;

    public Year() {
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Year{" +
                "id=" + id +
                ", version=" + version +
                ", value=" + value +
                '}';
    }
}
