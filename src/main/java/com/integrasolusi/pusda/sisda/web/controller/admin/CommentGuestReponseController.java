package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.CommentGuest;
import com.integrasolusi.pusda.sisda.persistence.CommentGuestResponse;
import com.integrasolusi.pusda.sisda.service.CommentGuestResponseService;
import com.integrasolusi.pusda.sisda.service.CommentGuestService;
import com.integrasolusi.pusda.sisda.web.form.CommentGuestResponseForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Programmer   : pancara
 * Date         : 6/21/11
 * Time         : 2:21 PM
 */
@Controller("adminCommentGuestResponseController")
@RequestMapping("/admin/comment/guest/response/")
public class CommentGuestReponseController {
    @Autowired
    private CommentGuestService commentGuestService;

    @Autowired
    private CommentGuestResponseService commentGuestResponseService;


    @RequestMapping(value = "add/{commentId}.html", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable(value = "commentId") Long commentId,
                            @ModelAttribute("form") CommentGuestResponseForm form) {
        CommentGuest comment = commentGuestService.findById(commentId);
        if (comment == null) {
            return new ModelAndView("redirect:/admin/comment/guest.html");
        }

        ModelAndView mav = new ModelAndView("admin/comment/guest/response/form");
        mav.addObject("comment", comment);
        return mav;
    }

    @RequestMapping(value = "add/{commentId}.html", method = RequestMethod.POST)
    public ModelAndView createNew(@PathVariable(value = "commentId") Long commentId,
                                  @ModelAttribute("form") CommentGuestResponseForm form,
                                  Errors errors) {
        CommentGuest comment = commentGuestService.findById(commentId);
        if (comment == null) {
            return new ModelAndView("redirect:/admin/comment/guest.html");
        }

        validateForm(form, errors);
        if (!errors.hasErrors()) {
            CommentGuestResponse response = new CommentGuestResponse();
            response.setText(form.getText());
            response.setBy(form.getBy());
            response.setPostDate(form.getPostDate().getCalendar().getTime());

            response.setCommentGuest(commentGuestService.findById(commentId));
            commentGuestResponseService.save(response);

            return new ModelAndView("redirect:/admin/comment/guest.html");
        }

        if (comment == null) {
            return new ModelAndView("redirect:/admin/comment/guest.html");
        }

        ModelAndView mav = new ModelAndView("admin/comment/guest/response/form");
        mav.addObject("comment", comment);
        return mav;
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(value = "id") Long id,
                             @ModelAttribute("form") CommentGuestResponseForm form) {
        CommentGuestResponse response = commentGuestResponseService.findById(id);
        if (response == null) {
            return new ModelAndView("redirect:/admin/comment/guest.html");
        }

        ModelAndView mav = new ModelAndView("admin/comment/guest/response/form");
        form.setBy(response.getBy());
        form.setText(response.getText());

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(response.getPostDate());
        form.getPostDate().setCalendar(cal);

        mav.addObject("comment", commentGuestResponseService.findResponseFor(response));
        return mav;
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable(value = "id") Long id,
                             @ModelAttribute("form") CommentGuestResponseForm form,
                             Errors errors) {
        CommentGuestResponse response = commentGuestResponseService.findById(id);
        if (response == null) {
            return new ModelAndView("redirect:/admin/comment/guest.html");
        }

        validateForm(form, errors);
        if (!errors.hasErrors()) {
            response.setText(form.getText());
            response.setBy(form.getBy());
            response.setPostDate(form.getPostDate().getCalendar().getTime());
            commentGuestResponseService.save(response);

            return new ModelAndView("redirect:/admin/comment/guest.html");
        }

        ModelAndView mav = new ModelAndView("admin/comment/guest/response/form");
        mav.addObject("comment", commentGuestResponseService.findResponseFor(response));
        return mav;
    }

    private void validateForm(CommentGuestResponseForm form, Errors errors) {

        if (StringUtils.isEmpty(form.getText())) {
            errors.reject("text.empty", "Jawaban belum diisi");
        }

        if (StringUtils.isEmpty(form.getBy())) {
            errors.reject("by.empty", "Pemberi tanggapan belum diisi");
        }
    }


    @RequestMapping(value = "delete/{id}.html", method = RequestMethod.GET)
    public ModelAndView remove(@PathVariable(value = "id") Long id,
                               @RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword) {
        commentGuestResponseService.removeById(id);
        String url = String.format("redirect:/admin/comment/guest/%d.html?keyword=%s", page, keyword);
        return new ModelAndView(url);
    }


}
