package com.integrasolusi.pusda.sisda.web.controller.site.tkpsda;

import com.integrasolusi.pusda.sisda.persistence.SystemConfig;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.documentation.Event;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.documentation.EventPicture;
import com.integrasolusi.pusda.sisda.service.SystemConfigService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.service.tkpsda.documentation.EventPictureService;
import com.integrasolusi.pusda.sisda.service.tkpsda.documentation.EventService;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Programmer : pancara
 * Date       : 11/12/12
 * Time       : 3:32 PM
 */
@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventPictureService eventPictureService;

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private YearService yearService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @Autowired
    private ImageUtils imageUtils;

    @RequestMapping("/tkpsda/event/{ws}/view.html")
    public ModelAndView view(@PathVariable("ws") Long wsId) {
        SystemConfig config = systemConfigService.findById(SystemConfig.TKPSDA_YEAR);
        return view(wsId, config.getLongValue());
    }

    @RequestMapping("/tkpsda/event/{ws}/{year}/view.html")
    public ModelAndView view(@PathVariable("ws") Long wsId,
                             @PathVariable("year") Long yearId) {
        ModelAndView mav = new ModelAndView("site/tkpsda/dokumentasi/list");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        Year year = yearService.findById(yearId);
        mav.addObject("year", year);

        mav.addObject("years", yearService.findAlls());

        List<Event> events = eventService.findByWilayahSungaiAndYearAndActive(ws, year, true);

        Map<Event, List> eventPhotoMap = new LinkedHashMap<>();
        for (Event event : events) {
            eventPhotoMap.put(event, eventPictureService.findByEvent(event));
        }
        mav.addObject("eventPhotoMap", eventPhotoMap);
        return mav;
    }

    @RequestMapping("/tkpsda/dokumentasi/photo/{id}/**")
    public void downloadPhoto(HttpServletResponse response,
                              @PathVariable("id") Long id) throws IOException {
        EventPicture photo = eventPictureService.findById(id);
        String contentType = contentTypeUtils.getContentType(photo.getFilename());
        response.setContentType(contentType);
        eventPictureService.getBlob(id, response.getOutputStream());
    }

    @RequestMapping("/tkpsda/dokumentasi/photo/thumb/{id}/**")
    public void downloadThumb(HttpServletResponse response,
                              @PathVariable("id") Long id,
                              @RequestParam(value = "width", required = false, defaultValue = "100") Integer w,
                              @RequestParam(value = "height", required = false, defaultValue = "0") Integer h) throws IOException {
        EventPicture photo = eventPictureService.findById(id);
        String contentType = contentTypeUtils.getContentType(photo.getFilename());
        response.setContentType(contentType);

        eventPictureService.getBlob(id, w, h, response.getOutputStream());

    }

}
