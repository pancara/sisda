package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.Agenda;
import com.integrasolusi.pusda.sisda.service.AgendaService;
import com.integrasolusi.pusda.sisda.web.form.CommentForm;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.ImageUtils;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jasypt.util.text.TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 9:03 AM
 */

@Controller
public class AgendaController {
    private static Logger logger = Logger.getLogger(AgendaController.class);
    private static String CAPTCHA_KEY = "agenda.comment.captcha";
    private AgendaService agendaService;
    private PagingHelper pagingHelper;
    private ImageUtils imageUtils;
    private TextEncryptor textEncryptor;
    private ContentTypeUtils contentTypeUtils;

    @Autowired
    public void setAgendaService(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @Autowired
    public void setContentTypeUtils(ContentTypeUtils contentTypeUtils) {
        this.contentTypeUtils = contentTypeUtils;
    }

    @Autowired
    public void setPagingHelper(PagingHelper pagingHelper) {
        this.pagingHelper = pagingHelper;
    }

    @Autowired
    public void setImageUtils(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    @Autowired
    public void setTextEncryptor(TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }

    @RequestMapping("/agenda.html")
    public ModelAndView list(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return list(1L, keyword);
    }


    @RequestMapping("/agenda/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        ModelAndView mav = new ModelAndView("site/agenda/agenda");
        mav.addObject("keyword", keyword);

        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start_row", start);

        Long count = getCount(keyword);
        mav.addObject("last_page", pagingHelper.calcPageCount(count));

        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<Agenda> agendaList = getAgendaList(keyword, start, pagingHelper.getRowPerPage());
        mav.addObject("agendaList", agendaList);

        return mav;
    }

    @RequestMapping("/agenda/{year}/{month}/{date}.html")
    public ModelAndView list(@PathVariable("year") Integer year,
                             @PathVariable("month") Integer month,
                             @PathVariable("date") Integer date) {
        return list(year, month, date, 1L);
    }

    @RequestMapping("/agenda/{year}/{month}/{date}/{page}.html")
    public ModelAndView list(@PathVariable("year") Integer year,
                             @PathVariable("month") Integer month,
                             @PathVariable("date") Integer date,
                             @PathVariable("page") Long page) {
        ModelAndView mav = new ModelAndView("site/agenda/agenda_by_date");
        mav.addObject("year", year);
        mav.addObject("month", month);
        mav.addObject("date", date);

        mav.addObject("current", page);


        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start_row", start);

        Date holdDate = new GregorianCalendar(year, month - 1, date).getTime();

        Long count = agendaService.countByHoldDate(holdDate);
        mav.addObject("last_page", pagingHelper.calcPageCount(count));

        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<Agenda> agendaList = agendaService.getByHoldDate(holdDate, start, pagingHelper.getRowPerPage());
        mav.addObject("agendaList", agendaList);

        return mav;
    }


    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return agendaService.countPublished();
        else
            return agendaService.countPublishedByKeyword(keyword);
    }

    private List<Agenda> getAgendaList(String keyword, Long start, Long count) {
        if (StringUtils.isEmpty(keyword))
            return agendaService.getPublished(start, count);
        else
            return agendaService.getPublishedByKeyword(keyword, start, count);
    }

    @RequestMapping("/agenda/read/{id}.html")
    public ModelAndView read(@PathVariable("id") Long id,
                             @ModelAttribute("form") CommentForm form) {
        ModelAndView mav = new ModelAndView("site/agenda/agenda_read");
        Agenda agenda = agendaService.findById(id);
        mav.addObject("agenda", agenda);
        return mav;
    }

    @RequestMapping("/agenda/thumb/{id}/**")
    public void thumb(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Agenda agenda = agendaService.findById(id);
        if (agenda == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(agenda.getThumbFilename()));
        agendaService.getThumbBlob(id, response.getOutputStream());
    }

}
