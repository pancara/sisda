package com.integrasolusi.pusda.sisda.persistence.sda.di;

/**
 * User: pancara
 * Date: 8/12/12
 * Time: 10:50 AM
 */
public class DiType {
    private Long id;
    private Integer version;
    private String name;
    private String filename;

    public DiType() {
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
