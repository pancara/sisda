package com.integrasolusi.pusda.sisda.web.dto;

import com.integrasolusi.pusda.sisda.persistence.Person;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 7/20/11
 * Time         : 7:05 PM
 */
public class UnitDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String fullDescription;
    private Long childrenCount;
    private Person head;

    public UnitDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public Long getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(Long childrenCount) {
        this.childrenCount = childrenCount;
    }

    public Person getHead() {
        return head;
    }

    public void setHead(Person head) {
        this.head = head;
    }
}
