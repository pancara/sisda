package com.integrasolusi.pusda.sisda.web.controller.site.sda;

import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.SdaDas;
import com.integrasolusi.pusda.sisda.service.sda.SdaDasService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
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
public class SdaDasController {
    private SdaDasService sdaDasService;
    private WilayahSungaiService wilayahSungaiService;
    private ContentTypeUtils contentTypeUtils;

    @Autowired
    public void setSdaDasService(SdaDasService sdaDasService) {
        this.sdaDasService = sdaDasService;
    }

    @Autowired
    public void setWilayahSungaiService(WilayahSungaiService wilayahSungaiService) {
        this.wilayahSungaiService = wilayahSungaiService;
    }

    @Autowired
    public void setContentTypeUtils(ContentTypeUtils contentTypeUtils) {
        this.contentTypeUtils = contentTypeUtils;
    }

    @RequestMapping("/sda/das.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("site/sda/das/intro");

        List<WilayahSungai> wilayahSungaiList = wilayahSungaiService.findAlls();
        Map<WilayahSungai, List<SdaDas>> dasData = new LinkedHashMap<WilayahSungai, List<SdaDas>>();

        for (WilayahSungai ws : wilayahSungaiList) {
            List<SdaDas> dasList = sdaDasService.findByWilayahSungai(ws);
            dasData.put(ws, dasList);
        }

        mav.addObject("dasData", dasData);

        return mav;
    }

    @RequestMapping("/sda/das/{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        SdaDas das = sdaDasService.findById(id);
        if (das == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(das.getFilename()));
        sdaDasService.getBlob(id, response.getOutputStream());
    }

}
