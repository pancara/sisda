package com.integrasolusi.pusda.sisda.web.controller.admin.tkpsda;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.documentation.Event;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.documentation.EventPicture;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.service.tkpsda.documentation.EventPictureService;
import com.integrasolusi.pusda.sisda.service.tkpsda.documentation.EventService;
import com.integrasolusi.pusda.sisda.web.form.tkpsda.EventForm;
import com.integrasolusi.pusda.sisda.web.form.tkpsda.EventPictureForm;
import com.integrasolusi.utils.ContentTypeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Programmer : pancara
 * Date       : 7/30/13
 * Time       : 9:38 AM
 */


@Controller("adminTkpsdaEventController")
@RequestMapping("/admin/tkpsda/event")
public class EventController {
    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private YearService yearService;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventPictureService eventPictureService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("view.html")
    public ModelAndView view() {
        ModelAndView mav = new ModelAndView("admin/tkpsda/event/list_ws");
        mav.addObject("wsList", wilayahSungaiService.findAlls());
        return mav;
    }

    @RequestMapping(value = "view/{ws}.html")
    public ModelAndView view(@PathVariable(value = "ws") Long wsId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/event/list_year");
        mav.addObject("wilayahSungai", wilayahSungaiService.findById(wsId));

        mav.addObject("years", yearService.findAlls());
        return mav;
    }

    @RequestMapping(value = "view/{ws}/{year}.html")
    public ModelAndView view(@PathVariable(value = "ws") Long wsId,
                             @PathVariable(value = "year") Long yearId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/event/list_event");

        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("wilayahSungai", ws);

        Year year = yearService.findById(yearId);
        mav.addObject("year", year);

        mav.addObject("events", eventService.findByWilayahSungaiAndYear(ws, year));
        return mav;
    }

    @RequestMapping(value = "view/{ws}/{year}/{event}.html")
    public ModelAndView view(@PathVariable(value = "ws") Long wsId,
                             @PathVariable(value = "year") Long yearId,
                             @PathVariable(value = "event") Long eventId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/event/list_photo");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("wilayahSungai", ws);

        Year year = yearService.findById(yearId);
        mav.addObject("year", year);

        Event event = eventService.findById(eventId);
        mav.addObject("event", event);

        mav.addObject("photos", eventPictureService.findByEvent(event));
        return mav;
    }


    @RequestMapping(value = "add/{ws}/{year}.html", method = RequestMethod.GET)
    public ModelAndView addEvent(@PathVariable(value = "ws") Long wsId,
                                 @PathVariable(value = "year") Long yearId,
                                 @ModelAttribute("form") EventForm form) {
        return createEventFormView(wsId, yearId, null);
    }

    private ModelAndView createEventFormView(Long wsId, Long yearId, Long eventId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/event/event_form");

        mav.addObject("wilayahSungai", wilayahSungaiService.findById(wsId));
        mav.addObject("year", yearService.findById(yearId));

        if (eventId != null) {
            mav.addObject("event", eventService.findById(eventId));
        }
        return mav;

    }

    @RequestMapping(value = "add/{ws}/{year}.html", method = RequestMethod.POST)
    public ModelAndView addEvent(@PathVariable(value = "ws") Long wsId,
                                 @PathVariable(value = "year") Long yearId,
                                 @ModelAttribute("form") EventForm form,
                                 Errors errors) throws IOException {
        validateEventForm(form, errors);
        if (!errors.hasErrors()) {
            Event event = new Event();
            event.setTitle(form.getTitle());
            event.setDate(form.getDate().getCalendar().getTime());

            event.setYear(yearService.findById(yearId));
            event.setWilayahSungai(wilayahSungaiService.findById(wsId));
            event.setActive(true);
            
            eventService.save(event);

            return createRedirectEventView(wsId, yearId);
        }

        return createEventFormView(wsId, yearId, null);
    }

    private ModelAndView createRedirectEventView(Long wsId, Long yearId) {
        return new ModelAndView(String.format("redirect:/admin/tkpsda/event/view/%d/%d.html", wsId, yearId));
    }

