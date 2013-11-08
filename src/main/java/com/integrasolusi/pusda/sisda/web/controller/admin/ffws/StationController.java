package com.integrasolusi.pusda.sisda.web.controller.admin.ffws;

import com.integrasolusi.pusda.sisda.persistence.ffws.Station;
import com.integrasolusi.pusda.sisda.service.ffws.StationService;
import com.integrasolusi.pusda.sisda.web.form.ffws.StationForm;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 9:03 AM
 */

@Controller("adminStationController")
@RequestMapping("/admin/ffws/station")
public class StationController {
    private static Logger logger = Logger.getLogger(StationController.class);

    @Autowired
    private StationService stationService;


    @RequestMapping("list.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("admin/ffws/station/list");
        List<Station> stations = stationService.findAlls();
        mav.addObject("stations", stations);
        return mav;
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") StationForm form) {
        return new ModelAndView("admin/ffws/station/form");
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") StationForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Station station = new Station();
            station.setName(form.getName());
            station.setDescription(form.getDescription());

            station.setLatitude(form.getLatitude());
            station.setLongitude(form.getLongitude());

            station.setSourceTable(form.getSourceTable());

            stationService.save(station);
            return new ModelAndView("redirect:/admin/ffws/station/list.html");
        }
        return new ModelAndView("admin/ffws/station/form");
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") StationForm form) {
        ModelAndView mav = new ModelAndView("admin/ffws/station/form");
        Station station = stationService.findById(id);
        if (station == null) {
            return new ModelAndView("redirect:/admin/ffws/station/list.html");
        }
        form.setName(station.getName());
        form.setDescription(station.getDescription());
        form.setLatitude(station.getLatitude());
        form.setLongitude(station.getLongitude());
        form.setSourceTable(station.getSourceTable());

        return mav;
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") StationForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Station station = stationService.findById(id);
            station.setName(form.getName());
            station.setDescription(form.getDescription());

            station.setLatitude(form.getLatitude());
            station.setLongitude(form.getLongitude());

            station.setSourceTable(form.getSourceTable());

            stationService.save(station);
            return new ModelAndView("redirect:/admin/ffws/station/list.html");
        }
        return new ModelAndView("admin/ffws/station/form");
    }

    private void validateForm(StationForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }

        if (StringUtils.isEmpty(form.getSourceTable())) {
            errors.reject("sourceTable.empty", "Nama table sumber belum diisi");
        }
    }

    @RequestMapping(value = "remove.html", params = "remove", method = RequestMethod.POST)
    public ModelAndView remove(@RequestParam(value = "ids", required = false) Long[] ids) {
        if (ids != null) {
            stationService.removeByIds(ids);
        }
        return new ModelAndView("redirect:/admin/ffws/station/list.html");

    }


}
