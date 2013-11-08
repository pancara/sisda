package com.integrasolusi.pusda.sisda.web.form;

import java.io.Serializable;

/**
 * Programmer : pancara
 * Date       : 10/12/12
 * Time       : 12:51 AM
 */
public class EmbungForm implements Serializable {
    private String name;
    private String description;
    private String content;
    private Long das;

    public EmbungForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDas() {
        return das;
    }

    public void setDas(Long das) {
        this.das = das;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}