package com.integrasolusi.pusda.sisda.web.controller.admin.patternplanpsda;

import com.integrasolusi.pusda.sisda.service.patternplanning.PolaRencanaPsdaFolderService;
import com.integrasolusi.pusda.sisda.web.form.patternplanpsda.PolaRencanaPsdaFolderForm;
import com.integrasolusi.pusda.sisda.web.form.patternplanpsda.SearchFolderForm;
import com.integrasolusi.pusda.sisda.persistence.patternplanning.PolaRencanaPsdaFolder;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
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
@Controller("adminPolaRencanaPsdaFolderController")
@RequestMapping("/admin/pola_rencana_psda_folder")
public class PolaRencanaPsdaFolderController {

    @Autowired
    private PolaRencanaPsdaFolderService polaRencanaPsdaFolderService;

    @Autowired
    private PagingHelper pagingHelper;


    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") SearchFolderForm form) {
        return list(1L, form);
    }


    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @ModelAttribute("form") SearchFolderForm form) {
        Long count = getCount(form);
        Long last = pagingHelper.calcPageCount(count);
        page = Math.min(last, page);
        Long start = pagingHelper.getStartRow(page);

        ModelAndView mav = new ModelAndView("admin/pola_rencana_psda_folder/list");
        mav.addObject("current", page);
        mav.addObject("start", start);
        mav.addObject("count", count);
        mav.addObject("pages", pagingHelper.getDisplayedPages(page, count));
        mav.addObject("last", last);
        mav.addObject("folders", getFolders(form, start, pagingHelper.getRowPerPage()));
        return mav;
    }

    private Long getCount(SearchFolderForm form) {
        if (StringUtils.isEmpty(form.getKeyword()))
            return polaRencanaPsdaFolderService.countAlls();
        else
            return polaRencanaPsdaFolderService.countByKeyword(createSearchKeyword(form.getKeyword()));
    }

    private List<PolaRencanaPsdaFolder> getFolders(SearchFolderForm form, Long start, Long count) {
        if (StringUtils.isEmpty(form.getKeyword()))
            return polaRencanaPsdaFolderService.findAlls(start, count);
        else
            return polaRencanaPsdaFolderService.findByKeyword(createSearchKeyword(form.getKeyword()), start, count);
    }

    private String createSearchKeyword(String keyword) {
        return String.format("%%%s%%", keyword);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") PolaRencanaPsdaFolderForm form,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "page", required = false) Long page) {
        return createFomView(keyword, page);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    private ModelAndView add(@ModelAttribute("form") PolaRencanaPsdaFolderForm form,
                             Errors errors,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "page", required = false) Long page) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            return createFomView(keyword, page);
        }

        PolaRencanaPsdaFolder folder = new PolaRencanaPsdaFolder();
        folder.setName(form.getName());

        PolaRencanaPsdaFolder parent = form.getParent() != null ?
                polaRencanaPsdaFolderService.findById(form.getParent()) :
                null;
        folder.setParent(parent);

        polaRencanaPsdaFolderService.save(folder);
        return createRedirectView(keyword, page);
    }

    private ModelAndView createRedirectView(String keyword, Long page) {
        return new ModelAndView(String.format("redirect:/admin/pola_rencana_psda_folder/page/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    private ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") PolaRencanaPsdaFolderForm form,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "page", required = false) Long page) throws IOException {
        ModelAndView mav = createFomView(keyword, page);
        PolaRencanaPsdaFolder folder = polaRencanaPsdaFolderService.findById(id);
        mav.addObject("folder", folder);

        form.setName(folder.getName());
        if (folder.getParent() != null) {
            form.setParent(folder.getParent().getId());
        }

        return mav;
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    private ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") PolaRencanaPsdaFolderForm form,
                              Errors errors,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "page", required = false) Long page) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            ModelAndView mav = createFomView(keyword, page);
            mav.addObject("folder", polaRencanaPsdaFolderService.findById(id));
            return mav;
        }

        PolaRencanaPsdaFolder folder = polaRencanaPsdaFolderService.findById(id);
        folder.setName(form.getName());

        PolaRencanaPsdaFolder parent = form.getParent() != null ?
                polaRencanaPsdaFolderService.findById(form.getParent()) :
                null;
        folder.setParent(parent);

        polaRencanaPsdaFolderService.save(folder);
        return createRedirectView(keyword, page);
    }

    public void validateForm(PolaRencanaPsdaFolderForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            polaRencanaPsdaFolderService.removeByIds(ids);
        }

        return createRedirectView(keyword, page);
    }

    private ModelAndView createFomView(String keyword, Long page) {
        ModelAndView mav = new ModelAndView("admin/pola_rencana_psda_folder/form");
        mav.addObject("keyword", keyword);
        mav.addObject("page", page);
        mav.addObject("folderLookup", getFolderLookup());
        return mav;
    }

    private Map<Long, String> getFolderLookup() {
        Map folderLookup = new LinkedHashMap();
        folderLookup.put(null, " --- ");
        populateFolder(null, folderLookup, 0);
        return folderLookup;
    }

    private void populateFolder(PolaRencanaPsdaFolder parent, Map<Long, String> lookup, int depth) {
        List<PolaRencanaPsdaFolder> folders = polaRencanaPsdaFolderService.findByParent(parent);
        String prefix = "";
        for (int i = 0; i < depth; i++) {
            prefix += "----";
        }
        for (PolaRencanaPsdaFolder folder : folders) {
            lookup.put(folder.getId(), prefix + folder.getName());
            populateFolder(folder, lookup, depth + 1);
        }
    }

    @RequestMapping(value = "{id}/down.html")
    public MappingJacksonJsonView moveDown(@PathVariable("id") Long id) {
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        PolaRencanaPsdaFolder folder = polaRencanaPsdaFolderService.findById(id);
        if (folder != null) {
            if (folder.getIndex() == null) {
                polaRencanaPsdaFolderService.reindex();
            } else {
                Integer oldIndex = folder.getIndex();
                polaRencanaPsdaFolderService.moveDown(folder);
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
        PolaRencanaPsdaFolder folder = polaRencanaPsdaFolderService.findById(id);
        if (folder != null) {
            if (folder.getIndex() == null) {
                polaRencanaPsdaFolderService.reindex();
            } else {
                Integer oldIndex = folder.getIndex();
                polaRencanaPsdaFolderService.moveUp(folder);
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
