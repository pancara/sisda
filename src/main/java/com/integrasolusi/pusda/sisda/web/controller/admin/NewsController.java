package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.News;
import com.integrasolusi.pusda.sisda.persistence.Ticker;
import com.integrasolusi.pusda.sisda.service.NewsService;
import com.integrasolusi.pusda.sisda.service.TickerService;
import com.integrasolusi.pusda.sisda.web.form.NewsForm;
import com.integrasolusi.pusda.sisda.web.form.NewsPhotoForm;
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
import java.util.*;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 5:19 PM
 */
@Controller("adminNewsController")
@RequestMapping("/admin")
public class NewsController {
    private static Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    private NewsService newsService;
    @Autowired
    private TickerService tickerService;
    @Autowired
    private PagingHelper pagingHelper;

    @RequestMapping("news.html")
    public ModelAndView list(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return list(1L, keyword);
    }


    @RequestMapping("news/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        ModelAndView mav = new ModelAndView("admin/news/list");
        mav.addObject("keyword", keyword);

        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start_row", start);

        Long count = getCount(keyword);
        mav.addObject("count", count);

        java.util.List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        mav.addObject("last", pagingHelper.calcPageCount(count));
        
        List<News> newsList = getNewsList(keyword, start, pagingHelper.getRowPerPage());
        mav.addObject("newsList", newsList);
        return mav;
    }

    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return newsService.countAlls();
        else
            return newsService.countByKeyword(keyword);
    }

    private java.util.List<News> getNewsList(String keyword, Long start, Long count) {
        if (StringUtils.isEmpty(keyword))
            return newsService.get(start, count);
        else
            return newsService.getByKeyword(keyword, start, count);
    }

    @RequestMapping(value = "news/add.html", method = RequestMethod.GET)
    public ModelAndView createNew(@ModelAttribute("form") NewsForm form) {
        return new ModelAndView("admin/news/form");
    }

    @RequestMapping(value = "news/add.html", method = RequestMethod.POST)
    public ModelAndView createNew(@ModelAttribute("form") NewsForm form, Errors errors) {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            News news = new News();
            news.setTitle(form.getTitle());
            news.setShortDescription(form.getShortDescription());
            news.setContent(form.getFullContent());
            news.setPublishedDate(form.getPublishedDate().getCalendar().getTime());

            Date now = new Date();
            news.setPostDate(now);
            news.setLastEditDate(now);


            news.setPublished(false);
            newsService.save(news);
            return new ModelAndView("redirect:/admin/news.html");
        }
        return new ModelAndView("admin/news/form");
    }

    @RequestMapping(value = "news/edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") NewsForm form) {
        News news = newsService.findById(id);
        form.setTitle(news.getTitle());
        form.setShortDescription(news.getShortDescription());
        form.setFullContent(news.getContent());

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(news.getPublishedDate());
        form.getPublishedDate().setCalendar(cal);
        return new ModelAndView("admin/news/form");
    }

    @RequestMapping(value = "news/edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") NewsForm form, Errors errors) {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            News news = newsService.findById(id);
            news.setTitle(form.getTitle());
            news.setShortDescription(form.getShortDescription());
            news.setContent(form.getFullContent());
            news.setPublishedDate(form.getPublishedDate().getCalendar().getTime());

            news.setLastEditDate(new Date());
            newsService.save(news);
            return new ModelAndView("redirect:/admin/news.html");
        }
        return new ModelAndView("admin/news/form");
    }

    private void validateForm(NewsForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getTitle())) {
            errors.reject("title.empty", "Judul belum diisi");
        }

        if (StringUtils.isEmpty(form.getShortDescription())) {
            errors.reject("shortDescription.empty", "Keterangan singkat belum diisi");
        }

        if (StringUtils.isEmpty(form.getFullContent())) {
            errors.reject("fullContent.empty", "Berita lengkap belum diisi");
        }
    }


    @RequestMapping(value = "news/manage.html")
    public ModelAndView manage(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {
        return new ModelAndView(String.format("redirect:/admin/news/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "news/manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            newsService.removeByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/news/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "news/manage.html", params = "publish")
    public ModelAndView publish(@RequestParam(value = "page", required = false) Long page,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            newsService.publishByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/news/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "news/manage.html", params = "unpublish")
    public ModelAndView unpublish(@RequestParam(value = "page", required = false) Long page,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            newsService.unpublishByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/news/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "news/manage.html", params = "ticker")
    public ModelAndView createTicker(@RequestParam(value = "page", required = false) Long page,
                                     @RequestParam(value = "keyword", required = false) String keyword,
                                     @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            createTicker(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/news/%d.html?keyword=%s", page, keyword));

    }

    private void createTicker(Long[] ids) {
        List<Long> idList = new LinkedList<>();
        for (Long id : ids) {
            idList.add(id);
        }
        Collections.sort(idList);
        for (Long id : idList) {
            News news = newsService.findById(id);
            Ticker ticker = new Ticker();
            ticker.setTitle(news.getTitle());
            ticker.setUrl(createNewsUrl(news));
            tickerService.save(ticker);
        }
    }

    private String createNewsUrl(News news) {
        return String.format("/news/full/%d.html", news.getId());
    }


    @RequestMapping(value = "news/{id}/photo/clear.html")
    public ModelAndView removePhoto(@PathVariable("id") Long id,
                                    @RequestParam(value = "page", required = false) Long page,
                                    @RequestParam(value = "keyword", required = false) String keyword) throws IOException {
        newsService.removePhoto(id);
        return new ModelAndView(String.format("redirect:/admin/news/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "popup/news/picture/upload/{id}.html", method = RequestMethod.GET)
    public ModelAndView uploadPicture(@PathVariable("id") Long id,
                                      @ModelAttribute("form") NewsPhotoForm form) {
        ModelAndView mav = new ModelAndView("admin/news/photo_form");
        mav.addObject("news", newsService.findById(id));
        return mav;
    }

    @RequestMapping(value = "popup/news/picture/upload/{id}.html", method = RequestMethod.POST)
    public ModelAndView uploadPicture(@PathVariable("id") Long id,
                                      @ModelAttribute("form") NewsPhotoForm form, Errors errors) throws IOException {

        News news = newsService.findById(id);
        if (form.getFile() != null && !form.getFile().isEmpty()) {
            news.setPhotoFilename(form.getFile().getOriginalFilename());
            newsService.save(news, form.getFile().getInputStream());

            ModelAndView mav = new ModelAndView("admin/news/photo_form_success");
            mav.addObject("news", news);
            return mav;
        }

        ModelAndView mav = new ModelAndView("admin/news/photo_form");
        mav.addObject("news", news);
        return mav;
    }
}
