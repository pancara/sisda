package com.integrasolusi.pusda.sisda.web.controller.site.sda;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 10:45 AM
 */
@Controller
public class EmbungTelagaController {

    @RequestMapping("/sda/embung_telaga.html")
    public ModelAndView list() {
        return new ModelAndView("site/sda/embung_telaga");
    }

}
