package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.CommentPublication;
import com.integrasolusi.pusda.sisda.service.CommentPublicationService;
import com.integrasolusi.pusda.sisda.web.form.SearchForm;
import com.integrasolusi.utils.PagingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/21/11
 * Time         : 2:21 PM
 */
@Controller("adminCommentPublicationController")
@RequestMapping("/admin")
public class CommentPublicationController {
    private CommentPublicationService commentPublicationService;
    private PagingHelper pagingHelper;

    @Autowired
    public void setCommentPublicationService(CommentPublicationService commentPublicationService) {
        this.commentPublicationService = commentPublicationService;
    }

    @Autowired
    public void setPagingHelper(PagingHelper pagingHelper) {
        this.pagingHelper = pagingHelper;
    }

    @RequestMapping("comment/publication.html")
    public ModelAndView list(HttpServletRequest request,
                             @ModelAttribute("form") SearchForm form) {
        return list(request, form, 1L);
    }

    @RequestMapping(value = "comment/publication/{page}.html")
    public ModelAndView list(HttpServletRequest request,
                             @ModelAttribute("form") SearchForm form,
                             @PathVariable(value = "page") Long page) {

        ModelAndView mav = new ModelAndView("admin/comment/publication/list");

        mav.addObject("current", page);
        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);

        Long count = getCount(form.getKeyword());
        mav.addObject("count", count);

        java.util.List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        mav.addObject("last", pagingHelper.calcPageCount(count));
        
        java.util.List<CommentPublication> comments = getComments(form.getKeyword(), start, pagingHelper.getRowPerPage());
        mav.addObject("comments", comments);

        return mav;
    }

    private Long getCount(String keyword) {
        if (org.apache.commons.lang.StringUtils.isEmpty(keyword)) {
            return commentPublicationService.countAlls();
        } else {
            String searchToken = "%" + keyword + "%";
            return commentPublicationService.countByKeyword(searchToken);
        }
    }

    private List<CommentPublication> getComments(String keyword, Long start, Long count) {
        if (org.apache.commons.lang.StringUtils.isEmpty(keyword)) {
            return commentPublicationService.get(start, count);
        } else {
            String searchToken = "%" + keyword + "%";
            return commentPublicationService.get(searchToken, start, count);
        }

    }

    @RequestMapping(value = "comment/publication/remove.html")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            commentPublicationService.removeByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/comment/publication/%d.html?keyword=%s", page, keyword));

    }

}
