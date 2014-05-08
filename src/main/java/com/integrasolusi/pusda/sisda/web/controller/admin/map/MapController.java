package com.integrasolusi.pusda.sisda.web.controller.admin.map;

import com.integrasolusi.pusda.sisda.persistence.Map;
import com.integrasolusi.pusda.sisda.persistence.MapCategory;
import com.integrasolusi.pusda.sisda.service.MapCategoryService;
import com.integrasolusi.pusda.sisda.service.MapService;
import com.integrasolusi.pusda.sisda.web.form.MapForm;
import com.integrasolusi.pusda.sisda.web.form.SearchMapForm;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.ImageUtils;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 9:03 AM
 */

@Controller("adminMapController")
@RequestMapping("/admin")
public class MapController {
    private static Logger logger = Logger.getLogger(MapController.class);

    @Autowired
    private MapService mapService;

    @Autowired
    private MapCategoryService mapCategoryService;

    @Autowired
    private PagingHelper pagingHelper;

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("map.html")
    public ModelAndView list(@ModelAttribute("form") SearchMapForm form) {
        return list(1L, form);
    }

    @RequestMapping("map/page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @ModelAttribute("form") SearchMapForm form) {
        ModelAndView mav = new ModelAndView("admin/map/list");
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
                return mapService.countAlls();
            else
                return mapService.countByKeyword(createKeywordString(keyword));
        } else {
            if (StringUtils.isEmpty(keyword))
                return mapService.countByCategory(category);
            else
                return mapService.countByCategoryAndKeyword(category, createKeywordString(keyword));
        }
    }

    private List<Map> getMapList(Long category, String keyword, Long start, Long count) {
        if (category == null) {
            if (StringUtils.isEmpty(keyword))
                return mapService.findAlls(start, count);
            else
                return mapService.findByKeyword(createKeywordString(keyword), start, count);
        } else {
            if (StringUtils.isEmpty(keyword))
                return mapService.findByCategory(category, start, count);
            else
                return mapService.findByCategoryAndKeyword(category, createKeywordString(keyword), start, count);
        }
    }

    @RequestMapping("/map/{id}/**")
    public void downloadMap(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
        Map map = mapService.findById(id);
        String contentType = contentTypeUtils.getContentType(map.getFilename());
        response.setContentType(contentType);

        String filename = StringUtils.replace(map.getFilename(), " ", "_");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));

        if (map.getSize() != null)
            response.setContentLength(map.getSize().intValue());

        mapService.getDocument(id, response.getOutputStream());
    }

    @RequestMapping("/map/thumb/{id}/**")
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

    @RequestMapping(value = "map/add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") MapForm form) {
        ModelAndView mav = new ModelAndView("admin/map/form");
        mav.addObject("categoryList", mapCategoryService.findAlls());
        return mav;
    }

    @RequestMapping(value = "map/add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") MapForm form, Errors errors) throws IOException {
        validateForm(form, errors, false);

        if (!errors.hasErrors()) {
            Map map = new Map();
            map.setName(form.getName());
            map.setDescription(form.getDescription());
            map.setPublished(false);
            MapCategory category = mapCategoryService.findById(form.getCategory());
            map.setCategory(category);

            map.setFilename(form.getFile().getOriginalFilename());
            map.setSize(form.getFile().getSize());

            mapService.save(map, form.getFile().getInputStream());
            return new ModelAndView("redirect:/admin/map.html");
        }
        ModelAndView mav = new ModelAndView("admin/map/form");
        mav.addObject("categoryList", mapCategoryService.findLeafs("name"));
        return mav;
    }

    @RequestMapping(value = "map/edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") MapForm form) {
        ModelAndView mav = new ModelAndView("admin/map/form");
        Map map = mapService.findById(id);
        form.setName(map.getName());
        form.setDescription(map.getDescription());
        if (map.getCategory() != null) {
            form.setCategory(map.getCategory().getId());
        }

        mav.addObject("categoryList", mapCategoryService.findLeafs("name"));
        return mav;
    }

    @RequestMapping(value = "map/edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") MapForm form, Errors errors) throws IOException {
        validateForm(form, errors, true);

        if (!errors.hasErrors()) {
            Map map = mapService.findById(id);
            map.setName(form.getName());
            map.setDescription(form.getDescription());

            MapCategory category = mapCategoryService.findById(form.getCategory());
            map.setCategory(category);

            if (form.getFile() != null && !form.getFile().isEmpty()) {
                map.setFilename(form.getFile().getOriginalFilename());
                map.setSize(form.getFile().getSize());
                mapService.save(map, form.getFile().getInputStream());
            } else {
                mapService.save(map);
            }

            return new ModelAndView("redirect:/admin/map.html");
        }
        ModelAndView mav = new ModelAndView("admin/map/form");
        mav.addObject("categoryList", mapCategoryService.findAlls());
        return mav;
    }

    private void validateForm(MapForm form, Errors errors, boolean fileEmptyAllowed) {
        if (!fileEmptyAllowed) {
            if (form.getFile() == null || form.getFile().isEmpty()) {
                errors.reject("file.empty", "File belum diisi");
            }
        }
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }

        if (form.getCategory() == null) {
            errors.reject("category.empty", "Kategori belum diisi");
        }

    }

    @RequestMapping(value = "map/manage.html")
    public ModelAndView manage(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "category", required = false) Long category,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {
        return createViewRedirectToPage(page, category, keyword);
    }

    @RequestMapping(value = "map/manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "category", required = false) Long category,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            mapService.removeByIds(ids);
        }

        return createViewRedirectToPage(page, category, keyword);

    }

    @RequestMapping(value = "map/manage.html", params = "publish")
    public ModelAndView publish(@RequestParam(value = "page", required = false) Long page,
                                @RequestParam(value = "category", required = false) Long category,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            mapService.publishByIds(ids);
        }
        return createViewRedirectToPage(page, category, keyword);

    }

    @RequestMapping(value = "map/manage.html", params = "unpublish")
    public ModelAndView unpublish(@RequestParam(value = "page", required = false) Long page,
                                  @RequestParam(value = "category", required = false) Long category,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            mapService.unpublishByIds(ids);
        }
        return createViewRedirectToPage(page, category, keyword);

    }

    private ModelAndView createViewRedirectToPage(Long page, Long category, String keyword) {
        String sPage = page == null ? "" : String.format("%d", page);
        String sCategory = category == null ? "" : String.format("%d", category);
        return new ModelAndView(String.format("redirect:/admin/map/page/%s.html?category=%s&keyword=%s", sPage, sCategory, keyword));
    }
}
