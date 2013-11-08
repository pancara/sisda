package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.service.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Programmer : pancara
 * Date       : 11/19/12
 * Time       : 3:53 PM
 */
@Controller
public class TickerController {
    @Autowired
    private TickerService tickerService;

    @RequestMapping("/ticker.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("site/ticker");
        mav.addObject("tickerList", tickerService.findPublished(0L, 10L));
        return mav;
    }
}
