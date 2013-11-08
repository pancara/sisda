package com.integrasolusi.pusda.sisda.web.controller.admin.map;

import com.integrasolusi.pusda.sisda.persistence.Map;
import com.integrasolusi.pusda.sisda.persistence.MapCategory;
import com.integrasolusi.pusda.sisda.service.MapCategoryService;
import com.integrasolusi.pusda.sisda.service.MapService;
import com.integrasolusi.pusda.sisda.web.form.MapCategoryForm;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 9:03 AM
 */

@Controller("adminMapCategoryController")
@RequestMapping("/admin")
public class MapCategoryController {
    private static Logger logger = Logger.getLogger(MapCategoryController.class);
    private MapService mapService;
    private MapCategoryService mapCategoryService;

    

    @Autowired
    public void setMapService(MapService mapService) {
        this.mapService = mapService;
    }

    @Autowired
    public void setMapCategoryService(MapCategoryService mapCategoryService) {
        this.mapCategoryService = mapCategoryService;
    }

    @RequestMapping("map/category.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("admin/map/category/list");
        List<MapCategory> categoryList = mapCategoryService.findAlls();
        mav.addObject("categoryList", categoryList);
        return mav;
    }

    @RequestMapping(value = "map/category/add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") MapCategoryForm form) {
        return createFormView();
    }

    private ModelAndView createFormView() {
        ModelAndView mav = new ModelAndView("admin/map/category/form");
        mav.addObject("categories", mapCategoryService.findAlls("name"));
        return mav;
    }

    @RequestMapping(value = "map/category/add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") MapCategoryForm form, Errors errors) throws IOException {
        validateForm(null, form, errors);
        if (!errors.hasErrors()) {
            saveCategory(null, form);
            return new ModelAndView("redirect:/admin/map/category.html");
        }
        return createFormView();
    }

    private void saveCategory(Long id, MapCategoryForm form) {
        MapCategory category = (id == null) ?
                new MapCategory() :
                mapCategoryService.findById(id);
        category.setName(form.getName());
        category.setDescription(form.getDescription());
        if (form.getParent() == null) {
            category.setParent(null);
        } else {
            category.setParent(mapCategoryService.findById(form.getParent()));
        }

        mapCategoryService.save(category);
    }

    @RequestMapping(value = "map/category/edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") MapCategoryForm form) {
        ModelAndView mav = createFormView();
        MapCategory category = mapCategoryService.findById(id);
        if (category == null) {
            return new ModelAndView("redirect:/admin/map/category.html");
        }
        form.setName(category.getName());
        form.setDescription(category.getDescription());
        if (category.getParent() != null) {
            form.setParent(category.getParent().getId());
        } else {
            form.setParent(null);
        }
        return mav;
    }

    @RequestMapping(value = "map/category/edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") MapCategoryForm form, Errors errors) throws IOException {
        validateForm(id, form, errors);
        if (!errors.hasErrors()) {
            saveCategory(id, form);
            return new ModelAndView("redirect:/admin/map/category.html");
        }
        return createFormView();
    }


    private void validateForm(Long id, MapCategoryForm form, Errors errors) {

        // validasi rekursif parent-child
        if (id != null) {
            if (mapCategoryService.isCircular(id, form.getParent())) {
                errors.reject("parent.circular", "Kategori membentuk hubungan sirkular");
            }
        }
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }

    }

    @RequestMapping(value = "map/category/manage.html", params = "remove", method = RequestMethod.POST)
    public ModelAndView remove(@RequestParam(value = "ids", required = false) Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                List<Map> maps = mapService.findByFilter(new ValueFilter("category.id", QueryOperator.EQUALS, id, "category_id"));
                for (Map map : maps) {
                    map.setCategory(null);
                    mapService.save(map);
                }
            }
            mapCategoryService.removeByIds(ids);
        }
        return new ModelAndView("redirect:/admin/map/category.html");

    }

    @RequestMapping(value = "map/category/manage.html", params = "publish", method = RequestMethod.POST)
    public ModelAndView publish(@RequestParam(value = "ids", required = false) Long[] ids) {
        if (ids != null) {
            mapCategoryService.publishByIds(ids);
        }
        return new ModelAndView("redirect:/admin/map/category.html");

    }

    @RequestMapping(value = "map/category/manage.html", params = "unpublish", method = RequestMethod.POST)
    public ModelAndView unpublish(@RequestParam(value = "ids", required = false) Long[] ids) {
        if (ids != null) {
            mapCategoryService.unpublishByIds(ids);
        }
        return new ModelAndView("redirect:/admin/map/category.html");
    }


}
