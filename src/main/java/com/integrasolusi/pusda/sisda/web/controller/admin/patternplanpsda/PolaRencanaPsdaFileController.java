package com.integrasolusi.pusda.sisda.web.controller.admin.patternplanpsda;

import com.integrasolusi.pusda.sisda.persistence.patternplanning.PolaRencanaPsdaFile;
import com.integrasolusi.pusda.sisda.persistence.patternplanning.PolaRencanaPsdaFolder;
import com.integrasolusi.pusda.sisda.service.patternplanning.PolaRencanaPsdaFileService;
import com.integrasolusi.pusda.sisda.service.patternplanning.PolaRencanaPsdaFolderService;
import com.integrasolusi.pusda.sisda.web.form.patternplanpsda.PolaRencanaPsdaFileForm;
import com.integrasolusi.pusda.sisda.web.form.patternplanpsda.SearchFileForm;
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
@Controller("adminPolaRencanaPsdaFileController")
@RequestMapping("/admin/pola_rencana_psda_file")
public class PolaRencanaPsdaFileController {
    private static Logger logger = Logger.getLogger(PolaRencanaPsdaFileController.class);

    @Autowired
    private PolaRencanaPsdaFileService polaRencanaPsdaFileService;

    @Autowired
    private PolaRencanaPsdaFolderService polaRencanaPsdaFolderService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @Autowired
    private PagingHelper pagingHelper;


    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") SearchFileForm form) {
        return list(1L, form);
    }


    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page, @ModelAttribute("form") SearchFileForm form) {
        Long count = getCount(form);
        Long last = pagingHelper.calcPageCount(count);
        page = Math.min(last, page);
        Long start = pagingHelper.getStartRow(page);

        ModelAndView mav = new ModelAndView("admin/pola_rencana_psda_file/list");
        mav.addObject("current", page);
        mav.addObject("start", start);
        mav.addObject("count", count);
        mav.addObject("pages", pagingHelper.getDisplayedPages(page, count));
        mav.addObject("last", last);
        mav.addObject("fileList", getFileList(form, start, pagingHelper.getRowPerPage()));
        return mav;
    }

    private Long getCount(SearchFileForm form) {
        if (StringUtils.isEmpty(form.getKeyword()))
            return polaRencanaPsdaFileService.countAlls();
        else
            return polaRencanaPsdaFileService.countByKeyword(createSearchKeyword(form.getKeyword()));
    }

    private List<PolaRencanaPsdaFile> getFileList(SearchFileForm form, Long start, Long count) {
        if (StringUtils.isEmpty(form.getKeyword()))
            return polaRencanaPsdaFileService.findAlls(start, count);
        else
            return polaRencanaPsdaFileService.findByKeyword(createSearchKeyword(form.getKeyword()), start, count);
    }

    private String createSearchKeyword(String keyword) {
        return String.format("%%%s%%", keyword);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") PolaRencanaPsdaFileForm form,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "page", required = false) Long page) {
        return createFomView(keyword, page);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    private ModelAndView add(@ModelAttribute("form") PolaRencanaPsdaFileForm form, Errors errors,
                             @RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "page", required = false) Long page) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            return createFomView(keyword, page);
        }

        PolaRencanaPsdaFile file = new PolaRencanaPsdaFile();
        file.setTitle(form.getTitle());
        file.setDescription(form.getDescription());

        PolaRencanaPsdaFolder folder = form.getFolder() == null ?
                null : polaRencanaPsdaFolderService.findById(form.getFolder());
        file.setFolder(folder);

        if (form.getFile() != null && !form.getFile().isEmpty()) {
            file.setFilename(form.getFile().getOriginalFilename());
            polaRencanaPsdaFileService.save(file, form.getFile().getInputStream());
        } else {
            polaRencanaPsdaFileService.save(file);
        }
        return createRedirectView(keyword, page);
    }

    private ModelAndView createRedirectView(String keyword, Long page) {
        return new ModelAndView(String.format("redirect:/admin/pola_rencana_psda_file/page/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    private ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") PolaRencanaPsdaFileForm form,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "page", required = false) Long page) throws IOException {
        ModelAndView mav = createFomView(keyword, page);
        PolaRencanaPsdaFile psdaFile = polaRencanaPsdaFileService.findById(id);
        mav.addObject("psdaFile", psdaFile);
        form.setTitle(psdaFile.getTitle());
        form.setDescription(psdaFile.getDescription());

        if (psdaFile.getFolder() != null) {
            form.setFolder(psdaFile.getFolder().getId());
        }

        return mav;
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    private ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") PolaRencanaPsdaFileForm form,
                              Errors errors,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "page", required = false) Long page) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            ModelAndView mav = createFomView(keyword, page);
            mav.addObject("psdaFile", polaRencanaPsdaFileService.findById(id));
            return mav;
        }

        PolaRencanaPsdaFile psdaFile = polaRencanaPsdaFileService.findById(id);
        psdaFile.setTitle(form.getTitle());
        psdaFile.setDescription(form.getDescription());

        PolaRencanaPsdaFolder folder = form.getFolder() != null ?
                polaRencanaPsdaFolderService.findById(form.getFolder()) :
                null;
        psdaFile.setFolder(folder);

        if (form.getFile() != null && !form.getFile().isEmpty()) {
            psdaFile.setFilename(form.getFile().getOriginalFilename());
            polaRencanaPsdaFileService.save(psdaFile, form.getFile().getInputStream());
        } else {
            polaRencanaPsdaFileService.save(psdaFile);
        }
        return createRedirectView(keyword, page);
    }

    public void validateForm(PolaRencanaPsdaFileForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getTitle())) {
            errors.reject("title.empty", "Nama dokumen belum diisi");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }
    }

    @RequestMapping("{id}/**")
    public void download(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
        PolaRencanaPsdaFile psdaFile = polaRencanaPsdaFileService.findById(id);
        String contentType = contentTypeUtils.getContentType(psdaFile.getFilename());

        response.setContentType(contentType);
        polaRencanaPsdaFileService.copyContent(id, response.getOutputStream());
    }

    @RequestMapping(value = "remove.html")
    public ModelAndView remove(@RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            polaRencanaPsdaFileService.removeByIds(ids);
        }

        return createRedirectView(keyword, page);
    }

    private ModelAndView createFomView(String keyword, Long page) {
        ModelAndView mav = new ModelAndView("admin/pola_rencana_psda_file/form");
        mav.addObject("keyword", keyword);
        mav.addObject("page", page);
        mav.addObject("folders", getFolders());
        return mav;
    }

    private Map<Long, String> getFolders() {
        Map lookup = new LinkedHashMap();
        populateFolder(null, lookup, 0);
        return lookup;
    }

    private void populateFolder(PolaRencanaPsdaFolder parent, Map<Long, String> lookup, int depth) {
        List<PolaRencanaPsdaFolder> folders = polaRencanaPsdaFolderService.findByParent(parent);
        String prefix = "";
        for (int i = 0; i < depth; i++) {
            prefix += "&nbsp;&nbsp;&nbsp;";
        }
        for (PolaRencanaPsdaFolder folder : folders) {
            lookup.put(folder.getId(), prefix + folder.getName());
            populateFolder(folder, lookup, depth + 1);
        }
    }

}
