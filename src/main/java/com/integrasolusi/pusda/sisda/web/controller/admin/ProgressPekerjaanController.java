package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.ProgressPekerjaan;
import com.integrasolusi.pusda.sisda.persistence.SatuanKerja;
import com.integrasolusi.pusda.sisda.persistence.SystemConfig;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.service.ProgressPekerjaanService;
import com.integrasolusi.pusda.sisda.service.SatuanKerjaService;
import com.integrasolusi.pusda.sisda.service.SystemConfigService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.web.form.ActiveYearForm;
import com.integrasolusi.pusda.sisda.web.form.ProgressPekerjaanForm;
import com.integrasolusi.pusda.sisda.web.form.SearchProgressPekerjaanForm;
import com.integrasolusi.utils.PagingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Programmer : pancara
 * Date       : 12/2/12
 * Time       : 2:17 PM
 */
@Controller("adminProgressPekerjaanController")
@RequestMapping("/admin/progress_pekerjaan")
public class ProgressPekerjaanController {
    @Autowired
    private ProgressPekerjaanService progressPekerjaanService;

    @Autowired
    private SatuanKerjaService satuanKerjaService;

    @Autowired
    private YearService yearService;

    @Autowired
    private PagingHelper pagingHelper;

    @Autowired
    private SystemConfigService systemConfigService;

    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") SearchProgressPekerjaanForm form) {
        return list(1L, form);
    }

    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page, @ModelAttribute("form") SearchProgressPekerjaanForm form) {
        ModelAndView mav = createListView();
        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);

        Long count = getCount(form.getYear());
        mav.addObject("count", count);

        java.util.List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<ProgressPekerjaan> progressPekerjaanList = getProgressPekerjaanList(form.getYear(), start, pagingHelper.getRowPerPage());
        mav.addObject("progressPekerjaanList", progressPekerjaanList);
        return mav;
    }

    private ModelAndView createListView() {
        ModelAndView mav = new ModelAndView("admin/progress_pekerjaan/list");
        Map<Long, Object> yearLookup = new LinkedHashMap<Long, Object>();
        yearLookup.put(null, "- semua -");

        for (Year year : yearService.findAlls()) {
            yearLookup.put(year.getId(), year.getValue());
        }

        mav.addObject("yearLookup", yearLookup);
        return mav;
    }

    private Long getCount(Long year) {
        if (year == null)
            return progressPekerjaanService.countAlls();
        else
            return progressPekerjaanService.countByYear(year);
    }

    private java.util.List<ProgressPekerjaan> getProgressPekerjaanList(Long year, Long start, Long count) {
        if (year == null)
            return progressPekerjaanService.findAlls(start, count);
        else
            return progressPekerjaanService.findByYear(year, start, count);
    }

    private Map<Long, Integer> createYearLookup() {
        Map<Long, Integer> yearLookup = new LinkedHashMap<Long, Integer>();
        for (Year year : yearService.findAlls()) {
            yearLookup.put(year.getId(), year.getValue());
        }
        return yearLookup;
    }

    private Map<Long, String> createSatuanKerjaLookup() {
        Map<Long, String> satuanKerjaLookup = new LinkedHashMap<Long, String>();
        for (SatuanKerja satker : satuanKerjaService.findAlls()) {
            satuanKerjaLookup.put(satker.getId(), satker.getName());
        }
        return satuanKerjaLookup;
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") ProgressPekerjaanForm form) {
        return createFormView();
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") ProgressPekerjaanForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            return createFormView();
        }

        saveProgressPekerjaanForm(null, form);
        return createRedirectView();

    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable("id") Long id, @ModelAttribute("form") ProgressPekerjaanForm form) {
        ProgressPekerjaan progress = progressPekerjaanService.findById(id);
        if (progress == null) {
            return createRedirectView();
        }
        if (progress.getYear() != null)
            form.setYear(progress.getYear().getId());

        if (progress.getSatuanKerja() != null)
            form.setSatuanKerja(progress.getSatuanKerja().getId());

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(progress.getReportingDate());
        form.getReportingDate().setCalendar(cal);

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView add(@PathVariable("id") Long id, @ModelAttribute("form") ProgressPekerjaanForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            return createFormView();
        }

        saveProgressPekerjaanForm(id, form);

        return createRedirectView();

    }

    private void saveProgressPekerjaanForm(Long id, ProgressPekerjaanForm form) throws IOException {
        ProgressPekerjaan pp = id == null ?
                new ProgressPekerjaan() :
                progressPekerjaanService.findById(id);

        pp.setSatuanKerja(satuanKerjaService.findById(form.getSatuanKerja()));
        pp.setYear(yearService.findById(form.getYear()));

        pp.setReportingDate(form.getReportingDate().getCalendar().getTime());
        if (form.getFile() == null || form.getFile().isEmpty()) {
            progressPekerjaanService.save(pp);
        } else {
            pp.setFilename(form.getFile().getOriginalFilename());
            progressPekerjaanService.save(pp, form.getFile().getInputStream());
        }
    }

    private ModelAndView createRedirectView() {
        return new ModelAndView("redirect:/admin/progress_pekerjaan/list.html");
    }

    private void validateForm(ProgressPekerjaanForm form, Errors errors) {
        if (form.getYear() == null) {
            errors.reject("year.empty", "Tahun belum diisi");
        }

        if (form.getSatuanKerja() == null) {
            errors.reject("satuanKerja.empty", "Satuan kerja belum diisi");
        }

    }

    private ModelAndView createFormView() {
        ModelAndView mav = new ModelAndView("admin/progress_pekerjaan/form");
        mav.addObject("yearLookup", createYearLookup());
        mav.addObject("satuanKerjaLookup", createSatuanKerjaLookup());
        return mav;
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "year", required = false) Long year,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            progressPekerjaanService.removeByIds(ids);
        }
        String sYear = year == null ? "" : String.format("%d", year);
        return new ModelAndView(String.format("redirect:/admin/progress_pekerjaan/page/%d.html?year=%s", page, sYear));

    }

    @RequestMapping(value = "tahun_berjalan.html", method = RequestMethod.GET)
    public ModelAndView setActiveYear(@ModelAttribute("form") ActiveYearForm form) {
        SystemConfig config = systemConfigService.findById(SystemConfig.YEAR_PROGRESS_PEKERJAAN);
        if (config != null) {
            form.setYear(config.getIntValue());
        }
        return createActiveYearFormView();
    }

    @RequestMapping(value = "tahun_berjalan.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") ActiveYearForm form, Errors errors) {
        validateActiveYearForm(form, errors);
        if (errors.hasErrors()) {
            return createActiveYearFormView();
        }

        saveActiveYearForm(form);

        return createRedirectView();

    }

    private void saveActiveYearForm(ActiveYearForm form) {
        SystemConfig config = systemConfigService.findById(SystemConfig.YEAR_PROGRESS_PEKERJAAN);
        if (config == null) {
            config = new SystemConfig();
            config.setId(SystemConfig.YEAR_PROGRESS_PEKERJAAN);
        }
        config.setIntValue(form.getYear());
        systemConfigService.save(config);
    }

    private void validateActiveYearForm(ActiveYearForm form, Errors errors) {
        if (form.getYear() == null) {
            errors.reject("year.empty", "Tahun aktif belum diisi");
        }
    }

    private ModelAndView createActiveYearFormView() {
        ModelAndView mav = new ModelAndView("admin/progress_pekerjaan/form_active_year");

        // populate year value lookup
        Map<Integer, Integer> yearValueLookup = new LinkedHashMap<Integer, Integer>();
        for (Year year : yearService.findAlls()) {
            yearValueLookup.put(year.getValue(), year.getValue());
        }
        mav.addObject("yearValueLookup", yearValueLookup);
        return mav;
    }
}
