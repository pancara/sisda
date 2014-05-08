package com.integrasolusi.pusda.sisda.web.controller.site.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.TinggiMukaAir;
import com.integrasolusi.pusda.sisda.service.sda.*;
import com.integrasolusi.pusda.sisda.service.sda.hidrologi.TinggiMukaAirService;
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
public class TinggiMukaAirController {

    @Autowired
    private TinggiMukaAirService tinggiMukaAirService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private DasService dasService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("/sda/hidrologi/tinggi_muka_air.html")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("site/sda/hidrologi/tinggi_muka_air/index");

        Map<WilayahSungai, Map> mapWs = new LinkedHashMap<>();

        List<WilayahSungai> listWs = wilayahSungaiService.findAlls();
        for (WilayahSungai ws : listWs) {
            Map<Das, List> mapDas = new LinkedHashMap<>();
            for (Das das : dasService.findByWilayahSungai(ws)) {
                mapDas.put(das, tinggiMukaAirService.findByDas(das));
            }

            mapWs.put(ws, mapDas);
        }

        mav.addObject("mapWs", mapWs);

        return mav;
    }

    @RequestMapping("/sda/hidrologi/tinggi_muka_air/{id}/**")
    public void downloadTMA(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        TinggiMukaAir tma = tinggiMukaAirService.findById(id);
        if (tma == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(tma.getFilename()));
        tinggiMukaAirService.getBlob(id, response.getOutputStream());
    }


}
