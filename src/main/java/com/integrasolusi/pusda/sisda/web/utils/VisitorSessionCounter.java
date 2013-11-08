package com.integrasolusi.pusda.sisda.web.utils;

import com.integrasolusi.pusda.sisda.service.VisitorCounterService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

/**
 * Programmer   : pancara
 * Date         : 7/28/11
 * Time         : 4:09 PM
 */
public class VisitorSessionCounter implements HttpSessionListener {

    public final static String SESSION_COUNT_KEY = "system_session_count";

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        ServletContext context = httpSessionEvent.getSession().getServletContext();
        Long count = getSessionCount(context);
        count++;
        if (count < 0L) {
            count = 0L;
        }
        setSessionCount(context, count);

        // increment visitor stats
        VisitorCounterService visitorCounterService = WebApplicationContextUtils.getWebApplicationContext(context).getBean(VisitorCounterService.class);
        visitorCounterService.incrementCount(new Date());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        ServletContext context = httpSessionEvent.getSession().getServletContext();
        Long count = getSessionCount(context);
        count--;
        if (count < 0L) count = 0L;
        setSessionCount(context, count);
    }

    private Long getSessionCount(ServletContext context) {
        Long count = (Long) context.getAttribute(SESSION_COUNT_KEY);
        return (count != null ? count : 0L);
    }

    private void setSessionCount(ServletContext context, Long count) {
        context.setAttribute(SESSION_COUNT_KEY, count);
    }


}
