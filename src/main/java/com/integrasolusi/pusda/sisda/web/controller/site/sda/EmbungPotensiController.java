package com.integrasolusi.pusda.sisda.web.controller.site.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.EmbungPotensi;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.EmbungPotensiService;
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
public class EmbungPotensiController {
    private WilayahSungaiService wilayahSungaiService;
    private DasService dasService;
    private EmbungPotensiService embungPotensiService;

    @Autowired
    public void setEmbungPotensiService(EmbungPotensiService embungPotensiService) {
        this.embungPotensiService = embungPotensiService;
    }

    @Autowired
    public void setWilayahSungaiService(WilayahSungaiService wilayahSungaiService) {
        this.wilayahSungaiService = wilayahSungaiService;
    }

    @Autowired
    public void setDasService(DasService dasService) {
        this.dasService = dasService;
    }

    @RequestMapping("/sda/embung_potensi.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("site/sda/embung_potensi/intro");

        Map<WilayahSungai, Map> embungData = new LinkedHashMap<WilayahSungai, Map>();
        List<WilayahSungai> wilayahSungaiList = wilayahSungaiService.findAlls();
        for (WilayahSungai ws : wilayahSungaiList) {
            List<Das> dasList = dasService.findByWilayahSungai(ws);
            Map<Das, List> dasData = new LinkedHashMap<>();
            for (Das das : dasList) {
                List<EmbungPotensi> embungList = embungPotensiService.findByDas(das);
                if (embungList.size() > 0) {
                    dasData.put(das, embungList);
                }
            }
            embungData.put(ws, dasData);
        }

        mav.addObject("embungData", embungData);
        return mav;
    }

    @RequestMapping("/popup/sda/embung_potensi/detail/{id}/**.html")
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("site/sda/embung_potensi/detail");
        EmbungPotensi embung = embungPotensiService.findById(id);
        mav.addObject("embung", embung);
        return mav;
    }
}
