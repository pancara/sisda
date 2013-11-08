package com.integrasolusi.pusda.sisda.persistence;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 7/17/11
 * Time         : 11:10 PM
 */
public class PublicationAttachment implements Serializable {
    private Long id;
    private Integer version;
    private Publication publication;
    private String description;
    private String filename;
    private Long size;
    private Integer index;

    public PublicationAttachment() {
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

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
