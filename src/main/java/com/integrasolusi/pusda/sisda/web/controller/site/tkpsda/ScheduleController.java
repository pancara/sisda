package com.integrasolusi.pusda.sisda.web.controller.site.tkpsda;

import com.integrasolusi.pusda.sisda.persistence.SystemConfig;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.service.SystemConfigService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.service.tkpsda.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Programmer : pancara
 * Date       : 11/10/12
 * Time       : 10:55 PM
 */
@Controller
public class ScheduleController {
    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private YearService yearService;

    @RequestMapping("/tkpsda/jadwal_sidang/{wilayahSungai}/view.html")
    public ModelAndView list(@PathVariable("wilayahSungai") Long wilayahSungaiId) {
        SystemConfig config = systemConfigService.findById(SystemConfig.TKPSDA_YEAR);
        Year year = yearService.findById(config.getLongValue());
        
        ModelAndView mav = new ModelAndView("site/tkpsda/jadwal_sidang/list");
        WilayahSungai wilayahSungai = wilayahSungaiService.findById(wilayahSungaiId);
        mav.addObject("wilayahSungai", wilayahSungai);
        mav.addObject("scheduleList", scheduleService.findByWilayahSungaiAndYear(wilayahSungai, year));
        return mav;
    }

    @RequestMapping("/popup/tkpsda/jadwal_sidang/{wilayahSungai}/view/{id}.html")
    private ModelAndView view(@PathVariable("wilayahSungai") Long wsId,
                              @PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("site/tkpsda/jadwal_sidang/view");
        mav.addObject("schedule", scheduleService.findById(id));
        return mav;
    }
}
