package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.CommentNews;
import com.integrasolusi.pusda.sisda.persistence.News;
import com.integrasolusi.pusda.sisda.service.NewsService;
import com.integrasolusi.pusda.sisda.web.form.CommentForm;
import com.integrasolusi.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.jasypt.util.text.TextEncryptor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 9:03 AM
 */

@Controller
public class NewsController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(NewsController.class);
    private static String CAPTCHA_KEY = "news.comment.captcha";
    @Autowired
    private NewsService newsService;

    @Autowired
    private PagingHelper pagingHelper;

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private TextEncryptor textEncryptor;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @Autowired
    private FileCacheManager fileCacheManager;


    @RequestMapping("news.html")
    public ModelAndView list(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return list(1L, keyword);
    }


    @RequestMapping("news/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        ModelAndView mav = new ModelAndView("site/news/list");
        mav.addObject("keyword", keyword);

        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start_row", start);

        Long count = getCount(keyword);
        mav.addObject("last_page", pagingHelper.calcPageCount(count));
        java.util.List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<News> newsList = getNewsList(keyword, start, pagingHelper.getRowPerPage());
        mav.addObject("newsList", newsList);
        java.util.Map<News, Boolean> titlePictures = new HashMap<News, Boolean>();
        for (News news : newsList) {
            titlePictures.put(news, newsService.hasPicture(news.getId()));
        }
        mav.addObject("titlePictures", titlePictures);

        return mav;
    }

    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return newsService.countPublished();
        else
            return newsService.countPublishedByKeyword(keyword);
    }

    private java.util.List<News> getNewsList(String keyword, Long start, Long count) {
        if (StringUtils.isEmpty(keyword))
            return newsService.getPublished(start, count);
        else
            return newsService.getPublishedByKeyword(keyword, start, count);
    }

    @RequestMapping("/news/full/{id}.html")
    public ModelAndView read(@PathVariable("id") Long id,
                             @ModelAttribute("form") CommentForm form) {
        ModelAndView mav = new ModelAndView("site/news/full");
        News news = newsService.findById(id);
        mav.addObject("news", news);
        List<CommentNews> comments = newsService.getComments(id);
        mav.addObject("comments", comments);
        return mav;
    }

    @RequestMapping(value = "/news/{id}/comment/post.html", method = RequestMethod.POST)
    public ModelAndView postComment(HttpServletRequest request,
                                    @PathVariable("id") Long newsId, @ModelAttribute("form") CommentForm form,
                                    Errors errors) {


        validateCommentForm(request, form, errors);
        if (!errors.hasErrors()) {
            CommentNews comment = new CommentNews();
            comment.setName(form.getName());
            comment.setEmail(form.getEmail());
            comment.setSite(form.getSite());
            comment.setMessage(form.getMessage());
            comment.setPostDate(new Date());
            News news = newsService.findById(newsId);
            comment.setNews(news);
            newsService.saveComment(comment);
        }
        return read(newsId, form);
    }

    @RequestMapping(value = "/news/comment/captcha.html")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        String captchaText = StringHelper.generateRandomText(4);
        ServletSessionHelper.setSessionAttribute(request, CAPTCHA_KEY, captchaText);
        try {
            imageUtils.createCaptchaImage(captchaText, response.getOutputStream());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void validateCommentForm(HttpServletRequest request, CommentForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

        if (StringUtils.isEmpty(form.getEmail())) {
            errors.reject("email.empty", "Email belum diisi");
        } else {
            if (!EmailValidator.getInstance().isValid(form.getEmail())) {
                errors.reject("email.not.valid", "Email tidak valid");
            }
        }

        if (StringUtils.isEmpty(form.getMessage())) {
            errors.reject("message.empty", "Pesan belum diisi");
        }

        String sessionCaptcha = (String) ServletSessionHelper.getSessionAttribute(request, CAPTCHA_KEY);
        logger.info("session captcha " + sessionCaptcha);
        logger.info("form captcha " + form.getCaptcha());
        ServletSessionHelper.removeSessionAttribute(request, CAPTCHA_KEY);
        if (!StringUtils.equalsIgnoreCase(sessionCaptcha, form.getCaptcha())) {
            errors.reject("captcha.not.valid", "Kode keamanan tidak valid");
        }
    }

    @RequestMapping("/news/latest.html")
    public ModelAndView getLatest() {
        ModelAndView mav = new ModelAndView("site/news/latest");
        List<News> newsList = newsService.getLatest(8L);
        mav.addObject("newsList", newsList);

        java.util.Map<News, Boolean> titlePictures = new HashMap<News, Boolean>();
        for (News news : newsList) {
            titlePictures.put(news, newsService.hasPicture(news.getId()));
        }
        mav.addObject("titlePictures", titlePictures);
        return mav;
    }


    @RequestMapping("/news/photo/{id}/**")
    public void downloadNewsPhoto(@PathVariable("id") Long id,
                                  @RequestParam(value = "width", required = false) Integer width,
                                  @RequestParam(value = "height", required = false) Integer height,
                                  HttpServletResponse response) throws Exception {
        News news = newsService.findById(id);
        if (news == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (width == null && height == null) {
            response.setContentType(contentTypeUtils.getContentType(news.getPhotoFilename()));
            newsService.getHeadlinePictureBlob(id, response.getOutputStream());
        } else {

            int w = width == null ? 0 : width;
            int h = height == null ? 0 : height;
            response.setContentType(contentTypeUtils.getContentType(news.getPhotoFilename()));
            newsService.getHeadlinePictureBlob(id, response.getOutputStream(), w, h);
        }
    }

    @RequestMapping(value = "/news/photo/{id}/info")
    public MappingJacksonJsonView remove(@PathVariable("id") Long id) throws IOException {
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        News news = newsService.findById(id);
        view.addStaticAttribute("empty", StringUtils.isEmpty(news.getPhotoFilename()));
        view.addStaticAttribute("id", news.getId());
        view.addStaticAttribute("filename", news.getPhotoFilename());
        return view;
    }

}
