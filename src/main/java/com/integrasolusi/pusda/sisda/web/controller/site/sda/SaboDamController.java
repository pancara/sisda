package com.integrasolusi.pusda.sisda.web.controller.site.sda;

import com.integrasolusi.pusda.sisda.persistence.region.SungaiSaboDam;
import com.integrasolusi.pusda.sisda.persistence.sda.SaboDam;
import com.integrasolusi.pusda.sisda.service.sda.SaboDamService;
import com.integrasolusi.pusda.sisda.service.sda.SungaiSaboDamService;
import com.integrasolusi.utils.ContentTypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Time: 10:45 AM
 */

@Controller
public class SaboDamController {
    private Logger logger = LoggerFactory.getLogger(SaboDamController.class);

    private SungaiSaboDamService sungaiSaboDamService;
    private SaboDamService saboDamService;
    private ContentTypeUtils contentTypeUtils;


    @Autowired
    public void setSungaiSaboDamService(SungaiSaboDamService sungaiSaboDamService) {
        this.sungaiSaboDamService = sungaiSaboDamService;
    }

    @Autowired
    public void setSaboDamService(SaboDamService saboDamService) {
        this.saboDamService = saboDamService;
    }

    @Autowired
    public void setContentTypeUtils(ContentTypeUtils contentTypeUtils) {
        this.contentTypeUtils = contentTypeUtils;
    }

    @RequestMapping("/sda/sabodam.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("site/sda/sabodam/intro");
        List<SungaiSaboDam> sungaiList = sungaiSaboDamService.findAlls();
        Map<SungaiSaboDam, List<SaboDam>> saboDamData = new LinkedHashMap<SungaiSaboDam, List<SaboDam>>();
        for (SungaiSaboDam sungai : sungaiList) {
            saboDamData.put(sungai, saboDamService.findBySungai(sungai));
        }
        mav.addObject("saboDamData", saboDamData);
        return mav;
    }

    @RequestMapping("/popup/sda/sabodam/map/view/{sungaiID}.html")
    public ModelAndView map(@PathVariable("sungaiID") Long id) {
        ModelAndView mav = new ModelAndView("site/sda/sabodam/map");
        SungaiSaboDam sungai = sungaiSaboDamService.findById(id);
        mav.addObject("sungai", sungai);
        return mav;
    }


    @RequestMapping("/popup/sda/sabodam/view/{sabodamID}.html")
    public ModelAndView view(@PathVariable("sabodamID") Long id) {
        ModelAndView mav = new ModelAndView("site/sda/sabodam/view");
        SaboDam sabodam = saboDamService.findById(id);
        mav.addObject("sabodam", sabodam);
        return mav;
    }

    @RequestMapping("/sda/sabodam/map/sungai/{id}/**")
    public void downloadMap(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        SungaiSaboDam sungaiSaboDam = sungaiSaboDamService.findById(id);
        String contentType = contentTypeUtils.getContentType(sungaiSaboDam.getFilename());
        response.setContentType(contentType);

        try {
            sungaiSaboDamService.getMapBlob(id, response.getOutputStream());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }

    @RequestMapping("/sda/sabodam/{id}/**")
    public void downloadDocument(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        SaboDam saboDam = saboDamService.findById(id);
        String contentType = contentTypeUtils.getContentType(saboDam.getFilename());
        response.setContentType(contentType);

        saboDamService.getBlob(id, response.getOutputStream());
    }

}
