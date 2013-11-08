package com.integrasolusi.pusda.sisda.web.controller.admin.tkpsda;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.Schedule;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.service.tkpsda.ScheduleService;
import com.integrasolusi.pusda.sisda.web.form.tkpsda.ScheduleForm;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Programmer : pancara
 * Date       : 10/31/12
 * Time       : 3:18 PM
 */
@Controller("adminTkpsdaMeetingSchedule")
@RequestMapping("/admin/tkpsda/jadwal_sidang")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private YearService yearService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private PagingHelper pagingHelper;

    @RequestMapping("view.html")
    public ModelAndView view() {
        ModelAndView mav = new ModelAndView("admin/tkpsda/jadwal_sidang/list_ws");
        mav.addObject("wsList", wilayahSungaiService.findAlls());
        return mav;
    }

    @RequestMapping(value = "view/{ws}.html")
    public ModelAndView view(@PathVariable(value = "ws") Long wsId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/jadwal_sidang/list_year");
        mav.addObject("wilayahSungai", wilayahSungaiService.findById(wsId));

        mav.addObject("years", yearService.findAlls());
        return mav;
    }

    @RequestMapping(value = "view/{ws}/{year}.html")
    public ModelAndView view(@PathVariable(value = "ws") Long wsId,
                             @PathVariable(value = "year") Long yearId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/jadwal_sidang/list_schedule");

        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("wilayahSungai", ws);

        Year year = yearService.findById(yearId);
        mav.addObject("year", year);

        mav.addObject("schedules", scheduleService.findByWilayahSungaiAndYear(ws, year));
        return mav;
    }

    @RequestMapping(value = "add/{ws}/{year}.html", method = RequestMethod.GET)
    public ModelAndView addSchedule(@PathVariable(value = "ws") Long wsId,
                                    @PathVariable(value = "year") Long yearId,
                                    @ModelAttribute("form") ScheduleForm form) {
        return createScheduleFormView(wsId, yearId, null);
    }

    private ModelAndView createScheduleFormView(Long wsId, Long yearId, Long scheduleId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/jadwal_sidang/schedule_form");

        mav.addObject("wilayahSungai", wilayahSungaiService.findById(wsId));
        mav.addObject("year", yearService.findById(yearId));

        if (scheduleId != null) {
            mav.addObject("schedule", scheduleService.findById(scheduleId));
        }
        return mav;
    }

    @RequestMapping(value = "add/{ws}/{year}.html", method = RequestMethod.POST)
    public ModelAndView addActivity(@PathVariable(value = "ws") Long wsId,
                                    @PathVariable(value = "year") Long yearId,
                                    @ModelAttribute("form") ScheduleForm form,
                                    Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Schedule schedule = new Schedule();
            schedule.setTitle(form.getTitle());
            schedule.setContent(form.getContent());
            schedule.setHoldDate(form.getHoldDate().getCalendar().getTime());

            schedule.setYear(yearService.findById(yearId));
            schedule.setWilayahSungai(wilayahSungaiService.findById(wsId));

            scheduleService.save(schedule);

            return createRedirectScheduleView(wsId, yearId);
        }

        return createScheduleFormView(wsId, yearId, null);
    }

    private ModelAndView createRedirectScheduleView(Long wsId, Long yearId) {
        return new ModelAndView(String.format("redirect:/admin/tkpsda/jadwal_sidang/view/%d/%d.html", wsId, yearId));
    }


    private void validateForm(ScheduleForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getTitle())) {
            errors.reject("title.empty", "Judul belum diisi");
        }

        if (StringUtils.isEmpty(form.getContent())) {
            errors.reject("content.empty", "Isi lengkap belum diisi");
        }

        if (form.getHoldDate().getYear() == null || form.getHoldDate().getMonth() == null || form.getHoldDate().getDate() == null) {
            errors.reject("holdDate.empty", "Tgl pelaksanaan tidak valid");
        }

    }

    @RequestMapping(value = "edit/{ws}/{year}/{schedule}.html", method = RequestMethod.GET)
    public ModelAndView editSchedule(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable(value = "schedule") Long scheduleId,
                                     @ModelAttribute("form") ScheduleForm form) {
        Schedule schedule = scheduleService.findById(scheduleId);
        form.setTitle(schedule.getTitle());
        form.setContent(schedule.getContent());
        Calendar cal = new GregorianCalendar();
        cal.setTime(schedule.getHoldDate());
        form.getHoldDate().setCalendar(cal);

        return createScheduleFormView(wsId, yearId, null);
    }

    @RequestMapping(value = "edit/{ws}/{year}/{schedule}.html", method = RequestMethod.POST)
    public ModelAndView editActivity(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable(value = "schedule") Long scheduleId,
                                     @ModelAttribute("form") ScheduleForm form,
                                     Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Schedule schedule = scheduleService.findById(scheduleId);
            schedule.setTitle(form.getTitle());
            schedule.setContent(form.getContent());
            schedule.setHoldDate(form.getHoldDate().getCalendar().getTime());

            scheduleService.save(schedule);

            return createRedirectScheduleView(wsId, yearId);
        }

        return createScheduleFormView(wsId, yearId, null);
    }

    @RequestMapping(value = "remove/{ws}/{year}.html", method = RequestMethod.POST)
    public ModelAndView remove(@PathVariable(value = "ws") Long wsId,
                               @PathVariable(value = "year") Long yearId,
                               @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            scheduleService.removeByIds(ids);
        }
        return createRedirectScheduleView(wsId, yearId);
    }


}
