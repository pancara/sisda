package com.integrasolusi.pusda.sisda.web.controller;

import com.integrasolusi.pusda.sisda.persistence.region.Kabupaten;
import com.integrasolusi.pusda.sisda.persistence.region.Propinsi;
import com.integrasolusi.pusda.sisda.service.sda.KabupatenService;
import com.integrasolusi.pusda.sisda.service.sda.PropinsiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import java.util.List;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 9:08 PM
 */
@Controller
public class RegionController {
    private PropinsiService propinsiService;
    private KabupatenService kabupatenService;

    @Autowired
    public void setPropinsiService(PropinsiService propinsiService) {
        this.propinsiService = propinsiService;
    }

    @Autowired
    public void setKabupatenService(KabupatenService kabupatenService) {
        this.kabupatenService = kabupatenService;
    }

    @RequestMapping("/propinsi/{propinsiId}/collection/kabupaten")
    public MappingJacksonJsonView ajaxGetCalendar(@PathVariable("propinsiId") Long propinsiId) {
        MappingJacksonJsonView view = new MappingJacksonJsonView();

        Propinsi propinsi = propinsiService.findById(propinsiId);
        view.addStaticAttribute("propinsi", propinsi);

        List<Kabupaten> kabupatenList = kabupatenService.findByPropinsi(propinsi);
        view.addStaticAttribute("kabupatenList", kabupatenList);


        return view;
    }
}
