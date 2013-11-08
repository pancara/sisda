package com.integrasolusi.pusda.sisda.web.controller.site.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.Telaga;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.TelagaService;
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
public class TelagaController {
    private WilayahSungaiService wilayahSungaiService;
    private DasService dasService;
    private TelagaService telagaService;

    @Autowired
    public void setTelagaService(TelagaService telagaService) {
        this.telagaService = telagaService;
    }

    @Autowired
    public void setWilayahSungaiService(WilayahSungaiService wilayahSungaiService) {
        this.wilayahSungaiService = wilayahSungaiService;
    }

    @Autowired
    public void setDasService(DasService dasService) {
        this.dasService = dasService;
    }

    @RequestMapping("/sda/telaga.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("site/sda/telaga/intro");

        Map<WilayahSungai, Map> telagaData = new LinkedHashMap<WilayahSungai, Map>();
        List<WilayahSungai> wilayahSungaiList = wilayahSungaiService.findAlls();
        for (WilayahSungai ws : wilayahSungaiList) {
            List<Das> dasList = dasService.findByWilayahSungai(ws);
            Map<Das, List> dasData = new LinkedHashMap<>();
            for (Das das : dasList) {
                List<Telaga> telagaList = telagaService.findByDas(das);
                if (telagaList.size() > 0) {
                    dasData.put(das, telagaList);
                }
            }
            telagaData.put(ws, dasData);
        }

        mav.addObject("telagaData", telagaData);
        return mav;
    }

    @RequestMapping("/popup/sda/telaga/detail/{id}/**.html")
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("site/sda/telaga/detail");
        Telaga telaga = telagaService.findById(id);
        mav.addObject("telaga", telaga);
        return mav;
    }
}
