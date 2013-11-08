package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * User: pancara
 * Date: 10/1/12
 * Time: 1:47 PM
 */
public class MapCategoryForm implements Serializable {
    private String name;
    private String description;
    private Long parent;

    public MapCategoryForm() {
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

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
}
