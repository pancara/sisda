package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.Leaflet;
import com.integrasolusi.pusda.sisda.persistence.Link;
import com.integrasolusi.pusda.sisda.persistence.LinkType;
import com.integrasolusi.pusda.sisda.persistence.Publication;
import com.integrasolusi.pusda.sisda.service.AgendaService;
import com.integrasolusi.pusda.sisda.service.LeafletService;
import com.integrasolusi.pusda.sisda.service.LinkService;
import com.integrasolusi.pusda.sisda.service.PublicationService;
import com.integrasolusi.pusda.sisda.service.ffws.StationService;
import com.integrasolusi.pusda.sisda.service.ffws.WaterLevelService;
import com.integrasolusi.pusda.sisda.service.presetantion.MeetingService;
import com.integrasolusi.pusda.sisda.web.form.GuestMessageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * User: pancara
 * Date: 7/26/12
 * Time: 12:13 PM
 */
@Controller
public class SidePanelController {
    @Autowired
    private LeafletService leafletService;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private StationService stationService;

    @Autowired
    private WaterLevelService waterLevelService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private MeetingService meetingService;

    @Value(value = "${latest.leaflet.count}")
    private Long latestCount;

    @Value(value = "${latest.presentation_meeting.count}")
    private Long presentationMeetingCount;

    @Value(value = "${latest.publication.count}")
    private Long publicationCount;

    @Value(value = "${latest.water_level.count}")
    private Long waterLevelCount;

    
    
    @RequestMapping("/side_panel/leaflet.html")
    public ModelAndView getLeaflet() {
        ModelAndView mav = new ModelAndView("site/side_panel/leaflet");
        List<Leaflet> leafletList = leafletService.getLatest(latestCount);
        mav.addObject("leafletList", leafletList);

        return mav;
    }

    @RequestMapping("/side_panel/calendar.html")
    public ModelAndView getCalendar() {
        ModelAndView mav = new ModelAndView("site/side_panel/calendar");

        Calendar cal = GregorianCalendar.getInstance();

        mav.addObject("today", cal.getTime());

        mav.addObject("selectedMonth", cal.get(Calendar.MONTH));
        mav.addObject("selectedYear", cal.get(Calendar.YEAR));
        mav.addObject("monthCalendar", createCalendar(cal));

        return mav;
    }

    public java.util.List<java.util.Map<Date, Object>> createCalendar(Calendar cal) {
        Calendar startDate = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        Calendar endDate = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        startDate.set(Calendar.DAY_OF_MONTH, 2 - startDate.get(Calendar.DAY_OF_WEEK));
        int day_of_month = endDate.getActualMaximum(Calendar.DAY_OF_MONTH) + 7 - endDate.get(Calendar.DAY_OF_WEEK);
        endDate.set(Calendar.DAY_OF_MONTH, day_of_month);

        List<Map<Date, Object>> monthCalendar = new LinkedList<Map<Date, Object>>();
        Map<Date, Object> week = null;
        Calendar day = startDate;
        while (true) {
            if (1 == day.get(Calendar.DAY_OF_WEEK)) {
                week = new LinkedHashMap<Date, Object>();
                monthCalendar.add(week);
            }

            week.put(day.getTime(), agendaService.countByHoldDate(day.getTime()));
            day.set(Calendar.DAY_OF_MONTH, day.get(Calendar.DAY_OF_MONTH) + 1);

            if (day.after(endDate)) {
                break;
            }
        }
        return monthCalendar;
    }


    @RequestMapping("/side_panel/guest_book_form.html")
    public ModelAndView getGuestBookForm(@ModelAttribute("form") GuestMessageForm form) {
        ModelAndView mav = new ModelAndView("site/side_panel/guest_book_form");
        return mav;
    }

    @RequestMapping("/ajax/calendar_view/{year}/{month}.html")
    public ModelAndView ajaxGetCalendar(@PathVariable("year") Integer year,
                                        @PathVariable("month") Integer month) {
        ModelAndView mav = new ModelAndView("site/side_panel/calendar_view");
        Calendar today = GregorianCalendar.getInstance();
        mav.addObject("today", today.getTime());
        mav.addObject("selectedMonth", month);
        mav.addObject("selectedYear", year);

        Calendar cal = GregorianCalendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);

        mav.addObject("monthCalendar", createCalendar(cal));

        return mav;
    }

    @RequestMapping("/side_panel/publication.html")
    public ModelAndView getPublication() {
        ModelAndView mav = new ModelAndView("site/side_panel/publication");
        List<Publication> publicationList = publicationService.getLatest(publicationCount);
        mav.addObject("publicationList", publicationList);

        java.util.Map<Publication, Boolean> titlePictures = new HashMap<Publication, Boolean>();
        for (Publication publication : publicationList) {
            titlePictures.put(publication, publicationService.hasTitlePicture(publication.getId()));
        }
        mav.addObject("titlePictures", titlePictures);
        return mav;
    }

    @RequestMapping(value = {"/side_panel/ffws.html", "/ajax/side_panel/ffws.html"})
    public ModelAndView getLatestWaterLevel() {
        ModelAndView mav = new ModelAndView("site/side_panel/ffws");
        mav.addObject("waterLevels", waterLevelService.getLatest(waterLevelCount.intValue()));
        return mav;
    }

    @RequestMapping("/side_panel/presentation/meeting.html")
    public ModelAndView getPresentationModule() {
        ModelAndView mav = new ModelAndView("site/side_panel/presentation_meeting");
        mav.addObject("meetings", meetingService.getLatest(presentationMeetingCount));
        return mav;
    }

    @RequestMapping("/side_panel/application.html")
    public ModelAndView getApplication() {
        return new ModelAndView("site/side_panel/application");
    }

    @RequestMapping("/side_panel/institution.html")
    public ModelAndView getInstitution() {
        ModelAndView mav = new ModelAndView("site/side_panel/institution");
        java.util.List<Link> links = linkService.findByTypeAndPublished(LinkType.INSTITUTION);
        mav.addObject("links", links);
        return mav;
    }

    @RequestMapping("/side_panel/media.html")
    public ModelAndView getMedia() {
        ModelAndView mav = new ModelAndView("site/side_panel/media");
        java.util.List<Link> links = linkService.findByTypeAndPublished(LinkType.MEDIA);
        mav.addObject("links", links);
        return mav;
    }
}
