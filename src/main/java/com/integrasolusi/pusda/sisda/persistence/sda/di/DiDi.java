package com.integrasolusi.pusda.sisda.persistence.sda.di;

/**
 * Programmer : pancara
 * Date       : 9/17/13
 * Time       : 10:43 AM
 */
public class DiDi {
    private Long id;
    private Integer version;
    private String name;
    private String filename;
    private DiType type;
    private Integer index;

    public DiDi() {
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

    public DiType getType() {
        return type;
    }

    public void setType(DiType type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
