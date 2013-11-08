package com.integrasolusi.pusda.sisda.web.controller.site.sda;

import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.bendung.Bendung;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.bendung.BendungService;
import com.integrasolusi.pusda.sisda.service.sda.bendung.BendungSummaryService;
import com.integrasolusi.utils.ContentTypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 10:44 AM
 */

@Controller
public class BendungController {
    private static final Logger logger = LoggerFactory.getLogger(BendungController.class);

    @Autowired
    private WilayahSungaiService wilayahBendungService;

    @Autowired
    private DasService dasService;

    @Autowired
    private BendungService bendungService;

    @Autowired
    private BendungSummaryService bendungSummaryService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("/sda/bendung.html")
    public ModelAndView list(HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("site/sda/bendung/index");
        Map<WilayahSungai, List<Bendung>> mapWs = new LinkedHashMap<>();

        for (WilayahSungai ws : wilayahBendungService.findAlls()) {
            mapWs.put(ws, bendungService.findByWs(ws));
        }

        mav.addObject("mapWs", mapWs);
        return mav;
    }

    @RequestMapping("/sda/bendung/{id}/**")
    public void downloadBendung(@PathVariable("id") Long id, HttpServletResponse response) {
        Bendung bendung = bendungService.findById(id);
        response.setContentType(contentTypeUtils.getContentType(bendung.getFilename()));
        try {
            bendungService.getBlob(id, response.getOutputStream());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }


}
