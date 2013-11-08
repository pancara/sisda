package com.integrasolusi.pusda.sisda.web.controller.site.ffws;

import com.integrasolusi.pusda.sisda.persistence.ffws.Station;
import com.integrasolusi.pusda.sisda.persistence.ffws.WaterLevel;
import com.integrasolusi.pusda.sisda.service.ffws.StationService;
import com.integrasolusi.pusda.sisda.service.ffws.WaterLevelService;
import com.integrasolusi.pusda.sisda.web.dto.WaterLevelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Programmer : pancara
 * Date       : 6/14/13
 * Time       : 6:12 PM
 */
@Controller
public class FfwsWaterLevelController {
    @Autowired
    private StationService stationService;

    @Autowired
    private WaterLevelService waterLevelService;

    @Value(value = "${ffws.waterLevel.jsonData.count}")
    private Long waterLevelDataCount = 10L;

    @RequestMapping(value = {"/ffws/chart.html", "/ajax/ffws/chart.html"})
    public ModelAndView getWaterLevel() {
        ModelAndView mav = new ModelAndView("site/ffws/chart");

        List<Station> stationList = stationService.findAlls();
        HashMap<Station, WaterLevel> waterLevelData = new LinkedHashMap<>();
        for (Station station : stationList) {
            waterLevelData.put(station, waterLevelService.getLatestByStation(station));
        }

        mav.addObject("waterLevelData", waterLevelData);
        return mav;
    }

    @RequestMapping("/ffws/get_data/{stationId}.html")
    @ResponseBody
    public List<WaterLevelDto> getData(@PathVariable(value = "stationId") Long stationId,
                                       @RequestParam(value = "n", required = false) Long n) {
        Station station = stationService.findById(stationId);
        Long count = n != null ? n : waterLevelDataCount;
        List<WaterLevel> waterLevelList = waterLevelService.findByStation(station, 0L, count);
        Collections.reverse(waterLevelList);

        List<WaterLevelDto> waterLevelDtoList = new LinkedList<>();
        for (WaterLevel wl : waterLevelList) {
            WaterLevelDto dto = new WaterLevelDto();
            dto.setSamplingAt(wl.getSamplingAt());
            dto.setValue(wl.getValue());
            waterLevelDtoList.add(dto);
        }

        return waterLevelDtoList;
    }

    @RequestMapping("/ffws/map")
    public ModelAndView view(@RequestParam(value = "station", required = false) Long stationId) {
        ModelAndView mav = new ModelAndView("site/ffws/map");
        if (stationId != null) {
            Station station = stationService.findById(stationId);
            mav.addObject("station", station);
        }

        return mav;
    }

    @RequestMapping("/ffws/get_data/stations.html")
    @ResponseBody
    public Map<String, Object> getStations(@RequestParam(value = "station", required = false) Long id) {
        Map<String, Object> json = new HashMap<>();

        Map<String, Object> center = id == null ? null : getStringObjectMap(stationService.findById(id));
        json.put("center", center);

        List<Map<String, Object>> locations = new LinkedList<>();
        for (Station station : stationService.findAlls()) {
            Map<String, Object> properties = getStringObjectMap(station);
            locations.add(properties);
        }
        json.put("locations", locations);
        return json;
    }

    private Map<String, Object> getStringObjectMap(Station station) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("id", station.getId());
        properties.put("name", station.getName());
        properties.put("description", station.getDescription());
        properties.put("latitude", station.getLatitude());
        properties.put("longitude", station.getLongitude());
        return properties;
    }
}
