package com.integrasolusi.pusda.sisda.web.dto.patternplansda;

/**
 * Created by pancara on 17/06/15.
 */
public class PatternPlanSdaFileDto {
    private Long id;
    private String title;
    private String description;
    private String filename;
    private Boolean published;

    public PatternPlanSdaFileDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
}
