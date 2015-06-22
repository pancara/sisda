package com.integrasolusi.pusda.sisda.persistence.patternplanning;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 7/12/11
 * Time         : 9:02 AM
 */
public class PolaRencanaPsdaFile implements Serializable {
    private Long id;
    private Integer version;
    private String title;
    private String description;
    private String filename;

    private PolaRencanaPsdaFolder folder;
    private Integer index;

    public PolaRencanaPsdaFile() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public PolaRencanaPsdaFolder getFolder() {
        return folder;
    }

    public void setFolder(PolaRencanaPsdaFolder folder) {
        this.folder = folder;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

}