    private void validateEventForm(EventForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getTitle())) {
            errors.reject("title.empty", "Nama event belum diisi");
        }
    }

    @RequestMapping(value = "edit/{ws}/{year}/{event}.html", method = RequestMethod.GET)
    public ModelAndView editActivity(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable("event") Long eventId,
                                     @ModelAttribute("form") EventForm form) {
        Event event = eventService.findById(eventId);
        form.setTitle(event.getTitle());

        Calendar cal = new GregorianCalendar();
        cal.setTime(event.getDate());
        form.getDate().setCalendar(cal);

        return createEventFormView(wsId, yearId, eventId);
    }

    @RequestMapping(value = "edit/{ws}/{year}/{event}.html", method = RequestMethod.POST)
    public ModelAndView editActivity(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable("event") Long eventId,
                                     @ModelAttribute("form") EventForm form,
                                     Errors errors) throws IOException {
        validateEventForm(form, errors);
        if (!errors.hasErrors()) {
            Event event = eventService.findById(eventId);
            event.setTitle(form.getTitle());
            event.setDate(form.getDate().getCalendar().getTime());

            eventService.save(event);

            return createRedirectEventView(wsId, yearId);
        }

        return createEventFormView(wsId, yearId, null);
    }

    @RequestMapping(value = "remove/{ws}/{year}.html", method = RequestMethod.POST)
    public ModelAndView removeActivity(@PathVariable(value = "ws") Long wsId,
                                       @PathVariable(value = "year") Long yearId,
                                       @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            eventService.removeByIds(ids);
        }
        return createRedirectEventView(wsId, yearId);
    }

    @RequestMapping(value = "add/{ws}/{year}/{event}.html", method = RequestMethod.GET)
    public ModelAndView addPhoto(@PathVariable(value = "ws") Long wsId,
                                 @PathVariable(value = "year") Long yearId,
                                 @PathVariable(value = "event") Long eventId,
                                 @ModelAttribute("form") EventPictureForm form) {
        return createPhotoFormView(wsId, yearId, eventId, null);
    }

    private ModelAndView createPhotoFormView(Long wsId, Long yearId, Long eventId, Long pictureId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/event/event_photo_form");

        mav.addObject("wilayahSungai", wilayahSungaiService.findById(wsId));
        mav.addObject("year", yearService.findById(yearId));
        mav.addObject("event", eventService.findById(eventId));

        if (pictureId != null) {
            mav.addObject("picture", eventPictureService.findById(pictureId));
        }

        return mav;
    }

    @RequestMapping(value = "add/{ws}/{year}/{event}.html", method = RequestMethod.POST)
    public ModelAndView addPhoto(@PathVariable(value = "ws") Long wsId,
                                 @PathVariable(value = "year") Long yearId,
                                 @PathVariable(value = "event") Long eventId,
                                 @ModelAttribute("form") EventPictureForm form,
                                 Errors errors) throws IOException {
        validateEventPictureForm(form, errors, false);
        if (!errors.hasErrors()) {
            EventPicture pic = new EventPicture();
            pic.setTitle(form.getTitle());
            pic.setIndex(form.getIndex());

            pic.setEvent(eventService.findById(eventId));

            pic.setFilename(form.getFile().getOriginalFilename());

            eventPictureService.save(pic, form.getFile().getInputStream());
            return createRedirectPhotoView(wsId, yearId, eventId);
        }

        return createPhotoFormView(wsId, yearId, eventId, null);
    }

    private ModelAndView createRedirectPhotoView(Long wsId, Long yearId, Long eventId) {
        return new ModelAndView(String.format("redirect:/admin/tkpsda/event/view/%d/%d/%d.html", wsId, yearId, eventId));
    }

    private void validateEventPictureForm(EventPictureForm form, Errors errors, boolean allowFileEmpty) {
        if (StringUtils.isEmpty(form.getTitle())) {
            errors.reject("title.empty", "Judul photo belum diisi");
        }

        if (allowFileEmpty == false) {
            if (form.getFile() == null || form.getFile().isEmpty()) {
                errors.reject("file.empty", "File belum diisi");
            }
        }
    }

    @RequestMapping(value = "edit/{ws}/{year}/{event}/{picture}.html", method = RequestMethod.GET)
    public ModelAndView editDocument(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable(value = "event") Long eventId,
                                     @PathVariable(value = "picture") Long pictureId,
                                     @ModelAttribute("form") EventPictureForm form) {
        EventPicture picture = eventPictureService.findById(pictureId);
        form.setTitle(picture.getTitle());
        form.setIndex(picture.getIndex());

        return createPhotoFormView(wsId, yearId, eventId, pictureId);
    }

    @RequestMapping(value = "edit/{ws}/{year}/{event}/{picture}.html", method = RequestMethod.POST)
    public ModelAndView editDocument(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable(value = "event") Long eventId,
                                     @PathVariable(value = "picture") Long picId,
                                     @ModelAttribute("form") EventPictureForm form,
                                     Errors errors) throws IOException {
        validateEventPictureForm(form, errors, true);
        if (!errors.hasErrors()) {
            EventPicture pic = eventPictureService.findById(picId);
            pic.setTitle(form.getTitle());
            pic.setIndex(form.getIndex());

            if (form.getFile() != null && !form.getFile().isEmpty()) {
                pic.setFilename(form.getFile().getOriginalFilename());
                eventPictureService.save(pic, form.getFile().getInputStream());
            } else {
                eventPictureService.save(pic);
            }


            return createRedirectPhotoView(wsId, yearId, eventId);
        }

        return createPhotoFormView(wsId, yearId, eventId, null);
    }

    @RequestMapping(value = "remove/{ws}/{year}/{event}.html", method = RequestMethod.POST)
    public ModelAndView removeDocument(@PathVariable(value = "ws") Long wsId,
                                       @PathVariable(value = "year") Long yearId,
                                       @PathVariable(value = "event") Long eventId,
                                       @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            eventPictureService.removeByIds(ids);
        }
        return createRedirectPhotoView(wsId, yearId, eventId);
    }

    @RequestMapping("picture/{ws}/{year}/{event}/{picture}/**")
    public void downloadPicture(@PathVariable(value = "ws") Long wsId,
                                @PathVariable(value = "year") Long yearId,
                                @PathVariable(value = "event") Long eventId,
                                @PathVariable(value = "picture") Long picId,
                                @RequestParam(value = "w", required = false) Integer width,
                                HttpServletResponse response) throws Exception {
        EventPicture picture = eventPictureService.findById(picId);
        if (picture == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (width == null) {
            response.setContentType(contentTypeUtils.getContentType(picture.getFilename()));
            eventPictureService.getBlob(picId, response.getOutputStream());
        } else {
            int w = width == null ? 0 : width;
            int h = 0;
            response.setContentType(contentTypeUtils.getContentType(picture.getFilename()));
            try {
                eventPictureService.getBlob(picId, w, h, response.getOutputStream());
            } catch (Exception e) {
                eventPictureService.getBlob(picId, response.getOutputStream());
            }
        }

    }
}
