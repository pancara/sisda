package com.integrasolusi.pusda.sisda.web.controller.site.presentation;

import com.integrasolusi.pusda.sisda.persistence.presentation.Meeting;
import com.integrasolusi.pusda.sisda.service.presetantion.MeetingService;
import com.integrasolusi.pusda.sisda.service.presetantion.ModuleService;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.PagingHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 9:03 AM
 */

@Controller
public class MeetingController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private PagingHelper pagingHelper;

    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping("presentation/meeting/list.html")
    public ModelAndView list() {
        return list(1L);
    }


    @RequestMapping("presentation/meeting/page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page) {
        ModelAndView mav = new ModelAndView("site/presentation/meeting/list");
        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);

        Long count = moduleService.countAlls();
        mav.addObject("last", pagingHelper.calcPageCount(count));
        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<Meeting> meetings = meetingService.findAlls(start, pagingHelper.getRowPerPage());
        mav.addObject("meetings", meetings);

        return mav;
    }

    @RequestMapping("presentation/meeting/view/{id}.html")
    public ModelAndView view(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("site/presentation/meeting/view");

        Meeting meeting = meetingService.findById(id);

        mav.addObject("meeting", meeting);
        if (meeting != null) {
            mav.addObject("modules", moduleService.findByMeeting(meeting));
        }

        return mav;
    }

}
