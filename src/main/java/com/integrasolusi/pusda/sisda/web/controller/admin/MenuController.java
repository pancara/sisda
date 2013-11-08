package com.integrasolusi.pusda.sisda.web.controller.admin;

/**
 * Programmer   : pancara
 * Date         : 6/12/11
 * Time         : 9:25 AM
 */

import com.integrasolusi.pusda.sisda.service.sda.SdaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller("adminMenuController")
@RequestMapping("/admin")
public class MenuController {
    @Autowired
    private SdaService sdaService;

    @RequestMapping("menu")
    public ModelAndView menu() throws IOException {
        return new ModelAndView("admin/menu");
    }

}
