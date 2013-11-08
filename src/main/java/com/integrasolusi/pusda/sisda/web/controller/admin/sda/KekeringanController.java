package com.integrasolusi.pusda.sisda.web.controller.admin.sda;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.Kekeringan;
import com.integrasolusi.pusda.sisda.service.sda.KekeringanService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.web.form.KekeringanSearchForm;
import com.integrasolusi.pusda.sisda.web.form.sda.KekeringanForm;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pancara
 * Date: 10/8/12
 * Time: 12:13 PM
 */
@Controller("adminKekeringanController")
@RequestMapping("/admin/sda/kekeringan")
public class KekeringanController {
    @Autowired
    private KekeringanService kekeringanService;

    @Autowired
    private YearService yearService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private PagingHelper pagingHelper;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") KekeringanSearchForm form) {
        return list(1L, form);
    }

    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @ModelAttribute("form") KekeringanSearchForm form) {
        ModelAndView mav = new ModelAndView("admin/sda/kekeringan/list");
        List<Year> yearList = getYearList();
        mav.addObject("yearList", yearList);

        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);
        Long count = getCount(form.getYear(), form.getKeyword());
        mav.addObject("count", count);
        mav.addObject("last", pagingHelper.calcPageCount(count));

        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<Kekeringan> kekeringanList = getKekeringanList(form.getYear(), form.getKeyword(), start, pagingHelper.getRowPerPage());
        mav.addObject("kekeringanList", kekeringanList);

        return mav;
    }

    private List<Year> getYearList() {
        List<Year> yearList = yearService.findAlls();
        Year nullValue = new Year();
        nullValue.setValue(null);
        yearList.add(0, nullValue);
        return yearList;
    }

    private Long getCount(Long year, String keyword) {
        if (year == null) {
            if (StringUtils.isEmpty(keyword))
                return kekeringanService.countAlls();
            else
                return kekeringanService.countByKeyword(createKeywordString(keyword));
        } else {
            if (StringUtils.isEmpty(keyword))
                return kekeringanService.countByYear(year);
            else {
                return kekeringanService.countByYearAndKeyword(year, createKeywordString(keyword));
            }
        }
    }

    private List<Kekeringan> getKekeringanList(Long year, String keyword, Long start, Long count) {
        if (year == null) {
            if (StringUtils.isEmpty(keyword))
                return kekeringanService.findAlls(start, count);
            else
                return kekeringanService.findByKeyword(createKeywordString(keyword), start, count);
        } else {
            if (StringUtils.isEmpty(keyword))
                return kekeringanService.findByYear(year, start, count);
            else
                return kekeringanService.findByYearAndKeyword(year, createKeywordString(keyword), start, count);
        }
    }

    private String createKeywordString(String keyword) {
        return String.format("%%%s%%", keyword);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") KekeringanForm form) {
        return createFormView();
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") KekeringanForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Kekeringan kekeringan = new Kekeringan();
            kekeringan.setDescription(form.getDescription());
            kekeringan.setYear(yearService.findById(form.getYear()));
            kekeringan.setWilayahSungai(wilayahSungaiService.findById(form.getWilayahSungai()));

            if (form.getFile() != null && !form.getFile().isEmpty()) {
                kekeringan.setFilename(form.getFile().getOriginalFilename());
                kekeringanService.save(kekeringan, form.getFile().getInputStream());
            } else {
                kekeringanService.save(kekeringan);
            }
            return new ModelAndView(String.format("redirect:/admin/sda/kekeringan/list.html"));
        }
        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") KekeringanForm form) {
        Kekeringan kekeringan = kekeringanService.findById(id);
        form.setDescription(kekeringan.getDescription());
        if (kekeringan.getYear() != null)
            form.setYear(kekeringan.getYear().getId());

        if (kekeringan.getWilayahSungai() != null) {
            form.setWilayahSungai(kekeringan.getWilayahSungai().getId());
        }

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") KekeringanForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Kekeringan kekeringan = kekeringanService.findById(id);
            kekeringan.setDescription(form.getDescription());
            kekeringan.setYear(yearService.findById(form.getYear()));
            kekeringan.setWilayahSungai(wilayahSungaiService.findById(form.getWilayahSungai()));
            if (form.getFile() != null && !form.getFile().isEmpty()) {
                kekeringan.setFilename(form.getFile().getOriginalFilename());
                kekeringanService.save(kekeringan, form.getFile().getInputStream());
            } else {
                kekeringanService.save(kekeringan);
            }
            return new ModelAndView(String.format("redirect:/admin/sda/kekeringan/list.html"));
        }
        return createFormView();
    }

    private ModelAndView createFormView() {
        ModelAndView mav = new ModelAndView("/admin/sda/kekeringan/form");
        mav.addObject("yearLookup", getYearLookup());
        mav.addObject("wilayahSungaiLookup", getWilayahSungaiLookup());
        return mav;
    }

    private Map getYearLookup() {
        Map lookup = new LinkedHashMap();
        for (Year year : yearService.findAlls()) {
            lookup.put(year.getId(), year.getValue());
        }
        return lookup;
    }

    private Map getWilayahSungaiLookup() {
        Map lookup = new LinkedHashMap();
        for (WilayahSungai ws : wilayahSungaiService.findAlls()) {
            lookup.put(ws.getId(), ws.getName());
        }

        return lookup;
    }

    private void validateForm(KekeringanForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }

        if (form.getYear() == null) {
            errors.reject("year.empty", "Tahun belum diisi");
        }

        if (form.getWilayahSungai() == null) {
            errors.reject("wilayahSungai.empty", "Wilayah sungai belum diisi");
        }
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "year", required = false) Long year,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            kekeringanService.removeByIds(ids);
        }

        return createViewRedirectToPage(page, year, keyword);
    }

    private ModelAndView createViewRedirectToPage(Long page, Long year, String keyword) {
        String sPage = page == null ? "" : String.format("%d", page);
        String sYear = year == null ? "" : String.format("%d", year);
        return new ModelAndView(String.format("redirect:/admin/sda/kekeringan/page/%s.html?year=%s&keyword=%s", sPage, sYear, keyword));
    }
}
