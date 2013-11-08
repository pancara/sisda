package com.integrasolusi.pusda.sisda.web.controller.site.sda.banjir;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.banjir.Banjir;
import com.integrasolusi.pusda.sisda.persistence.sda.banjir.DaerahRawanBanjir;
import com.integrasolusi.pusda.sisda.service.*;
import com.integrasolusi.pusda.sisda.service.sda.*;
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
 * Date: 7/31/12
 * Time: 10:46 AM
 */

@Controller
public class BanjirController {
    private BanjirService banjirService;
    private DaerahRawanBanjirService daerahRawanBanjirService;
    private SdaService sdaService;
    private WilayahSungaiService wilayahSungaiService;
    private DasService dasService;
    private KabupatenService kabupatenService;

    private ContentTypeUtils contentTypeUtils;

    @Autowired
    public void setBanjirService(BanjirService banjirService) {
        this.banjirService = banjirService;
    }

    @Autowired
    public void setDaerahRawanBanjirService(DaerahRawanBanjirService daerahRawanBanjirService) {
        this.daerahRawanBanjirService = daerahRawanBanjirService;
    }

    @Autowired
    public void setSdaService(SdaService sdaService) {
        this.sdaService = sdaService;
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

    @RequestMapping("/sda/banjir.html")
    public ModelAndView list() {
        return new ModelAndView("site/sda/banjir/intro");
    }


    @RequestMapping("/sda/banjir/data_banjir.html")
    public ModelAndView dataBanjir() {
        ModelAndView mav = new ModelAndView("site/sda/banjir/data_banjir");

        Map<WilayahSungai, Map> banjirData = new LinkedHashMap<WilayahSungai, Map>();

        List<WilayahSungai> wilayahSungaiList = wilayahSungaiService.findAlls();
        for (WilayahSungai wilayahSungai : wilayahSungaiList) {
            List<Das> dasList = dasService.findByWilayahSungai(wilayahSungai);
            Map<Das, List> dasMap = new LinkedHashMap();
            for (Das das : dasList) {
                List<Banjir> banjirList = banjirService.findByDas(das);
                if (banjirList.size() > 0) {
                    dasMap.put(das, banjirList);
                }
            }

            banjirData.put(wilayahSungai, dasMap);
        }

        mav.addObject("banjirData", banjirData);

        return mav;
    }

    @RequestMapping("/sda/banjir/data_banjir/{id}/**")
    public void downloadDataBanjir(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Banjir banjir = banjirService.findById(id);
        if (banjir == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(banjir.getFilename()));
        banjirService.getBlob(id, response.getOutputStream());
    }

    @RequestMapping("/sda/banjir/daerah_rawan_banjir.html")
    public ModelAndView daerahRawanBanjir() {
        ModelAndView mav = new ModelAndView("site/sda/banjir/daerah_rawan_banjir");

        Map<WilayahSungai, Map> drbData = new LinkedHashMap<WilayahSungai, Map>();

        List<WilayahSungai> wsList = wilayahSungaiService.findAlls();
        for (WilayahSungai ws : wsList) {
            List<Das> dasList = dasService.findByWilayahSungai(ws);
            Map<Das, List> dasMap = new LinkedHashMap();

            for (Das das : dasList) {
                List<DaerahRawanBanjir> daerahRawanBanjirList = daerahRawanBanjirService.findByDas(das);
                if (daerahRawanBanjirList.size() > 0) {
                    dasMap.put(das, daerahRawanBanjirList);
                }
            }

            drbData.put(ws, dasMap);
        }

        mav.addObject("drbData", drbData);

        return mav;
    }

    @RequestMapping("/sda/banjir/daerah_rawan_banjir/{id}/**")
    public void downloadDaerahRawanBanjir(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        DaerahRawanBanjir drb = daerahRawanBanjirService.findById(id);
        if (drb == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(drb.getFilename()));
        daerahRawanBanjirService.getBlob(id, response.getOutputStream());
    }

}
