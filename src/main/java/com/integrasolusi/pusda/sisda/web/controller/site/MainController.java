package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.service.LeafletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * User: pancara
 * Date: 7/26/12
 * Time: 12:13 PM
 */
@Controller
public class MainController {
    @Autowired
    private LeafletService leafletService;

    @Value(value = "${latest.leaflet.count}")
    private Long latestCount;


    @RequestMapping(value = "/visi_misi")
    public ModelAndView visiMisi() {
        return new ModelAndView("site/visi_misi");
    }

}
