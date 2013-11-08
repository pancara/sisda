package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.CommentGuest;
import com.integrasolusi.pusda.sisda.persistence.CommentGuestResponse;
import com.integrasolusi.pusda.sisda.service.CommentGuestService;
import com.integrasolusi.pusda.sisda.web.form.SearchForm;
import com.integrasolusi.utils.PagingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Programmer   : pancara
 * Date         : 6/21/11
 * Time         : 2:21 PM
 */
@Controller("adminCommentGuestController")
@RequestMapping("/admin")
public class CommentGuestController {
    private CommentGuestService commentGuestService;
    private PagingHelper pagingHelper;

    @Autowired
    public void setCommentGuestService(CommentGuestService commentGuestService) {
        this.commentGuestService = commentGuestService;
    }

    @Autowired
    public void setPagingHelper(PagingHelper pagingHelper) {
        this.pagingHelper = pagingHelper;
    }

    @RequestMapping("comment/guest.html")
    public ModelAndView list(@ModelAttribute("form") SearchForm form) {
        return list(form, 1L);
    }

    @RequestMapping(value = "comment/guest/{page}.html")
    public ModelAndView list(@ModelAttribute("form") SearchForm form,
                             @PathVariable(value = "page") Long page) {

        ModelAndView mav = new ModelAndView("admin/comment/guest/list");

        mav.addObject("current", page);
        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start_row", start);

        if (org.apache.commons.lang.StringUtils.isEmpty(form.getKeyword())) {
            Long count = commentGuestService.countAlls();
            mav.addObject("count", count);

            java.util.List<Long> pages = pagingHelper.getDisplayedPages(page, count);
            mav.addObject("pages", pages);
            
            mav.addObject("last", pagingHelper.calcPageCount(count));
            
            Map<CommentGuest, List<CommentGuestResponse>> commentResponseMap = new LinkedHashMap<>();
            java.util.List<CommentGuest> comments = commentGuestService.get(start, pagingHelper.getRowPerPage());
            for (CommentGuest comment : comments) {
                commentResponseMap.put(comment, commentGuestService.findResponse(comment));

            }
            mav.addObject("commentResponseMap", commentResponseMap);
        } else {
            Long count = commentGuestService.countByKeyword(form.getKeyword());
            mav.addObject("count", count);

            java.util.List<Long> pages = pagingHelper.getDisplayedPages(page, count);
            mav.addObject("pages", pages);
            
            mav.addObject("last", pagingHelper.calcPageCount(count));
            
            Map<CommentGuest, List<CommentGuestResponse>> commentResponseMap = new LinkedHashMap<>();
            java.util.List<CommentGuest> comments = commentGuestService.get(form.getKeyword(), start, pagingHelper.getRowPerPage());
            for (CommentGuest comment : comments) {
                commentResponseMap.put(comment, commentGuestService.findResponse(comment));

            }
            mav.addObject("commentResponseMap", commentResponseMap);
        }

        return mav;
    }

    @RequestMapping(value = "comment/guest/remove.html")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] deleteIds) {

        if (deleteIds != null && deleteIds.length > 0) {
            commentGuestService.removeByIds(deleteIds);
        }

        return new ModelAndView(String.format("redirect:/admin/comment/guest/%d.html?keyword=%s", page, keyword));

    }

}
