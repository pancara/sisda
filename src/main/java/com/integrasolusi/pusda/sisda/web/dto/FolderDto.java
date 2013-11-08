package com.integrasolusi.pusda.sisda.web.dto;

import com.integrasolusi.pusda.sisda.persistence.regulation.Folder;
import com.integrasolusi.pusda.sisda.persistence.regulation.Regulation;

import java.util.LinkedList;
import java.util.List;

/**
 * User: pancara
 * Date: 8/30/12
 * Time: 4:01 PM
 */

public class FolderDto {
    private Folder folder;
    private List<FolderDto> children = new LinkedList<>();
    private List<Regulation> regulations;

    public FolderDto() {
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public List<FolderDto> getChildren() {
        return children;
    }

    public void setChildren(List<FolderDto> children) {
        this.children = children;
    }

    public List<Regulation> getRegulations() {
        return regulations;
    }

    public void setRegulations(List<Regulation> regulations) {
        this.regulations = regulations;
    }
}