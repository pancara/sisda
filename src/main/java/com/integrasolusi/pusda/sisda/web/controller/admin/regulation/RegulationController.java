package com.integrasolusi.pusda.sisda.web.controller.admin.regulation;

import com.integrasolusi.pusda.sisda.persistence.regulation.Folder;
import com.integrasolusi.pusda.sisda.persistence.regulation.Regulation;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.pusda.sisda.service.RegulationFolderService;
import com.integrasolusi.pusda.sisda.service.RegulationService;
import com.integrasolusi.pusda.sisda.web.form.RegulationForm;
import com.integrasolusi.pusda.sisda.web.form.RegulationSearchForm;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Programmer   : pancara
 * Date         : 7/12/11
 * Time         : 12:11 PM
 */
@Controller("adminRegulationController")
@RequestMapping("/admin/regulation")
public class RegulationController {
    private static Logger logger = Logger.getLogger(RegulationController.class);

    @Autowired
    private RegulationService regulationService;

    @Autowired
    private RegulationFolderService regulationFolderService;

    @Autowired
    private BlobRepository blobRepository;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @Autowired
    private PagingHelper pagingHelper;


    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") RegulationSearchForm form) {
        return list(1L, form);
    }


    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @ModelAttribute("form") RegulationSearchForm form) {
        Long count = getCount(form);
        Long last = pagingHelper.calcPageCount(count);
        page = Math.min(last, page);
        Long start = pagingHelper.getStartRow(page);

        ModelAndView mav = new ModelAndView("admin/regulation/list");
        mav.addObject("current", page);
        mav.addObject("start", start);
        mav.addObject("count", count);
        mav.addObject("pages", pagingHelper.getDisplayedPages(page, count));
        mav.addObject("last", last);
        mav.addObject("regulationList", getRegulationList(form, start, pagingHelper.getRowPerPage()));
        return mav;
    }

    private Long getCount(RegulationSearchForm form) {
        if (StringUtils.isEmpty(form.getKeyword()))
            return regulationService.countAlls();
        else
            return regulationService.countByKeyword(createSearchKeyword(form.getKeyword()));
    }

    private List<Regulation> getRegulationList(RegulationSearchForm form, Long start, Long count) {
        if (StringUtils.isEmpty(form.getKeyword()))
            return regulationService.findAlls(start, count);
        else
            return regulationService.findByKeyword(createSearchKeyword(form.getKeyword()), start, count);
    }

    private String createSearchKeyword(String keyword) {
        return String.format("%%%s%%", keyword);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") RegulationForm form,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "page", required = false) Long page) {
        return createFomView(keyword, page);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    private ModelAndView add(@ModelAttribute("form") RegulationForm form, Errors errors,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "page", required = false) Long page) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            return createFomView(keyword, page);
        }

        Regulation regulation = new Regulation();
        regulation.setTitle(form.getTitle());
        regulation.setDescription(form.getDescription());

        Folder folder = form.getFolder() != null ?
                regulationFolderService.findById(form.getFolder()) :
                null;
        regulation.setFolder(folder);

        if (form.getFile() != null && !form.getFile().isEmpty()) {
            regulation.setFilename(form.getFile().getOriginalFilename());
            regulationService.save(regulation, form.getFile().getInputStream());
        } else {
            regulationService.save(regulation);
        }
        return createRedirectView(keyword, page);
    }

    private ModelAndView createRedirectView(String keyword, Long page) {
        return new ModelAndView(String.format("redirect:/admin/regulation/page/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    private ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") RegulationForm form,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "page", required = false) Long page) throws IOException {
        ModelAndView mav = createFomView(keyword, page);
        Regulation regulation = regulationService.findById(id);
        mav.addObject("regulation", regulation);

        form.setTitle(regulation.getTitle());
        form.setDescription(regulation.getDescription());
        if (regulation.getFolder() != null) {
            form.setFolder(regulation.getFolder().getId());
        }

        return mav;
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    private ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") RegulationForm form,
                              Errors errors,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "page", required = false) Long page) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            ModelAndView mav = createFomView(keyword, page);
            mav.addObject("regulation", regulationService.findById(id));
            return mav;
        }

        Regulation regulation = regulationService.findById(id);
        regulation.setTitle(form.getTitle());
        regulation.setDescription(form.getDescription());

        Folder folder = form.getFolder() != null ?
                regulationFolderService.findById(form.getFolder()) :
                null;
        regulation.setFolder(folder);

        if (form.getFile() != null && !form.getFile().isEmpty()) {
            regulation.setFilename(form.getFile().getOriginalFilename());
            regulationService.save(regulation, form.getFile().getInputStream());
        } else {
            regulationService.save(regulation);
        }
        return createRedirectView(keyword, page);
    }

    public void validateForm(RegulationForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getTitle())) {
            errors.reject("title.empty", "Judul belum diisi");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }
    }

    @RequestMapping("{id}/**")
    public void download(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
        Regulation regulation = regulationService.findById(id);
        String contentType = contentTypeUtils.getContentType(regulation.getFilename());
        response.setContentType(contentType);

        regulationService.copyContent(id, response.getOutputStream());
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            regulationService.removeByIds(ids);
        }

        return createRedirectView(keyword, page);
    }

    private ModelAndView createFomView(String keyword, Long page) {
        ModelAndView mav = new ModelAndView("admin/regulation/form");
        mav.addObject("keyword", keyword);
        mav.addObject("page", page);
        mav.addObject("folderLookup", getFolderLookup());
        return mav;
    }

    private Map<Long, String> getFolderLookup() {
        Map lookup = new LinkedHashMap();
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

}
