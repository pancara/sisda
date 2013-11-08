package com.integrasolusi.pusda.sisda.web.controller.site.tkpsda;

import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: pancara
 * Date: 8/13/12
 * Time: 1:05 AM
 */
@Controller
public class TkpsdaController {
    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @RequestMapping("/tkpsda.html")
    public ModelAndView intro() {
        ModelAndView mav = new ModelAndView("site/tkpsda/intro");
        mav.addObject("wilayahSungaiList", wilayahSungaiService.findAlls());
        return mav;
    }

}
