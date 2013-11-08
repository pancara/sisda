package com.integrasolusi.pusda.sisda.web.form.tkpsda;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 7/31/13
 * Time       : 2:13 PM
 */
public class MiscFolderForm implements Serializable{
    private String name;
    private Integer index;

    public MiscFolderForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
