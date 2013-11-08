package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.Sda;
import com.integrasolusi.pusda.sisda.service.sda.SdaService;
import com.integrasolusi.pusda.sisda.web.utils.SdaForwardPathResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 1:57 PM
 */
@Controller("adminSdaController")
public class SdaController {
    private SdaService sdaService;
    private SdaForwardPathResolver sdaForwardPathResolver;

    @Autowired
    public void setSdaService(SdaService sdaService) {
        this.sdaService = sdaService;
    }

    @Autowired
    @Qualifier("adminSdaForwardPathResolver")
    public void setSdaForwardPathResolver(SdaForwardPathResolver sdaForwardPathResolver) {
        this.sdaForwardPathResolver = sdaForwardPathResolver;
    }


    @RequestMapping("/admin/sda/{sdaID}/{sdaName}.html")
    public ModelAndView sda(@PathVariable("sdaID") Long id, @PathVariable("sdaName") String sdaName) {
        Sda sda = sdaService.findById(id);

        String forwardUrl = sdaForwardPathResolver.getForwardPath(sda);
        return new ModelAndView("forward:/" + forwardUrl);
    }
}
