package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.NotificationEmail;
import com.integrasolusi.pusda.sisda.service.NotificationEmailService;
import com.integrasolusi.pusda.sisda.web.form.NotificationEmailForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Programmer : pancara
 * Date       : 4/15/13
 * Time       : 9:47 PM
 */

@Controller("adminNotificationEmailController")
@RequestMapping("/admin/comment/guest/notificationemail/")
public class NotificationEmailController {
    @Autowired
    private NotificationEmailService notificationEmailService;


    @RequestMapping(value = "list.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("admin/comment/guest/notificationemail/list");
        mav.addObject("count", notificationEmailService.countAlls());
        mav.addObject("emails", notificationEmailService.findAlls());
        return mav;
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView createNew(@ModelAttribute("form") NotificationEmailForm form) throws IOException {
        return createFormView();
    }

    private ModelAndView createFormView() {
        return new ModelAndView("admin/comment/guest/notificationemail/form");
    }

    private ModelAndView createRedirectView() {
        return new ModelAndView("redirect:/admin/comment/guest/notificationemail/list.html");
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView createNew(@ModelAttribute("form") NotificationEmailForm form,
                                  Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            try {
                NotificationEmail email = new NotificationEmail();
                email.setAddress(form.getAddress());
                email.setName(form.getName());

                notificationEmailService.save(email);
                return createRedirectView();
            } catch (Exception e) {

            }
        }
        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id,
                             @ModelAttribute("form") NotificationEmailForm form) throws IOException {

        NotificationEmail email = notificationEmailService.findById(id);
        if (email == null) {
            return createRedirectView();
        }

        ModelAndView mav = createFormView();
        form.setAddress(email.getAddress());
        form.setName(email.getName());

        return mav;
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id,
                             @ModelAttribute("form") NotificationEmailForm form,
                             Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            try {
                NotificationEmail email = notificationEmailService.findById(id);
                email.setAddress(form.getAddress());
                email.setName(form.getName());

                notificationEmailService.save(email);
                return createRedirectView();
            } catch (Exception e) {

            }
        }
        return createFormView();
    }

    private void validateForm(NotificationEmailForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getAddress())) {
            errors.reject("address.empty", "Alamat email belum diisi");
        }

        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }
    }

    @RequestMapping(value = "manage.html")
    public ModelAndView manage(@RequestParam(value = "ids", required = false) Long[] ids) {
        return createRedirectView();
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "ids", required = false) Long[] ids) {
        if (ids != null) {
            notificationEmailService.removeByIds(ids);
        }
        return createRedirectView();

    }

}
