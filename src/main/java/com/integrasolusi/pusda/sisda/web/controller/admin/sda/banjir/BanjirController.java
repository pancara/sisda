package com.integrasolusi.pusda.sisda.web.controller.admin.sda.banjir;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.banjir.Banjir;
import com.integrasolusi.pusda.sisda.service.sda.BanjirService;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.web.form.SearchForm;
import com.integrasolusi.pusda.sisda.web.form.sda.BanjirForm;
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

@Controller("adminBanjirController")
@RequestMapping("/admin/sda/banjir/data_banjir")
public class BanjirController {
    @Autowired
    private BanjirService banjirService;

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

        ModelAndView mav = new ModelAndView("admin/sda/banjir/data_banjir/list");

        mav.addObject("current", page);
        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);


        Long count = null;
        List<Long> pages = null;
        List<Banjir> dataBanjirList = null;
        Long last = null;

        if (StringUtils.isEmpty(form.getKeyword())) {
            count = banjirService.countAlls();
            pages = pagingHelper.getDisplayedPages(page, count);
            dataBanjirList = banjirService.findAlls(start, pagingHelper.getRowPerPage());
        } else {
            String keyword = "%" + form.getKeyword() + "%";
            count = banjirService.countByKeyword(keyword);
            pages = pagingHelper.getDisplayedPages(page, count);
            dataBanjirList = banjirService.findByKeyword(keyword, start, pagingHelper.getRowPerPage());
        }

        mav.addObject("count", count);
        mav.addObject("last", pagingHelper.calcPageCount(count));

        mav.addObject("pages", pages);
        mav.addObject("dataBanjirList", dataBanjirList);

        return mav;
    }


    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") BanjirForm form) {
        return createFormView();
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") BanjirForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(null, form);
            return createListView();
        }

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") BanjirForm form) {
        Banjir banjir = banjirService.findById(id);
        form.setDescription(banjir.getDescription());
        if (banjir.getYear() != null) {
            form.setYear(banjir.getYear().getId());
        }

        if (banjir.getDas() != null) {
            form.setDas(banjir.getDas().getId());
        }

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") BanjirForm form, Errors errors) throws IOException {
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
            banjirService.removeByIds(ids);
        }
        return createViewRedirectToPage(page, keyword);
    }

    private ModelAndView createViewRedirectToPage(Long page, String keyword) {
        String sPage = page == null ? "" : String.format("%d", page);
        return new ModelAndView(String.format("redirect:/admin/sda/banjir/data_banjir/page/%s.html?keyword=%s", sPage, keyword));
    }

    private void saveForm(Long id, BanjirForm form) throws IOException {
        Banjir banjir = (id == null ?
                new Banjir() :
                banjirService.findById(id));

        banjir.setDescription(form.getDescription());

        Year year = yearService.findById(form.getYear());
        banjir.setYear(year);

        Das das = dasService.findById(form.getDas());
        banjir.setDas(das);


        if (form.getFile() == null || form.getFile().isEmpty()) {
            banjirService.save(banjir);
        } else {
            banjir.setFilename(form.getFile().getOriginalFilename());
            banjirService.save(banjir, form.getFile().getInputStream());
        }
    }

    private void validateForm(BanjirForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }

        if (form.getYear() == null) {
            errors.reject("year.empty", "Tahun belum diisi");
        }

        if (form.getDas() == null) {
            errors.reject("das.empty", "DAS belum diisi");
        }
    }

    private ModelAndView createFormView() {
        ModelAndView mav = new ModelAndView("/admin/sda/banjir/data_banjir/form");
        mav.addObject("dasLookup", getDasLookup());
        mav.addObject("yearLookup", getYearLookup());
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

    private Map getYearLookup() {
        Map lookupData = new LinkedHashMap();
        List<Year> yearList = yearService.findAlls();
        for (Year year : yearList) {
            lookupData.put(year.getId(), year.getValue());
        }
        return lookupData;
    }

    private ModelAndView createListView() {
        return new ModelAndView("redirect:/admin/sda/banjir/data_banjir/list.html");
    }

    @RequestMapping("{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Banjir banjir = banjirService.findById(id);
        if (banjir == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(banjir.getFilename()));
        banjirService.getBlob(id, response.getOutputStream());
    }
}
