package com.integrasolusi.pusda.sisda.web.controller.admin.regulation;

import com.integrasolusi.pusda.sisda.persistence.regulation.Folder;
import com.integrasolusi.pusda.sisda.service.RegulationFolderService;
import com.integrasolusi.pusda.sisda.web.form.RegulationFolderForm;
import com.integrasolusi.pusda.sisda.web.form.SearchRegulationFolderForm;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Programmer   : pancara
 * Date         : 7/12/11
 * Time         : 12:11 PM
 */
@Controller("adminRegulationFolderController")
@RequestMapping("/admin/regulation/folder")
public class FolderController {
    private static Logger logger = Logger.getLogger(FolderController.class);

    @Autowired
    private RegulationFolderService regulationFolderService;

    @Autowired
    private PagingHelper pagingHelper;


    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") SearchRegulationFolderForm form) {
        return list(1L, form);
    }


    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @ModelAttribute("form") SearchRegulationFolderForm form) {
        Long count = getCount(form);
        Long last = pagingHelper.calcPageCount(count);
        page = Math.min(last, page);
        Long start = pagingHelper.getStartRow(page);

        ModelAndView mav = new ModelAndView("admin/regulation/folder/list");
        mav.addObject("current", page);
        mav.addObject("start", start);
        mav.addObject("count", count);
        mav.addObject("pages", pagingHelper.getDisplayedPages(page, count));
        mav.addObject("last", last);
        mav.addObject("folderList", getFolderList(form, start, pagingHelper.getRowPerPage()));
        return mav;
    }

    private Long getCount(SearchRegulationFolderForm form) {
        if (StringUtils.isEmpty(form.getKeyword()))
            return regulationFolderService.countAlls();
        else
            return regulationFolderService.countByKeyword(createSearchKeyword(form.getKeyword()));
    }

    private List<Folder> getFolderList(SearchRegulationFolderForm form, Long start, Long count) {
        if (StringUtils.isEmpty(form.getKeyword()))
            return regulationFolderService.findAlls(start, count);
        else
            return regulationFolderService.findByKeyword(createSearchKeyword(form.getKeyword()), start, count);
    }

    private String createSearchKeyword(String keyword) {
        return String.format("%%%s%%", keyword);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") RegulationFolderForm form,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "page", required = false) Long page) {
        return createFomView(keyword, page);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    private ModelAndView add(@ModelAttribute("form") RegulationFolderForm form,
                             Errors errors,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "page", required = false) Long page) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            return createFomView(keyword, page);
        }

        Folder folder = new Folder();
        folder.setName(form.getName());

        Folder parent = form.getParent() != null ?
                regulationFolderService.findById(form.getParent()) :
                null;
        folder.setParent(parent);

        regulationFolderService.save(folder);
        return createRedirectView(keyword, page);
    }

    private ModelAndView createRedirectView(String keyword, Long page) {
        return new ModelAndView(String.format("redirect:/admin/regulation/folder/page/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    private ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") RegulationFolderForm form,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "page", required = false) Long page) throws IOException {
        ModelAndView mav = createFomView(keyword, page);
        Folder folder = regulationFolderService.findById(id);
        mav.addObject("folder", folder);

        form.setName(folder.getName());
        if (folder.getParent() != null) {
            form.setParent(folder.getParent().getId());
        }

        return mav;
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    private ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") RegulationFolderForm form,
                              Errors errors,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "page", required = false) Long page) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            ModelAndView mav = createFomView(keyword, page);
            mav.addObject("folder", regulationFolderService.findById(id));
            return mav;
        }

        Folder folder = regulationFolderService.findById(id);
        folder.setName(form.getName());

        Folder parent = form.getParent() != null ?
                regulationFolderService.findById(form.getParent()) :
                null;
        folder.setParent(parent);

        regulationFolderService.save(folder);
        return createRedirectView(keyword, page);
    }

    public void validateForm(RegulationFolderForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            regulationFolderService.removeByIds(ids);
        }

        return createRedirectView(keyword, page);
    }

    private ModelAndView createFomView(String keyword, Long page) {
        ModelAndView mav = new ModelAndView("admin/regulation/folder/form");
        mav.addObject("keyword", keyword);
        mav.addObject("page", page);
        mav.addObject("folderLookup", getFolderLookup());
        return mav;
    }

    private Map<Long, String> getFolderLookup() {
        Map lookup = new LinkedHashMap();
        lookup.put(null, " --- ");
        populateFolder(null, lookup, 0);
        return lookup;
    }

    private void populateFolder(Folder parent, Map<Long, String> lookup, int depth) {
        List<Folder> folders = regulationFolderService.findByParent(parent);
        String prefix = "";
        for (int i = 0; i < depth; i++) {
            prefix += "----";
        }
        for (Folder folder : folders) {
            lookup.put(folder.getId(), prefix + folder.getName());
            populateFolder(folder, lookup, depth + 1);
        }
    }

    @RequestMapping(value = "{id}/down.html")
    public MappingJacksonJsonView moveDown(@PathVariable("id") Long id) {
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        Folder folder = regulationFolderService.findById(id);
        if (folder != null) {
            if (folder.getIndex() == null) {
                regulationFolderService.reindex();
            } else {
                Integer oldIndex = folder.getIndex();
                regulationFolderService.moveDown(folder);
                if (!oldIndex.equals(folder.getIndex())) {
                    view.addStaticAttribute("result", true);
                    return view;
                }
            }
        }
        view.addStaticAttribute("result", false);
        return view;
    }

    @RequestMapping(value = "{id}/up.html")
    public MappingJacksonJsonView moveUp(@PathVariable("id") Long id) {
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        Folder folder = regulationFolderService.findById(id);
        if (folder != null) {
            if (folder.getIndex() == null) {
                regulationFolderService.reindex();
            } else {
                Integer oldIndex = folder.getIndex();
                regulationFolderService.moveUp(folder);
                if (!oldIndex.equals(folder.getIndex())) {
                    view.addStaticAttribute("result", true);
                    return view;
                }
            }
        }
        view.addStaticAttribute("result", false);
        return view;
    }

}
