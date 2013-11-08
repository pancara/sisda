package com.integrasolusi.pusda.sisda.persistence;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 6/27/11
 * Time         : 8:36 AM
 */
public class LinkType implements Serializable {
    public static Long INSTITUTION = 1L;
    public static Long MEDIA = 2L;
    
    private Long id;
    private Integer version;
    private String description;

    public LinkType() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
