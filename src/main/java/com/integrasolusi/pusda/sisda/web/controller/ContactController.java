package com.integrasolusi.pusda.sisda.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Programmer   : pancara
 * Date         : 6/11/11
 * Time         : 6:51 PM
 */
@Controller
public class ContactController {
    @RequestMapping(value = "contact")
    public ModelAndView home(HttpServletRequest request) {
        return new ModelAndView("contact");
    }
}
