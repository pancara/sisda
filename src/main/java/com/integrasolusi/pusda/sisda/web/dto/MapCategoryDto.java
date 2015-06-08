package com.integrasolusi.pusda.sisda.web.dto;

import com.integrasolusi.pusda.sisda.persistence.Map;
import com.integrasolusi.pusda.sisda.persistence.MapCategory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by pancara on 05/06/15.
 */
public class MapCategoryDto {
    private MapCategory category;
    private List<MapCategoryDto> children = new LinkedList<>();
    private List<Map> maps = new LinkedList<>();

    public MapCategoryDto() {
    }

    public MapCategory getCategory() {
        return category;
    }

    public void setCategory(MapCategory category) {
        this.category = category;
    }

    public List<MapCategoryDto> getChildren() {
        return children;
    }

    public void setChildren(List<MapCategoryDto> children) {
        this.children = children;
    }

    public List<Map> getMaps() {
        return maps;
    }

    public void setMaps(List<Map> maps) {
        this.maps = maps;
    }
}
