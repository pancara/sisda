package com.integrasolusi.pusda.sisda.web.controller.admin.presentation;

import com.integrasolusi.pusda.sisda.persistence.presentation.Meeting;
import com.integrasolusi.pusda.sisda.service.presetantion.MeetingService;
import com.integrasolusi.pusda.sisda.web.form.SearchMeetingForm;
import com.integrasolusi.pusda.sisda.web.form.presentation.MeetingForm;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 9:03 AM
 */

@Controller("adminMeetingController")
@RequestMapping("/admin/presentation/meeting")
public class MeetingController {
    private static Logger logger = Logger.getLogger(MeetingController.class);

    @Autowired
    private MeetingService meetingService;


    @Autowired
    private PagingHelper pagingHelper;


    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") SearchMeetingForm form) {
        return list(1L, form);
    }

    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @ModelAttribute("form") SearchMeetingForm form) {
        ModelAndView mav = new ModelAndView("admin/presentation/meeting/list");

        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);
        Long count = getCount(form.getKeyword());
        mav.addObject("count", count);
        mav.addObject("last", pagingHelper.calcPageCount(count));

        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<Meeting> meetingList = getMeetingList(form.getKeyword(), start, pagingHelper.getRowPerPage());
        mav.addObject("meetingList", meetingList);

        return mav;
    }

    private String createKeywordString(String text) {
        return String.format("%%%s%%", text);
    }

    private Long getCount(String keyword) {
        return (StringUtils.isEmpty(keyword)) ?
                meetingService.countAlls()
                :
                meetingService.countByKeyword(createKeywordString(keyword));
    }

    private List<Meeting> getMeetingList(String keyword, Long start, Long count) {
        return (StringUtils.isEmpty(keyword)) ?
                meetingService.findAlls(start, count)
                :
                meetingService.findByKeyword(createKeywordString(keyword), start, count);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") MeetingForm form) {
        return new ModelAndView("admin/presentation/meeting/form");
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") MeetingForm form, Errors errors) throws IOException {
        validateForm(form, errors);

        if (!errors.hasErrors()) {
            Meeting meeting = new Meeting();
            meeting.setName(form.getName());
            meeting.setLocation(form.getLocation());
            Calendar cal = form.getDate().getCalendar();
            if (cal != null) {
                meeting.setDate(cal.getTime());
            }


            meetingService.save(meeting);
            return new ModelAndView("redirect:/admin/presentation/meeting/list.html");
        }
        return new ModelAndView("admin/presentation/meeting/form");
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") MeetingForm form) {
        ModelAndView mav = new ModelAndView("admin/presentation/meeting/form");
        Meeting meeting = meetingService.findById(id);
        form.setName(meeting.getName());
        form.setLocation(meeting.getLocation());
        if (meeting.getDate() != null) {
            Calendar cal = new GregorianCalendar();
            cal.setTime(meeting.getDate());
            form.getDate().setCalendar(cal);
        }

        return mav;
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") MeetingForm form, Errors errors) throws IOException {
        validateForm(form, errors);

        if (!errors.hasErrors()) {
            Meeting meeting = meetingService.findById(id);
            meeting.setName(form.getName());
            meeting.setLocation(form.getLocation());
            Calendar cal = form.getDate().getCalendar();
            if (cal != null) {
                meeting.setDate(cal.getTime());
            }

            meetingService.save(meeting);
            return new ModelAndView("redirect:/admin/presentation/meeting/list.html");
        }
        return new ModelAndView("admin/presentation/meeting/form");
    }

    private void validateForm(MeetingForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

    }

    @RequestMapping(value = "remove.html")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            meetingService.removeByIds(ids);
        }

        return createViewRedirectToPage(page, keyword);

    }

    private ModelAndView createViewRedirectToPage(Long page, String keyword) {
        String sPage = page == null ? "1" : String.format("%d", page);
        return new ModelAndView(String.format("redirect:/admin/presentation/meeting/page/%s.html?keyword=%s", sPage, keyword));
    }

}
