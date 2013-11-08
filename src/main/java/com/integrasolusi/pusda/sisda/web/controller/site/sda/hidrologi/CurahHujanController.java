package com.integrasolusi.pusda.sisda.web.controller.site.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosCH;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.hidrologi.PosCHService;
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
public class CurahHujanController {

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private PosCHService posCHService;


    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping("/sda/hidrologi/curah_hujan.html")
    public ModelAndView intro() {
        ModelAndView mav = new ModelAndView("site/sda/hidrologi/curah_hujan/index");

        Map<WilayahSungai, List> mapWilayahSungai = new LinkedHashMap<>();
        for (WilayahSungai ws : wilayahSungaiService.findAlls()) {
            mapWilayahSungai.put(ws, posCHService.findByWs(ws));
        }

        mav.addObject("mapWilayahSungai", mapWilayahSungai);

        return mav;
    }

    @RequestMapping("/sda/hidrologi/curah_hujan/{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        PosCH posCH = posCHService.findById(id);
        if (posCH == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(posCH.getFilename()));
        posCHService.getBlob(id, response.getOutputStream());
    }

}
