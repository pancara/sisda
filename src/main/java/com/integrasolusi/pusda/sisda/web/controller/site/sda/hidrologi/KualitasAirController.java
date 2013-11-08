package com.integrasolusi.pusda.sisda.web.controller.site.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.KualitasAir;
import com.integrasolusi.pusda.sisda.service.sda.*;
import com.integrasolusi.pusda.sisda.service.sda.hidrologi.KualitasAirService;
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
public class KualitasAirController {
    private SdaService sdaService;
    private KualitasAirService kualitasAirService;
    private WilayahSungaiService wilayahSungaiService;
    private DasService dasService;
    private KabupatenService kabupatenService;

    private ContentTypeUtils contentTypeUtils;

    @Autowired
    public void setSdaService(SdaService sdaService) {
        this.sdaService = sdaService;
    }

    @Autowired
    public void setKualitasAirService(KualitasAirService kualitasAirService) {
        this.kualitasAirService = kualitasAirService;
    }

    @Autowired
    public void setWilayahSungaiService(WilayahSungaiService wilayahSungaiService) {
        this.wilayahSungaiService = wilayahSungaiService;
    }

    @Autowired
    public void setDasService(DasService dasService) {
        this.dasService = dasService;
    }

    @Autowired
    public void setKabupatenService(KabupatenService kabupatenService) {
        this.kabupatenService = kabupatenService;
    }

    @Autowired
    public void setContentTypeUtils(ContentTypeUtils contentTypeUtils) {
        this.contentTypeUtils = contentTypeUtils;
    }

    @RequestMapping("/sda/hidrologi/kualitas_air.html")
    public ModelAndView intro() {
        ModelAndView mav = new ModelAndView("site/sda/hidrologi/kualitas_air/intro");

        Map<WilayahSungai, Map> mapWilayahSungai = new LinkedHashMap<WilayahSungai, Map>();
        List<WilayahSungai> wilayahSungaiList = wilayahSungaiService.findAlls();
        
        for (WilayahSungai wilayahSungai : wilayahSungaiList) {
            List<Das> dasList = dasService.findByWilayahSungai(wilayahSungai);
            Map<Das, List> mapDas = new LinkedHashMap();
            for (Das das : dasList) {
                mapDas.put(das, kualitasAirService.findByDas(das));
            }
            mapWilayahSungai.put(wilayahSungai, mapDas);
        }

        mav.addObject("mapWilayahSungai", mapWilayahSungai);

        return mav;
    }

    @RequestMapping("/sda/hidrologi/kualitas_air/{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        KualitasAir kualitasAir = kualitasAirService.findById(id);
        if (kualitasAir == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(kualitasAir.getFilename()));
        kualitasAirService.getBlob(id, response.getOutputStream());
    }

}
