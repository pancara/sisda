package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.Agenda;
import com.integrasolusi.pusda.sisda.persistence.Ticker;
import com.integrasolusi.pusda.sisda.service.AgendaService;
import com.integrasolusi.pusda.sisda.service.TickerService;
import com.integrasolusi.pusda.sisda.web.form.AgendaForm;
import com.integrasolusi.pusda.sisda.web.form.RepositoryPhotoForm;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 5:19 PM
 */
@Controller("adminAgendaController")
@RequestMapping("/admin/agenda")
public class AgendaController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AgendaController.class);
    @Autowired
    private AgendaService agendaService;

    @Autowired
    private PagingHelper pagingHelper;

    @Autowired
    private TickerService tickerService;


    @RequestMapping("list.html")
    public ModelAndView list(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return list(1L, keyword);
    }


    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        ModelAndView mav = new ModelAndView("admin/agenda/list");
        mav.addObject("keyword", keyword);

        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);

        Long count = getCount(keyword);
        mav.addObject("count", count);

        mav.addObject("last", pagingHelper.calcPageCount(count));



        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        mav.addObject("last", pagingHelper.calcPageCount(count));

        List<Agenda> agendaList = getAgendaList(keyword, start, pagingHelper.getRowPerPage());
        mav.addObject("agendaList", agendaList);
        return mav;
    }

    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return agendaService.countAlls();
        else
            return agendaService.countByKeyword(keyword);
    }

    private List<Agenda> getAgendaList(String keyword, Long start, Long count) {
        if (StringUtils.isEmpty(keyword))
            return agendaService.get(start, count);
        else
            return agendaService.getByKeyword(keyword, start, count);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView createNew(@ModelAttribute("form") AgendaForm form) {
        return new ModelAndView("admin/agenda/form");
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView createNew(@ModelAttribute("form") AgendaForm form, Errors errors) {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Agenda agenda = new Agenda();
            agenda.setTitle(form.getTitle());
            agenda.setDescription(form.getDescription());
            agenda.setContent(form.getFullContent());
            agenda.setHoldDate(form.getHoldDate().getCalendar().getTime());

            agenda.setPublishedDate(new Date());
            agenda.setPublished(false);
            agendaService.save(agenda);
            return new ModelAndView("redirect:/admin/agenda/list.html");
        }
        return new ModelAndView("admin/agenda/form");
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") AgendaForm form) {
        Agenda agenda = agendaService.findById(id);
        form.setTitle(agenda.getTitle());
        form.setDescription(agenda.getDescription());
        form.setFullContent(agenda.getContent());

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(agenda.getHoldDate());
        form.getHoldDate().setCalendar(cal);
        return new ModelAndView("admin/agenda/form");
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") AgendaForm form, Errors errors) {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Agenda agenda = agendaService.findById(id);
            agenda.setTitle(form.getTitle());
            agenda.setDescription(form.getDescription());
            agenda.setContent(form.getFullContent());
            agenda.setHoldDate(form.getHoldDate().getCalendar().getTime());

            agendaService.save(agenda);
            return new ModelAndView("redirect:/admin/agenda/list.html");
        }
        return new ModelAndView("admin/agenda/form");
    }

    private void validateForm(AgendaForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getTitle())) {
            errors.reject("title.empty", "Judul belum diisi");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }

        if (StringUtils.isEmpty(form.getFullContent())) {
            errors.reject("fullContent.empty", "Berita lengkap belum diisi");
        }
    }


    @RequestMapping(value = "manage.html")
    public ModelAndView manage(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {
        return new ModelAndView(String.format("redirect:/admin/agenda/page/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            agendaService.removeByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/agenda/page/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "manage.html", params = "publish")
    public ModelAndView publish(@RequestParam(value = "page", required = false) Long page,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            agendaService.publishByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/agenda/page/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "manage.html", params = "unpublish")
    public ModelAndView unpublish(@RequestParam(value = "page", required = false) Long page,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            agendaService.unpublishByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/agenda/page/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "manage.html", params = "ticker")
    public ModelAndView createTicker(@RequestParam(value = "page", required = false) Long page,
                                     @RequestParam(value = "keyword", required = false) String keyword,
                                     @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            createTicker(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/agenda/page/%d.html?keyword=%s", page, keyword));

    }

    private void createTicker(Long[] ids) {
        List<Long> idList = new LinkedList<>();
        for (Long id : ids) {
            idList.add(id);
        }
        Collections.sort(idList);
        for (Long id : idList) {
            Agenda agenda = agendaService.findById(id);
            Ticker ticker = new Ticker();
            ticker.setTitle(agenda.getTitle());
            ticker.setUrl(createTickerUrl(agenda));
            tickerService.save(ticker);
        }
    }

    private String createTickerUrl(Agenda agenda) {
        return String.format("/agenda/read/%d.html", agenda.getId());
    }

    @RequestMapping(value = "{id}/thumb/clear.html")
    public ModelAndView removeThumb(@PathVariable("id") Long id,
                                    @RequestParam(value = "page", required = false) Long page,
                                    @RequestParam(value = "keyword", required = false) String keyword) {
        agendaService.removeThumb(id);
        return new ModelAndView(String.format("redirect:/admin/agenda/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "/popup/agenda/thumb/upload/{id}.html", method = RequestMethod.GET)
    public ModelAndView uploadThumb(@PathVariable("id") Long id,
                                    @ModelAttribute("form") RepositoryPhotoForm form) {
        ModelAndView mav = new ModelAndView("admin/agenda/thumb_form");
        mav.addObject("agenda", agendaService.findById(id));
        return mav;
    }

    @RequestMapping(value = "/popup/agenda/thumb/upload/{id}.html", method = RequestMethod.POST)
    public ModelAndView uploadThumb(@PathVariable("id") Long id,
                                    @ModelAttribute("form") RepositoryPhotoForm form, Errors errors) throws IOException {

        Agenda agenda = agendaService.findById(id);
        if (form.getFile() != null && !form.getFile().isEmpty()) {
            agenda.setThumbFilename(form.getFile().getOriginalFilename());
            agendaService.save(agenda, form.getFile().getInputStream());

            ModelAndView mav = new ModelAndView("admin/agenda/thumb_form_success");
            mav.addObject("agenda", agenda);
            return mav;
        }

        ModelAndView mav = new ModelAndView("admin/agenda/thumb_form");
        mav.addObject("agenda", agenda);
        return mav;
    }


}
