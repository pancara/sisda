package com.integrasolusi.pusda.sisda.web.controller.site.sda;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.Kekeringan;
import com.integrasolusi.pusda.sisda.service.sda.KekeringanService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.utils.ContentTypeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 10:45 AM
 */

@Controller
@RequestMapping("/sda")
public class KekeringanController {
    private static final Logger logger = LoggerFactory.getLogger(KekeringanController.class);

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private KekeringanService kekeringanService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("kekeringan.html")
    public ModelAndView list(HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("site/sda/kekeringan/intro");
        List<WilayahSungai> wsList = wilayahSungaiService.findAlls();
        Map<WilayahSungai, Map> dataKekeringan = new LinkedHashMap<WilayahSungai, Map>();
        for (WilayahSungai ws : wsList) {
            List<Year> yearList = kekeringanService.findYearByWilayahSungai(ws);
            Map<Year, List> dataPerYear = new LinkedHashMap<Year, List>();
            for (Year year : yearList) {
                List<Kekeringan> data = kekeringanService.findByWilayahSungaiAndYear(ws, year);
                dataPerYear.put(year, data);
            }
            dataKekeringan.put(ws, dataPerYear);
        }
        mav.addObject("dataKekeringan", dataKekeringan);
        return mav;
    }

    @RequestMapping("kekeringan/{id}/**")
    public void downloadBendung(@PathVariable("id") Long id, HttpServletResponse response) {
        Kekeringan kekeringan = kekeringanService.findById(id);
        response.setContentType(contentTypeUtils.getContentType(kekeringan.getFilename()));
        try {
            kekeringanService.getBlob(id, response.getOutputStream());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }

}
