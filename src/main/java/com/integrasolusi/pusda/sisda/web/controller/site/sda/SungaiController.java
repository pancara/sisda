package com.integrasolusi.pusda.sisda.web.controller.site.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.sungai.Sungai;
import com.integrasolusi.pusda.sisda.persistence.sda.sungai.SungaiSummary;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.sungai.SungaiService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.sungai.SungaiSummaryService;
import com.integrasolusi.utils.ContentTypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 10:44 AM
 */

@Controller
public class SungaiController {
    private static final Logger logger = LoggerFactory.getLogger(SungaiController.class);

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private DasService dasService;

    @Autowired
    private SungaiService sungaiService;

    @Autowired
    private SungaiSummaryService sungaiSummaryService;


    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("/sda/sungai.html")
    public ModelAndView list(HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("site/sda/sungai/index");
        Map<WilayahSungai, Map> mapWs = new LinkedHashMap<WilayahSungai, Map>();
        Map<WilayahSungai, SungaiSummary> mapSummary = new HashMap<>();

        for (WilayahSungai ws : wilayahSungaiService.findAlls()) {
            Map<Das, List> mapDas = new LinkedHashMap<>();
            for (Das das : dasService.findByWilayahSungai(ws)) {
                mapDas.put(das, sungaiService.findByDas(das));
            }

            mapWs.put(ws, mapDas);

            mapSummary.put(ws, sungaiSummaryService.findByWilayahSungai(ws));
        }

        mav.addObject("mapWs", mapWs);
        mav.addObject("mapSummary", mapSummary);
        return mav;
    }

    @RequestMapping("/sda/sungai/{id}/**")
    public void downloadSungai(@PathVariable("id") Long id, HttpServletResponse response) {
        Sungai sungai = sungaiService.findById(id);
        response.setContentType(contentTypeUtils.getContentType(sungai.getFilename()));
        try {
            sungaiService.getBlob(id, response.getOutputStream());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }

    @RequestMapping("/sda/sungai/summary/{id}/**")
    public void downloadSummary(@PathVariable("id") Long id, HttpServletResponse response) {
        SungaiSummary summary = sungaiSummaryService.findById(id);
        response.setContentType(contentTypeUtils.getContentType(summary.getFilename()));
        try {
            sungaiSummaryService.getBlob(id, response.getOutputStream());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }

}
