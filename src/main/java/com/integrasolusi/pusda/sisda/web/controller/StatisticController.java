package com.integrasolusi.pusda.sisda.web.controller;

import com.integrasolusi.pusda.sisda.service.VisitorCounterService;
import com.integrasolusi.pusda.sisda.web.utils.VisitorSessionCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

/**
 * Programmer   : pancara
 * Date         : 7/28/11
 * Time         : 4:18 PM
 */
@Controller
public class StatisticController {
    @Autowired
    private VisitorCounterService visitorCounterService;

    @RequestMapping("/statistic.html")
    public ModelAndView getStatistic(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("site/statistic");
        Date today = new Date();
        mav.addObject("today", today);

        ServletContext context = request.getSession().getServletContext();
        Long sessionCount = (Long) context.getAttribute(VisitorSessionCounter.SESSION_COUNT_KEY);
        if (sessionCount == null || sessionCount == 0L) {
            sessionCount = 1L;
            context.setAttribute(VisitorSessionCounter.SESSION_COUNT_KEY, sessionCount);
        }
        mav.addObject("sessionCount", sessionCount);

        mav.addObject("count_today", visitorCounterService.findByDate(today));

        // set the date
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        // calculate week
        Calendar start = (Calendar) cal.clone();
        start.add(Calendar.DAY_OF_WEEK, start.getFirstDayOfWeek() - start.get(Calendar.DAY_OF_WEEK));

        Calendar end = (Calendar) start.clone();
        end.add(Calendar.DAY_OF_YEAR, 6);

        mav.addObject("count_week", visitorCounterService.findInDate(start.getTime(), end.getTime()));

        mav.addObject("count_month", visitorCounterService.findInMonth(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR)));

        mav.addObject("count_total", visitorCounterService.getTotal());
        return mav;
    }
}
