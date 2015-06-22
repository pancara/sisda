package com.integrasolusi.pusda.sisda.web.form.patternplanpsda;

import java.io.Serializable;

/**
 * Programmer   : pancara
 * Date         : 7/12/11
 * Time         : 2:04 PM
 */
public class PolaRencanaPsdaFolderForm implements Serializable {
    private String name;
    private Long parent;

    public PolaRencanaPsdaFolderForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
}
