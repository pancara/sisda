package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.CommentPublication;
import com.integrasolusi.pusda.sisda.persistence.Publication;
import com.integrasolusi.pusda.sisda.persistence.PublicationAttachment;
import com.integrasolusi.pusda.sisda.service.PublicationAttachmentService;
import com.integrasolusi.pusda.sisda.service.PublicationService;
import com.integrasolusi.pusda.sisda.web.form.CommentForm;
import com.integrasolusi.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.apache.log4j.Logger;
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
import java.util.Map;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 9:03 AM
 */

@Controller
public class PublicationController {
    private static Logger logger = Logger.getLogger(PublicationController.class);
    private static String CAPTCHA_KEY = "publication.comment.captcha";
    private PublicationService publicationService;
    private PublicationAttachmentService publicationAttachmentService;
    private PagingHelper pagingHelper;
    private ImageUtils imageUtils;
    private ContentTypeUtils contentTypeUtils;


    @Autowired
    public void setContentTypeUtils(ContentTypeUtils contentTypeUtils) {
        this.contentTypeUtils = contentTypeUtils;
    }

    @Autowired
    public void setPublicationService(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @Autowired
    public void setPublicationAttachmentService(PublicationAttachmentService publicationAttachmentService) {
        this.publicationAttachmentService = publicationAttachmentService;
    }

    @Autowired
    public void setPagingHelper(PagingHelper pagingHelper) {
        this.pagingHelper = pagingHelper;
    }

    @Autowired
    public void setImageUtils(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    @RequestMapping("publication.html")
    public ModelAndView list(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return list(1L, keyword);
    }


    @RequestMapping("publication/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        ModelAndView mav = new ModelAndView("site/publication/publication");
        mav.addObject("keyword", keyword);

        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start_row", start);

        Long count = getCount(keyword);
        mav.addObject("last_page", pagingHelper.calcPageCount(count));
        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<Publication> publicationList = getPublicationList(keyword, start, pagingHelper.getRowPerPage());
        mav.addObject("publicationList", publicationList);
        Map<Publication, Boolean> titlePictures = new HashMap<Publication, Boolean>();
        for (Publication publication : publicationList) {
            titlePictures.put(publication, publicationService.hasTitlePicture(publication.getId()));
        }
        mav.addObject("titlePictures", titlePictures);

        return mav;
    }

    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return publicationService.countPublished();
        else
            return publicationService.countPublishedByKeyword(keyword);
    }

    private List<Publication> getPublicationList(String keyword, Long start, Long count) {
        if (StringUtils.isEmpty(keyword))
            return publicationService.getPublished(start, count);
        else
            return publicationService.getPublishedByKeyword(keyword, start, count);
    }

    @RequestMapping("/publication/full/{id}.html")
    public ModelAndView read(@PathVariable("id") Long id,
                             @ModelAttribute("form") CommentForm form) {
        ModelAndView mav = new ModelAndView("site/publication/full");
        Publication publication = publicationService.findById(id);
        mav.addObject("publication", publication);

        List<PublicationAttachment> attachments = publicationAttachmentService.findByPublication(publication);
        mav.addObject("documents", attachments);

        List<CommentPublication> comments = publicationService.getComments(id);
        mav.addObject("comments", comments);
        return mav;
    }

    @RequestMapping(value = "/publication/{id}/comment/post.html", method = RequestMethod.POST)
    public ModelAndView postComment(HttpServletRequest request,
                                    @PathVariable("id") Long publicationId, @ModelAttribute("form") CommentForm form,
                                    Errors errors) {
        validateCommentForm(request, form, errors);
        if (errors.hasErrors()) {
            form.setCaptcha(null);
        } else {
            CommentPublication comment = new CommentPublication();
            comment.setName(form.getName());
            comment.setEmail(form.getEmail());
            comment.setSite(form.getSite());
            comment.setMessage(form.getMessage());
            comment.setPostDate(new Date());
            Publication publication = publicationService.findById(publicationId);
            comment.setPublication(publication);
            publicationService.saveComment(comment);
            form.clear();
        }
        return read(publicationId, form);
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

    @RequestMapping("/publication/picture/{id}/**")
    public void downloadPicture(@PathVariable("id") Long id,
                                @RequestParam(value = "width", required = false) Integer width,
                                @RequestParam(value = "height", required = false) Integer height,
                                HttpServletResponse response) throws Exception {
        Publication publication = publicationService.findById(id);
        if (publication == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(publication.getPicture()));

        if (width == null && height == null) {
            publicationService.getPicture(id, response.getOutputStream());
        } else {
            int w = width == null ? 0 : width;
            int h = height == null ? 0 : height;
            publicationService.getPicture(id, response.getOutputStream(), w, h);
        }

    }


    @RequestMapping(value = "/publication/picture/{id}/info")
    public MappingJacksonJsonView remove(@PathVariable("id") Long id) throws IOException {
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        Publication publication = publicationService.findById(id);
        view.addStaticAttribute("empty", StringUtils.isEmpty(publication.getPicture()));
        view.addStaticAttribute("id", publication.getId());
        view.addStaticAttribute("filename", publication.getPicture());
        return view;
    }

    @RequestMapping(value = "/publication/attachment/{id}/**")
    public void downloadAttachment(HttpServletResponse response, @PathVariable(value = "id") Long id) throws IOException {
        PublicationAttachment attachment = publicationAttachmentService.findById(id);
        if (attachment != null) {
            String contentType = contentTypeUtils.getContentType(attachment.getFilename());
            response.setContentType(contentType);
            response.setContentLength(attachment.getSize().intValue());
            publicationAttachmentService.getBlob(id, response.getOutputStream());
        }
    }

    @RequestMapping(value = "/publication/comment/captcha.html")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        String captchaText = StringHelper.generateRandomText(4);
        ServletSessionHelper.setSessionAttribute(request, CAPTCHA_KEY, captchaText);
        try {
            imageUtils.createCaptchaImage(captchaText, response.getOutputStream());
        } catch (Exception e) {
            logger.error(e);
        }
    }

}
    