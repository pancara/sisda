package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.Ticker;
import com.integrasolusi.pusda.sisda.service.TickerService;
import com.integrasolusi.pusda.sisda.web.form.SearchTickerForm;
import com.integrasolusi.pusda.sisda.web.form.TickerForm;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 11/29/12
 * Time       : 2:56 PM
 */
@Controller("adminTickerController")
@RequestMapping("/admin/ticker")
public class TickerController {
    private static Logger logger = LoggerFactory.getLogger(TickerController.class);

    @Autowired
    private TickerService tickerService;

    @Autowired
    private PagingHelper pagingHelper;

    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") SearchTickerForm form) {
        return list(1L, form);
    }

    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @ModelAttribute("form") SearchTickerForm form) {
        ModelAndView mav = new ModelAndView("admin/ticker/list");
        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);

        Long count = getCount(form.getKeyword());
        mav.addObject("count", count);

        mav.addObject("last", pagingHelper.calcPageCount(count));
        mav.addObject("pages", pagingHelper.getDisplayedPages(page, count));
        mav.addObject("tickerList", getTickerList(form.getKeyword(), start, pagingHelper.getRowPerPage()));
        return mav;
    }

    private List<Ticker> getTickerList(String keyword, Long start, Long count) {
        if (StringUtils.isEmpty(keyword))
            return tickerService.findAlls(start, count);
        else
            return tickerService.findByKeyword(createSearchKeyword(keyword), start, count);
    }

    private String createSearchKeyword(String keyword) {
        return String.format("%%%s%%", keyword);
    }

    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return tickerService.countAlls();
        else
            return tickerService.countByKeyword(createSearchKeyword(keyword));
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") TickerForm form,
                            @RequestParam(value = "page", required = false) Long page,
                            @RequestParam(value = "keyword", required = false) String keyword) throws IOException {
        return createFormView(page, keyword);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") TickerForm form,
                            Errors errors,
                            @RequestParam(value = "page", required = false) Long page,
                            @RequestParam(value = "keyword", required = false) String keyword) throws IOException {
        validateTickerForm(form, errors);
        if (errors.hasErrors()) {
            return createFormView(page, keyword);
        }

        try {
            saveUserForm(null, form);
            return createRedirectView(page, keyword);
        } catch (Exception e) {
            errors.reject("saving.failed", "Penyimpanan data gagal.");
            return createFormView(page, keyword);
        }
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id,
                             @ModelAttribute("form") TickerForm form,
                             @RequestParam(value = "page", required = false) Long page,
                             @RequestParam(value = "keyword", required = false) String keyword) throws IOException {
        Ticker ticker = tickerService.findById(id);
        if (ticker == null) {
            return createRedirectView(page, keyword);
        }
        form.setTitle(ticker.getTitle());
        form.setUrl(ticker.getUrl());

        ModelAndView mav = createFormView(page, keyword);
        mav.addObject("ticker", tickerService.findById(id));
        return mav;
    }


    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id,
                             @ModelAttribute("form") TickerForm form,
                             Errors errors,
                             @RequestParam(value = "page", required = false) Long page,
                             @RequestParam(value = "keyword", required = false) String keyword) throws IOException {
        validateTickerForm(form, errors);
        if (errors.hasErrors()) {
            ModelAndView mav = createFormView(page, keyword);
            mav.addObject("ticker", tickerService.findById(id));
            return mav;
        }

        try {
            saveUserForm(id, form);
            return createRedirectView(page, keyword);
        } catch (Exception e) {
            errors.rejectValue("saving.failed", "Penyimpanan data gagal.");
            return createFormView(page, keyword);
        }
    }

    private ModelAndView createFormView(Long page, String keyword) {
        ModelAndView mav = new ModelAndView("admin/ticker/form");
        mav.addObject("page", page);
        mav.addObject("keyword", keyword);
        return mav;
    }

    private ModelAndView createRedirectView(Long page, String keyword) {
        return new ModelAndView(String.format("redirect:/admin/ticker/page/%d.html?keyword=%s", page, keyword));
    }

    private void saveUserForm(Long id, TickerForm form) {
        Ticker ticker = id == null ?
                new Ticker() :
                tickerService.findById(id);

        if (ticker != null) {
            ticker.setTitle(form.getTitle());
            ticker.setUrl(form.getUrl());
            try {
                tickerService.save(ticker);
            } catch (Exception e) {
                throw new RuntimeException("save failed");
            }
        }
    }


    private void validateTickerForm(TickerForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getTitle())) {
            errors.reject("title.empty", "Judul belum diisi");
        }
        if (StringUtils.isEmpty(form.getUrl())) {
            errors.reject("url.empty", "URL  belum diisi");
        }
    }


    @RequestMapping(value = "manage.html")
    public ModelAndView manage(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {
        return createRedirectView(page, keyword);
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            try {
                tickerService.removeByIds(ids);
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        }
        return createRedirectView(page, keyword);

    }

    @RequestMapping(value = "manage.html", params = "publish")
    public ModelAndView publish(@RequestParam(value = "page", required = false) Long page,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            try {
                tickerService.publishByIds(ids);
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        }
        return createRedirectView(page, keyword);

    }


    @RequestMapping(value = "manage.html", params = "unpublish")
    public ModelAndView unpublish(@RequestParam(value = "page", required = false) Long page,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            try {
                tickerService.unpublishByIds(ids);
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        }
        return createRedirectView(page, keyword);

    }
}
