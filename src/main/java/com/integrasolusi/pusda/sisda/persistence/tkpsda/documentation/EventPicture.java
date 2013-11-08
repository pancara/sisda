package com.integrasolusi.pusda.sisda.persistence.tkpsda.documentation;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 9/3/12
 * Time: 8:29 AM
 */
public class EventPicture implements Serializable {
    private Long id;
    private Integer version;
    private String title;
    private Event event;
    private Integer index;
    private String filename;

    public EventPicture() {
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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
