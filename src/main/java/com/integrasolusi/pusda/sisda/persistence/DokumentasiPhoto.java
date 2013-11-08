package com.integrasolusi.pusda.sisda.persistence;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : Jan 3, 2011
 * Time         : 9:31:42 AM
 */
public class DokumentasiPhoto implements Serializable {
    private Long id;
    private Integer version;
    private String title;
    private Integer index;
    private Dokumentasi dokumentasi;
    private String filename;

    public DokumentasiPhoto() {
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

    public Dokumentasi getDokumentasi() {
        return dokumentasi;
    }

    public void setDokumentasi(Dokumentasi dokumentasi) {
        this.dokumentasi = dokumentasi;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}