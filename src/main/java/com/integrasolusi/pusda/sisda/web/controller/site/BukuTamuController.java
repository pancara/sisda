package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.CommentGuest;
import com.integrasolusi.pusda.sisda.persistence.CommentGuestResponse;
import com.integrasolusi.pusda.sisda.service.CommentGuestService;
import com.integrasolusi.pusda.sisda.web.form.GuestMessageForm;
import com.integrasolusi.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;
import org.jasypt.util.text.TextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/12/11
 * Time         : 2:48 PM
 */


@Controller
public class BukuTamuController {
    private final static Logger logger = LoggerFactory.getLogger(BukuTamuController.class);
    private final static String CAPTCHA_KEY = "buku_tamu.captcha";
    private final static String ATTEMPT_COUNTER_KEY = "buku_tamu.attempt.counter";

    private PagingHelper pagingHelper;

    private CommentGuestService commentGuestService;

    private TextEncryptor textEncryptor;

    private ImageUtils imageUtils;
    private ContentTypeUtils contentTypeUtils;

    private Integer maximumAttempt = 10;


    @RequestMapping("/buku_tamu.html")
    public ModelAndView list(HttpServletRequest request,
                             @ModelAttribute("form") GuestMessageForm form) {
        return list(request, form, 1L);
    }

    @RequestMapping(value = "/buku_tamu/page/{page}.html")
    public ModelAndView list(HttpServletRequest request,
                             @ModelAttribute("form") GuestMessageForm form,
                             @PathVariable(value = "page") Long page) {

        ModelAndView mav = new ModelAndView("site/buku_tamu");

        mav.addObject("current", page);
        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start_row", start);

        Long count = commentGuestService.countAlls();
        mav.addObject("last_page", pagingHelper.calcPageCount(count));

        java.util.List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        java.util.Map<CommentGuest, List<CommentGuestResponse>> commentGuestMap = new LinkedHashMap<CommentGuest, List<CommentGuestResponse>>();
        for (CommentGuest comment : commentGuestService.get(start, pagingHelper.getRowPerPage())) {
            commentGuestMap.put(comment, commentGuestService.findResponse(comment));
        }
        mav.addObject("commentGuestMap", commentGuestMap);

        return mav;
    }

    @RequestMapping(value = "/buku_tamu/post.html", method = RequestMethod.POST)
    public ModelAndView post(HttpServletRequest request,
                             @RequestParam(value = "key", defaultValue = "") String key,
                             @ModelAttribute("form") GuestMessageForm form,
                             Errors errors) {
        validateForm(request, form, key, errors);

        if (errors.hasErrors()) {
            return new ModelAndView("site/buku_tamu_form");
        } else {
            CommentGuest message = new CommentGuest();
            message.setName(form.getName());
            message.setEmail(form.getEmail());
            message.setPhone(form.getPhone());
            message.setPostDate(new java.util.Date());
            message.setMessage(form.getMessage());

            commentGuestService.postComment(message);
            ServletSessionHelper.removeSessionAttribute(request, ATTEMPT_COUNTER_KEY);

            return new ModelAndView("redirect:/buku_tamu.html");
        }
    }

    private void validateForm(HttpServletRequest request, GuestMessageForm form,
                              String key, Errors errors) {
        Integer nAttempt = (Integer) ServletSessionHelper.getSessionAttribute(request, ATTEMPT_COUNTER_KEY);
        if (nAttempt == null) {
            nAttempt = 0;
        }

        if (nAttempt >= maximumAttempt) {
            errors.reject("captcha.not.valid", "Captcha tidak valid");
        }

        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi.");
        }

        if (StringUtils.isEmpty(form.getEmail())) {
            errors.reject("email.empty", "Email belum diisi.");
        } else {
            if (!EmailValidator.getInstance().isValid(form.getEmail())) {
                errors.reject("email.not.valid", "Email tidak valid.");
            }
        }

        if (StringUtils.isEmpty(form.getMessage())) {
            errors.reject("message.empty", "Pesan belum diisi");
        }


        if (StringUtils.isEmpty(form.getCaptcha())) {
            errors.reject("captcha.empty", "Captcha belum diisi");
        } else {

            nAttempt++;

            ServletSessionHelper.setSessionAttribute(request, ATTEMPT_COUNTER_KEY, nAttempt);

            String captchaKey = CAPTCHA_KEY + key;
            String sessionCaptchaText = (String) ServletSessionHelper.getAndClearSessionAttribute(request, captchaKey);
            if (!StringUtils.equalsIgnoreCase(form.getCaptcha(), sessionCaptchaText)) {
                errors.reject("captcha.not.valid", "Isikan captcha sama dengan teks yang ditampilkan.");
            }
        }
    }

    @RequestMapping(value = "/buku_tamu/email.html", method = RequestMethod.GET)
    public void email(HttpServletResponse response,
                      @RequestParam(value = "code", required = true, defaultValue = "xxx") String hexCode) {
        String code = StringHelper.hexToString(hexCode);
        String email = textEncryptor.decrypt(code);
        try {
            imageUtils.createTextImage(email, response.getOutputStream());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/buku_tamu/captcha.html", method = RequestMethod.GET)
    public void captcha(HttpServletRequest request,
                        @RequestParam(value = "key", defaultValue = "") String key,
                        HttpServletResponse response) {

        String captchaKey = CAPTCHA_KEY + key;
        String captchaText = StringHelper.generateRandomText(4);

        ServletSessionHelper.setSessionAttribute(request, captchaKey, captchaText);
        try {
            String filename = "file.jpg";
            response.setContentType(contentTypeUtils.getContentType(filename));
            imageUtils.createCaptchaImage(captchaText, response.getOutputStream());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Autowired
    public void setPagingHelper(PagingHelper pagingHelper) {
        this.pagingHelper = pagingHelper;
    }

    @Autowired
    public void setCommentGuestService(CommentGuestService commentGuestService) {
        this.commentGuestService = commentGuestService;
    }

    @Autowired
    public void setTextEncryptor(TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }

    @Autowired
    public void setImageUtils(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    @Autowired
    public void setContentTypeUtils(ContentTypeUtils contentTypeUtils) {
        this.contentTypeUtils = contentTypeUtils;
    }
}
