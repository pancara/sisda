package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.Map;
import com.integrasolusi.pusda.sisda.persistence.MapCategory;
import com.integrasolusi.pusda.sisda.service.MapCategoryService;
import com.integrasolusi.pusda.sisda.service.MapService;
import com.integrasolusi.pusda.sisda.web.dto.MapCategoryDto;
import com.integrasolusi.pusda.sisda.web.form.SearchMapForm;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.ImageUtils;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 9:03 AM
 */

@Controller("mapController")
@RequestMapping("/map")
public class MapController {
    private static Logger logger = LoggerFactory.getLogger(MapController.class);
    private MapService mapService;
    private MapCategoryService mapCategoryService;
    private PagingHelper pagingHelper;
    private ImageUtils imageUtils;
    private ContentTypeUtils contentTypeUtils;

    @Autowired
    public void setContentTypeUtils(ContentTypeUtils contentTypeUtils) {
        this.contentTypeUtils = contentTypeUtils;
    }

    @Autowired
    public void setMapService(MapService mapService) {
        this.mapService = mapService;
    }

    @Autowired
    public void setMapCategoryService(MapCategoryService mapCategoryService) {
        this.mapCategoryService = mapCategoryService;
    }

    @Autowired
    public void setPagingHelper(PagingHelper pagingHelper) {
        this.pagingHelper = pagingHelper;
    }

    @Autowired
    public void setImageUtils(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    private Object getMaps(MapCategory category) {
        if (mapCategoryService.childCount(category).intValue() > 0) {
            java.util.Map<MapCategory, Object> mapCategories = new LinkedHashMap<>();
            for (MapCategory c : mapCategoryService.findByParent(category, "name")) {
                mapCategories.put(c, getMaps(c));
            }
            return mapCategories;
        } else {
            return mapService.findByCategory(category);
        }

    }

    @RequestMapping("index.html")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("site/map/index");
        List<MapCategory> categoryList = createCategoryList();
        mav.addObject("categoryList", categoryList);

        List<MapCategoryDto> mapDto = new LinkedList<>();
        for (MapCategory cat : mapCategoryService.findByParent(null, "name")) {
            mapDto.add(entityToDto(cat));
        }

        mav.addObject("mapDto", mapDto);
        return mav;
    }

    private MapCategoryDto entityToDto(MapCategory category) {
        MapCategoryDto dto = new MapCategoryDto();
        dto.setCategory(category);

        List<Map> maps = mapService.findByCategory(category);
        dto.setMaps(maps);

        for (MapCategory c : mapCategoryService.findByParent(category, "name")) {
            dto.getChildren().add(this.entityToDto(c));
        }
        return dto;

    }

    @RequestMapping("search.html")
    public ModelAndView search(@ModelAttribute("form") SearchMapForm form) {
        return search(1L, form);
    }

    @RequestMapping("search/{page}.html")
    public ModelAndView search(@PathVariable("page") Long page, @ModelAttribute("form") SearchMapForm form) {

        ModelAndView mav = new ModelAndView("site/map/search");
        List<MapCategory> categoryList = createCategoryList();
        mav.addObject("categoryList", categoryList);

        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start_row", start);
        Long count = getCount(form.getCategory(), form.getKeyword());
        mav.addObject("count", count);
        mav.addObject("last_page", pagingHelper.calcPageCount(count));

        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<Map> mapList = getMapList(form.getCategory(), form.getKeyword(), start, pagingHelper.getRowPerPage());
        mav.addObject("mapList", mapList);

        return mav;
    }

    private String createKeywordString(String text) {
        String keyword = String.format("%%%s%%", text);
        logger.info("keyword = " + keyword);
        return keyword;

    }

    private List<MapCategory> createCategoryList() {
        List<MapCategory> categoryList = mapCategoryService.findAlls();
        MapCategory allCategory = new MapCategory();
        allCategory.setName("- SEMUA -");
        allCategory.setDescription("- SEMUA -");
        categoryList.add(0, allCategory);
        return categoryList;
    }

    private Long getCount(Long category, String keyword) {
        if (category == null) {
            if (StringUtils.isEmpty(keyword))
                return mapService.countByPublished(true);
            else
                return mapService.countByKeywordAndPublished(createKeywordString(keyword), true);
        } else {
            if (StringUtils.isEmpty(keyword))
                return mapService.countByCategoryAndPublished(category, true);
            else
                return mapService.countByCategoryAndKeywordAndPublished(category, createKeywordString(keyword), true);
        }
    }

    private List<Map> getMapList(Long category, String keyword, Long start, Long count) {
        if (category == null) {
            if (StringUtils.isEmpty(keyword))
                return mapService.findByPublished(start, count, true);
            else
                return mapService.findByKeywordAndPublished(createKeywordString(keyword), true, start, count);
        } else {
            if (StringUtils.isEmpty(keyword))
                return mapService.findByCategoryAndPublished(category, true, start, count);
            else
                return mapService.findByCategoryAndKeywordAndPublished(category, createKeywordString(keyword), true, start, count);
        }
    }

    @RequestMapping("/popup/map/view/{id}.html")
    public ModelAndView viewFull(@PathVariable("id") Long id) throws IOException {
        ModelAndView mav = new ModelAndView("site/map/view");
        mav.addObject("map", mapService.findById(id));
        return mav;
    }

    @RequestMapping("{id}/**")
    public void downloadFull(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
        Map map = mapService.findById(id);
        String contentType = contentTypeUtils.getContentType(map.getFilename());
        response.setContentType(contentType);

        if (map.getSize() != null)
            response.setContentLength(map.getSize().intValue());

        mapService.getDocument(id, response.getOutputStream());
    }

    @RequestMapping("thumb/{id}/**")
    public void downloadThumb(HttpServletResponse response,
                              @PathVariable("id") Long id,
                              @RequestParam(value = "width", required = false, defaultValue = "100") Integer w,
                              @RequestParam(value = "height", required = false, defaultValue = "0") Integer h) throws IOException {
        Map map = mapService.findById(id);
        String contentType = contentTypeUtils.getContentType(map.getFilename());
        response.setContentType(contentType);

        String format = StringUtils.lowerCase(FilenameUtils.getExtension(map.getFilename()));
        mapService.getBlob(id, w, h, response.getOutputStream(), format);
    }
}
