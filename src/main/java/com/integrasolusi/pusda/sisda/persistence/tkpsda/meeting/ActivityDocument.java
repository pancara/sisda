package com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 9/3/12
 * Time: 8:29 AM
 */
public class ActivityDocument implements Serializable {
    private Long id;

    private String name;

    private Activity activity;

    private Integer index;

    private String filename;
    
    private Integer version;
    
    private Boolean active;

    public ActivityDocument() {
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
