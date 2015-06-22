package com.integrasolusi.pusda.sisda.web.dto.patternplansda;

import java.util.LinkedList;
import java.util.List;

/**
 * User: pancara
 * Date: 8/30/12
 * Time: 4:01 PM
 */

public class PatternPlanSdaFolderDto {
    private Long id;
    private String name;
    private String description;
    private Integer index;
    private Boolean published;

    private List<PatternPlanSdaFolderDto> children = new LinkedList<>();
    private List<PatternPlanSdaFileDto> files = new LinkedList<>();

    public PatternPlanSdaFolderDto() {
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public List<PatternPlanSdaFolderDto> getChildren() {
        return children;
    }

    public void setChildren(List<PatternPlanSdaFolderDto> children) {
        this.children = children;
    }

    public List<PatternPlanSdaFileDto> getFiles() {
        return files;
    }

    public void setFiles(List<PatternPlanSdaFileDto> files) {
        this.files = files;
    }
}