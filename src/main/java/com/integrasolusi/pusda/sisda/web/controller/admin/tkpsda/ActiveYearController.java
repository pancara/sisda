package com.integrasolusi.pusda.sisda.web.controller.admin.tkpsda;

import com.integrasolusi.pusda.sisda.persistence.SystemConfig;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.service.SystemConfigService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.web.form.tkpsda.TkpsdaYearForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Programmer : pancara
 * Date       : 7/31/13
 * Time       : 4:02 PM
 */
@Controller("adminTkpsdaActiveYearController")
@RequestMapping("/admin/tkpsda/year")
public class ActiveYearController {

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private YearService yearService;

    @RequestMapping(value = "set.html", method = RequestMethod.GET)
    public ModelAndView setActiveYear(@ModelAttribute("form") TkpsdaYearForm form) {
        SystemConfig config = systemConfigService.findById(SystemConfig.TKPSDA_YEAR);

        if (config != null) {
            form.setYear(config.getLongValue());
        }
        return createFormView();
    }

    @RequestMapping(value = "set.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") TkpsdaYearForm form, Errors errors) {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveActiveYearForm(form);
            return new ModelAndView("redirect:/admin/tkpsda/year/set.html");
        }


        return createFormView();

    }

    private void saveActiveYearForm(TkpsdaYearForm form) {
        SystemConfig config = systemConfigService.findById(SystemConfig.TKPSDA_YEAR);
        if (config == null) {
            config = new SystemConfig();
            config.setId(SystemConfig.TKPSDA_YEAR);
            config.setDescription("TAHUN TKPSDA");
        }
        config.setLongValue(form.getYear());
        systemConfigService.save(config);
    }

    private void validateForm(TkpsdaYearForm form, Errors errors) {
        if (form.getYear() == null) {
            errors.reject("year.empty", "Tahun aktif belum diisi");
        }
    }

    private ModelAndView createFormView() {
        ModelAndView mav = new ModelAndView("admin/tkpsda/year_form");

        // populate year value lookup
        Map<Long, Integer> yearValueLookup = new LinkedHashMap<Long, Integer>();
        for (Year year : yearService.findAlls()) {
            yearValueLookup.put(year.getId(), year.getValue());
        }
        mav.addObject("yearValueLookup", yearValueLookup);
        return mav;
    }
}
