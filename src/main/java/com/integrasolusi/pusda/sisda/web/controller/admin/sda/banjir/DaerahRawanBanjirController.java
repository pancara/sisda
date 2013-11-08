package com.integrasolusi.pusda.sisda.web.controller.admin.sda.banjir;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.banjir.DaerahRawanBanjir;
import com.integrasolusi.pusda.sisda.service.sda.DaerahRawanBanjirService;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.web.form.SearchForm;
import com.integrasolusi.pusda.sisda.web.form.sda.DaerahRawanBanjirForm;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
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
 * User: pancara
 * Date: 8/6/12
 * Time: 11:39 AM
 */

@Controller("adminDaerahRawanBanjirController")
@RequestMapping("/admin/sda/banjir/daerah_rawan_banjir")
public class DaerahRawanBanjirController {
    @Autowired
    private DaerahRawanBanjirService daerahRawanBanjirService;

    @Autowired
    private PagingHelper pagingHelper;

    @Autowired
    private YearService yearService;

    @Autowired
    private DasService dasService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") SearchForm form) {
        return list(form, 1L);
    }

    @RequestMapping(value = "page/{page}.html")
    public ModelAndView list(@ModelAttribute("form") SearchForm form,
                             @PathVariable(value = "page") Long page) {

        ModelAndView mav = new ModelAndView("admin/sda/banjir/daerah_rawan_banjir/list");

        mav.addObject("current", page);
        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);


        Long count;
        List<Long> pages;
        List<DaerahRawanBanjir> daerahRawanBanjirList;

        if (StringUtils.isEmpty(form.getKeyword())) {
            count = daerahRawanBanjirService.countAlls();
            pages = pagingHelper.getDisplayedPages(page, count);
            daerahRawanBanjirList = daerahRawanBanjirService.findAlls(start, pagingHelper.getRowPerPage());
        } else {
            String keyword = "%" + form.getKeyword() + "%";
            count = daerahRawanBanjirService.countByKeyword(keyword);
            pages = pagingHelper.getDisplayedPages(page, count);
            daerahRawanBanjirList = daerahRawanBanjirService.findByKeyword(keyword, start, pagingHelper.getRowPerPage());
        }

        mav.addObject("count", count);
        mav.addObject("last", pagingHelper.calcPageCount(count));

        mav.addObject("pages", pages);
        mav.addObject("daerahRawanBanjirList", daerahRawanBanjirList);

        return mav;
    }


    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") DaerahRawanBanjirForm form) {
        return createFormView();
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") DaerahRawanBanjirForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(null, form);
            return createListView();
        }

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") DaerahRawanBanjirForm form) {
        DaerahRawanBanjir drb = daerahRawanBanjirService.findById(id);
        form.setName(drb.getName());
        form.setDescription(drb.getDescription());

        if (drb.getDas() != null) {
            form.setDas(drb.getDas().getId());
        }

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") DaerahRawanBanjirForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(id, form);
            return createListView();
        }
        return createFormView();
    }

    @RequestMapping(value = "manage.html", params = "remove", method = RequestMethod.POST)
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            daerahRawanBanjirService.removeByIds(ids);
        }
        return createViewRedirectToPage(page, keyword);
    }

    private ModelAndView createViewRedirectToPage(Long page, String keyword) {
        String sPage = page == null ? "" : String.format("%d", page);
        return new ModelAndView(String.format("redirect:/admin/sda/banjir/daerah_rawan_banjir/page/%s.html?keyword=%s", sPage, keyword));
    }

    private void saveForm(Long id, DaerahRawanBanjirForm form) throws IOException {
        DaerahRawanBanjir drb = (id == null ?
                new DaerahRawanBanjir() :
                daerahRawanBanjirService.findById(id));

        drb.setName(form.getName());
        drb.setDescription(form.getDescription());

        Das das = dasService.findById(form.getDas());
        drb.setDas(das);


        if (form.getFile() == null || form.getFile().isEmpty()) {
            daerahRawanBanjirService.save(drb);
        } else {
            drb.setFilename(form.getFile().getOriginalFilename());
            daerahRawanBanjirService.save(drb, form.getFile().getInputStream());
        }
    }

    private void validateForm(DaerahRawanBanjirForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }

        if (form.getDas() == null) {
            errors.reject("das.empty", "DAS belum diisi");
        }
    }

    private ModelAndView createFormView() {
        ModelAndView mav = new ModelAndView("/admin/sda/banjir/daerah_rawan_banjir/form");
        mav.addObject("dasLookup", getDasLookup());
        return mav;
    }

    private Map getDasLookup() {
        Map lookupData = new LinkedHashMap();
        List<Das> dasList = dasService.findAlls();
        for (Das das : dasList) {
            lookupData.put(das.getId(), das.getName());
        }
        return lookupData;
    }

    private ModelAndView createListView() {
        return new ModelAndView("redirect:/admin/sda/banjir/daerah_rawan_banjir/list.html");
    }

    @RequestMapping("{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        DaerahRawanBanjir drb = daerahRawanBanjirService.findById(id);
        if (drb == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(drb.getFilename()));
        daerahRawanBanjirService.getBlob(id, response.getOutputStream());
    }
}
