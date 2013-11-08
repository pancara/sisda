package com.integrasolusi.pusda.sisda.web.controller.admin.tkpsda;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.Activity;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.ActivityDocument;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.service.tkpsda.activity.ActivityDocumentService;
import com.integrasolusi.pusda.sisda.service.tkpsda.activity.ActivityService;
import com.integrasolusi.pusda.sisda.web.form.component.DateComponent;
import com.integrasolusi.pusda.sisda.web.form.tkpsda.ActivityForm;
import com.integrasolusi.pusda.sisda.web.form.tkpsda.DocumentForm;
import com.integrasolusi.utils.ContentTypeUtils;
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
@Controller("adminActivityDocumentController")
@RequestMapping("/admin/tkpsda/hasil_sidang")

public class ActivityDocumentController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityDocumentService activityDocumentService;

    @Autowired
    private YearService yearService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("view.html")
    public ModelAndView view() {
        ModelAndView mav = new ModelAndView("admin/tkpsda/activity_document/list_ws");
        mav.addObject("wsList", wilayahSungaiService.findAlls());
        return mav;
    }

    @RequestMapping(value = "view/{ws}.html")
    public ModelAndView view(@PathVariable(value = "ws") Long wsId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/activity_document/list_year");
        mav.addObject("wilayahSungai", wilayahSungaiService.findById(wsId));

        mav.addObject("years", yearService.findAlls());
        return mav;
    }

    @RequestMapping(value = "view/{ws}/{year}.html")
    public ModelAndView view(@PathVariable(value = "ws") Long wsId,
                             @PathVariable(value = "year") Long yearId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/activity_document/list_activity");

        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("wilayahSungai", ws);

        Year year = yearService.findById(yearId);
        mav.addObject("year", year);

        mav.addObject("activities", activityService.findByWilayahSungaiAndYear(ws, year));
        return mav;
    }

    @RequestMapping(value = "view/{ws}/{year}/{activity}.html")
    public ModelAndView view(@PathVariable(value = "ws") Long wsId,
                             @PathVariable(value = "year") Long yearId,
                             @PathVariable(value = "activity") Long activityId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/activity_document/list_activity_document");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("wilayahSungai", ws);

        Year year = yearService.findById(yearId);
        mav.addObject("year", year);

        Activity activity = activityService.findById(activityId);
        mav.addObject("activity", activity);

        mav.addObject("activityDocuments", activityDocumentService.findByActivity(activity));
        return mav;
    }


    @RequestMapping(value = "add/{ws}/{year}.html", method = RequestMethod.GET)
    public ModelAndView addActivity(@PathVariable(value = "ws") Long wsId,
                                    @PathVariable(value = "year") Long yearId,
                                    @ModelAttribute("form") ActivityForm form) {
        return createActivityFormView(wsId, yearId, null);
    }

    private ModelAndView createActivityFormView(Long wsId, Long yearId, Long activityId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/activity_document/activity_form");

        mav.addObject("wilayahSungai", wilayahSungaiService.findById(wsId));
        mav.addObject("year", yearService.findById(yearId));

        if (activityId != null) {
            mav.addObject("activity", activityService.findById(activityId));
        }
        return mav;

    }

    @RequestMapping(value = "add/{ws}/{year}.html", method = RequestMethod.POST)
    public ModelAndView addActivity(@PathVariable(value = "ws") Long wsId,
                                    @PathVariable(value = "year") Long yearId,
                                    @ModelAttribute("form") ActivityForm form,
                                    Errors errors) throws IOException {
        validateActivityForm(form, errors);
        if (!errors.hasErrors()) {
            Activity activity = new Activity();
            activity.setName(form.getName());
            activity.setDate(form.getDate().getCalendar().getTime());

            activity.setYear(yearService.findById(yearId));
            activity.setWs(wilayahSungaiService.findById(wsId));

            activityService.save(activity);

            return createRedirectActivityView(wsId, yearId);
        }

        return createActivityFormView(wsId, yearId, null);
    }

    private ModelAndView createRedirectActivityView(Long wsId, Long yearId) {
        return new ModelAndView(String.format("redirect:/admin/tkpsda/hasil_sidang/view/%d/%d.html", wsId, yearId));
    }

    private void validateActivityForm(ActivityForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama kegiatan belum diisi");
        }
    }

    @RequestMapping(value = "edit/{ws}/{year}/{activityId}.html", method = RequestMethod.GET)
    public ModelAndView editActivity(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable("activityId") Long activityId,
                                     @ModelAttribute("form") ActivityForm form) {
        Activity activity = activityService.findById(activityId);
        form.setName(activity.getName());
        Calendar cal = new GregorianCalendar();
        cal.setTime(activity.getDate());

        DateComponent date = new DateComponent();
        date.setCalendar(cal);
        form.setDate(date);

        return createActivityFormView(wsId, yearId, activityId);
    }

    @RequestMapping(value = "edit/{ws}/{year}/{activityId}.html", method = RequestMethod.POST)
    public ModelAndView editActivity(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable("activityId") Long activityId,
                                     @ModelAttribute("form") ActivityForm form,
                                     Errors errors) throws IOException {
        validateActivityForm(form, errors);
        if (!errors.hasErrors()) {
            Activity activity = activityService.findById(activityId);
            activity.setName(form.getName());
            activity.setDate(form.getDate().getCalendar().getTime());

            activityService.save(activity);

            return createRedirectActivityView(wsId, yearId);
        }

        return createActivityFormView(wsId, yearId, null);
    }

    @RequestMapping(value = "remove/{ws}/{year}.html", method = RequestMethod.POST)
    public ModelAndView removeActivity(@PathVariable(value = "ws") Long wsId,
                                       @PathVariable(value = "year") Long yearId,
                                       @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            activityService.removeByIds(ids);
        }
        return createRedirectActivityView(wsId, yearId);
    }

    @RequestMapping(value = "add/{ws}/{year}/{activity}.html", method = RequestMethod.GET)
    public ModelAndView addDocument(@PathVariable(value = "ws") Long wsId,
                                    @PathVariable(value = "year") Long yearId,
                                    @PathVariable(value = "activity") Long activityId,
                                    @ModelAttribute("form") DocumentForm form) {
        return createDocumentFormView(wsId, yearId, activityId, null);
    }

    private ModelAndView createDocumentFormView(Long wsId, Long yearId, Long activityId, Long documentId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/activity_document/activity_document_form");

        mav.addObject("wilayahSungai", wilayahSungaiService.findById(wsId));
        mav.addObject("year", yearService.findById(yearId));
        mav.addObject("activity", activityService.findById(activityId));

        if (documentId != null) {
            mav.addObject("document", activityDocumentService.findById(documentId));
        }

        return mav;
    }

    @RequestMapping(value = "add/{ws}/{year}/{activity}.html", method = RequestMethod.POST)
    public ModelAndView addDocument(@PathVariable(value = "ws") Long wsId,
                                    @PathVariable(value = "year") Long yearId,
                                    @PathVariable(value = "activity") Long activityId,
                                    @ModelAttribute("form") DocumentForm form,
                                    Errors errors) throws IOException {
        validateDocumentForm(form, errors, false);
        if (!errors.hasErrors()) {
            ActivityDocument document = new ActivityDocument();
            document.setName(form.getName());
            document.setIndex(form.getIndex());

            document.setActive(true);
            document.setActivity(activityService.findById(activityId));

            document.setFilename(form.getFile().getOriginalFilename());

            activityDocumentService.save(document, form.getFile().getInputStream());

            return createRedirectDocumentView(wsId, yearId, activityId);
        }

        return createDocumentFormView(wsId, yearId, activityId, null);
    }

    private ModelAndView createRedirectDocumentView(Long wsId, Long yearId, Long activityId) {
        return new ModelAndView(String.format("redirect:/admin/tkpsda/hasil_sidang/view/%d/%d/%d.html", wsId, yearId, activityId));
    }

    private void validateDocumentForm(DocumentForm form, Errors errors, boolean allowFileEmpty) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama dokumen belum diisi");
        }

        if (allowFileEmpty == false) {
            if (form.getFile() == null || form.getFile().isEmpty()) {
                errors.reject("file.empty", "File belum diisi");
            }
        }
    }

    @RequestMapping(value = "edit/{ws}/{year}/{activity}/{document}.html", method = RequestMethod.GET)
    public ModelAndView editDocument(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable(value = "activity") Long activityId,
                                     @PathVariable(value = "document") Long documentId,
                                     @ModelAttribute("form") DocumentForm form) {
        ActivityDocument document = activityDocumentService.findById(documentId);
        form.setName(document.getName());
        form.setIndex(document.getIndex());

        return createDocumentFormView(wsId, yearId, activityId, documentId);
    }

    @RequestMapping(value = "edit/{ws}/{year}/{activity}/{document}.html", method = RequestMethod.POST)
    public ModelAndView editDocument(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable(value = "activity") Long activityId,
                                     @PathVariable(value = "document") Long documentId,
                                     @ModelAttribute("form") DocumentForm form,
                                     Errors errors) throws IOException {
        validateDocumentForm(form, errors, true);
        if (!errors.hasErrors()) {
            ActivityDocument document = activityDocumentService.findById(documentId);
            document.setName(form.getName());
            document.setIndex(form.getIndex());

            if (form.getFile() != null && !form.getFile().isEmpty()) {
                document.setFilename(form.getFile().getOriginalFilename());
                activityDocumentService.save(document, form.getFile().getInputStream());
            } else {
                activityDocumentService.save(document);
            }


            return createRedirectDocumentView(wsId, yearId, activityId);
        }

        return createDocumentFormView(wsId, yearId, activityId, null);
    }

    @RequestMapping(value = "remove/{ws}/{year}/{activity}.html", method = RequestMethod.POST)
    public ModelAndView removeDocument(@PathVariable(value = "ws") Long wsId,
                                       @PathVariable(value = "year") Long yearId,
                                       @PathVariable(value = "activity") Long activityId,
                                       @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            activityDocumentService.removeByIds(ids);
        }
        return createRedirectDocumentView(wsId, yearId, activityId);
    }
}
