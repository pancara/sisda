package com.integrasolusi.pusda.sisda.web.controller.site.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosKlimatologi;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.SdaService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.hidrologi.KlimatologiService;
import com.integrasolusi.pusda.sisda.service.sda.hidrologi.PosKlimatologiService;
import com.integrasolusi.utils.ContentTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 8:48 PM
 */

@Controller
public class KlimatologiController {
    @Autowired
    private SdaService sdaService;

    @Autowired
    private PosKlimatologiService posKlimatologiService;

    @Autowired
    private KlimatologiService klimatologiService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private DasService dasService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping("/sda/hidrologi/ klimatologi.html")
    public ModelAndView intro() {
        ModelAndView mav = new ModelAndView("site/sda/hidrologi/klimatologi/index");

        Map<WilayahSungai, List> mapWilayahSungai = new LinkedHashMap<>();
        for (WilayahSungai ws : wilayahSungaiService.findAlls()) {
            mapWilayahSungai.put(ws, posKlimatologiService.findByWs(ws));
        }

        mav.addObject("mapWilayahSungai", mapWilayahSungai);

        return mav;
    }

    
    @RequestMapping("/sda/hidrologi/klimatologi/{id}/**")
    public void downloadPos(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        PosKlimatologi pos = posKlimatologiService.findById(id);
        if (pos == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(pos.getFilename()));
        posKlimatologiService.getBlob(id, response.getOutputStream());
    }


}
