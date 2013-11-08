package com.integrasolusi.pusda.sisda.web.controller.site.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.Embung;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.EmbungService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 10:45 AM
 */
@Controller
public class EmbungController {
    private WilayahSungaiService wilayahSungaiService;
    private DasService dasService;
    private EmbungService embungService;

    @Autowired
    public void setEmbungService(EmbungService embungService) {
        this.embungService = embungService;
    }

    @Autowired
    public void setWilayahSungaiService(WilayahSungaiService wilayahSungaiService) {
        this.wilayahSungaiService = wilayahSungaiService;
    }

    @Autowired
    public void setDasService(DasService dasService) {
        this.dasService = dasService;
    }

    @RequestMapping("/sda/embung.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("site/sda/embung/intro");

        Map<WilayahSungai, Map> mapWs = new LinkedHashMap<WilayahSungai, Map>();
        for (WilayahSungai ws : wilayahSungaiService.findAlls()) {
            List<Das> dasList = dasService.findByWilayahSungai(ws);
            Map<Das, List> dasData = new LinkedHashMap<>();
            for (Das das : dasList) {
                dasData.put(das, embungService.findByDas(das));
            }
            mapWs.put(ws, dasData);
        }

        mav.addObject("mapWs", mapWs);
        return mav;
    }

    @RequestMapping("/popup/sda/embung/detail/{id}/**.html")
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("site/sda/embung/detail");
        Embung embung = embungService.findById(id);
        mav.addObject("embung", embung);
        return mav;
    }
}
