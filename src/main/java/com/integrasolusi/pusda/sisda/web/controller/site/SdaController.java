package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.Sda;
import com.integrasolusi.pusda.sisda.service.sda.SdaService;
import com.integrasolusi.pusda.sisda.web.utils.SdaForwardPathResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 1:57 PM
 */
@Controller
public class SdaController {
    private SdaService sdaService;
    private SdaForwardPathResolver sdaForwardPathResolver;

    @Autowired
    public void setSdaService(SdaService sdaService) {
        this.sdaService = sdaService;
    }

    @Autowired
    @Qualifier("sdaForwardPathResolver")
    public void setSdaForwardPathResolver(SdaForwardPathResolver sdaForwardPathResolver) {
        this.sdaForwardPathResolver = sdaForwardPathResolver;
    }

    @RequestMapping("/sda.html")
    public ModelAndView sda() {
        ModelAndView mav = new ModelAndView("site/sda");
        List<Sda> listSda = sdaService.findActive();
        mav.addObject("listSda", listSda);
        return mav;
    }

    @RequestMapping("/sda/view/{sdaID}/{sdaName}.html")
    public ModelAndView sda(@PathVariable("sdaID") Long id) {
        if (id == null) {
            return sda();
        }
        Sda sda = sdaService.findById(id);
        if (sda == null) {
            return sda();
        }

        String forwardUrl = sdaForwardPathResolver.getForwardPath(sda);
        return new ModelAndView("forward:/" + forwardUrl);
    }
}
