package com.integrasolusi.pusda.sisda.web.controller.site;

/**
 * Programmer   : pancara
 * Date         : 6/12/11
 * Time         : 9:25 AM
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MenuController {

    @RequestMapping("/menu")
    public ModelAndView menu() {
        return new ModelAndView("site/menu/menu");
    }

    @RequestMapping("/menubar")
    public ModelAndView menuBar() {
        return new ModelAndView("site/menu/menubar");
    }

}
