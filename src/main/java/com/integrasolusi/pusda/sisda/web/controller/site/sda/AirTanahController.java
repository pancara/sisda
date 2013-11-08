package com.integrasolusi.pusda.sisda.web.controller.site.sda;

import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.AirTanah;
import com.integrasolusi.pusda.sisda.service.sda.AirTanahService;
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
public class AirTanahController {
    private static final Logger logger = LoggerFactory.getLogger(AirTanahController.class);

    @Autowired
    private AirTanahService airTanahService;


    @Autowired
    private WilayahSungaiService wilayahSungaiService;


    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("air_tanah.html")
    public ModelAndView list(HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("site/sda/air_tanah/intro");
        List<WilayahSungai> wsList = wilayahSungaiService.findAlls();
        Map<WilayahSungai, List<AirTanah>> dataAirTanah = new LinkedHashMap<WilayahSungai, List<AirTanah>>();
        for (WilayahSungai ws : wsList) {
            List<AirTanah> data = airTanahService.findByWilayahSungai(ws);
            if (data.size() > 0)
                dataAirTanah.put(ws, data);
        }
        mav.addObject("dataAirTanah", dataAirTanah);
        return mav;
    }

    @RequestMapping("air_tanah/{id}/**")
    public void downloadBendung(@PathVariable("id") Long id, HttpServletResponse response) {
        AirTanah airTanah = airTanahService.findById(id);
        response.setContentType(contentTypeUtils.getContentType(airTanah.getFilename()));
        try {
            airTanahService.getBlob(id, response.getOutputStream());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }


}
