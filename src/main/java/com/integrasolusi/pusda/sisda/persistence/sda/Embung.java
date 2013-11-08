package com.integrasolusi.pusda.sisda.persistence.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 6:18 PM
 */
public class Embung implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String description;
    private String content;
    private Das das;

    public Embung() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Das getDas() {
        return das;
    }

    public void setDas(Das das) {
        this.das = das;
    }
}
