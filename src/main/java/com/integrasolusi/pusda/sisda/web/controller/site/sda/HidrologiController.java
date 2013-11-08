package com.integrasolusi.pusda.sisda.web.controller.site.sda;

import com.integrasolusi.pusda.sisda.service.sda.SdaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 10:46 AM
 */

@Controller
public class HidrologiController {
    private SdaService sdaService;

    @Autowired
    public void setSdaService(SdaService sdaService) {
        this.sdaService = sdaService;
    }

    @RequestMapping("/sda/hidrologi.html")
    public ModelAndView list() {
        return new ModelAndView("site/sda/hidrologi/intro");
    }

}
