package com.integrasolusi.pusda.sisda.web.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 12:07 PM
 */
@Controller
public class WilayahKerjaController {

    @RequestMapping("/wilayah_kerja.html")
    public ModelAndView wilayahKerja() {
        return new ModelAndView("site/wilayah_kerja");
    }
}
