package com.integrasolusi.pusda.sisda.web.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Programmer : pancara
 * Date       : 10/25/13
 * Time       : 8:00 AM
 */

@Controller
public class ProfileController {

    @RequestMapping(value = "/profile.html")
    public ModelAndView about() {
        return new ModelAndView("site/profile");
    }



}
