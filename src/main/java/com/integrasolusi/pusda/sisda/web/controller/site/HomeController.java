package com.integrasolusi.pusda.sisda.web.controller.site;

/**
 * Programmer   : pancara
 * Date         : 6/1/11
 * Time         : 4:53 PM
 */

import com.integrasolusi.pusda.sisda.persistence.*;
import com.integrasolusi.pusda.sisda.service.*;
import com.integrasolusi.pusda.sisda.web.form.GuestMessageForm;
import org.jasypt.util.text.TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : Dec 31, 2010
 * Time         : 6:53:58 PM
 */
@Controller
public class HomeController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private PublicationService publicationService;
    
    @Autowired
    private SlideService slideService;

    @Autowired
    private CommentGuestService commentGuestService;

    @Autowired
    private TextEncryptor textEncryptor;
 

    @RequestMapping(value = "home")
    public ModelAndView home(@ModelAttribute("form") GuestMessageForm form) {
        ModelAndView mav = new ModelAndView("home");
        List<News> newsList = newsService.getLatest();
        mav.addObject("newsList", newsList);

        java.util.Map<News, Boolean> newsTitlePictures = new HashMap<News, Boolean>();
        for (News news : newsList)
            newsTitlePictures.put(news, newsService.hasPicture(news.getId()));
        mav.addObject("newsTitlePictures", newsTitlePictures);

        List<Publication> publicationList = publicationService.getLatest();
        mav.addObject("publicationList", publicationList);
        java.util.Map<Publication, Boolean> publicationTitlePictures = new HashMap<Publication, Boolean>();
        for (Publication publication : publicationList)
            publicationTitlePictures.put(publication, newsService.hasPicture(publication.getId()));
        mav.addObject("publicationTitlePictures", publicationTitlePictures);

        List<Agenda> agendaList = agendaService.getNearest(new java.util.Date());
        mav.addObject("agendaList", agendaList);

        mav.addObject("slide", slideService.getRandom());

        java.util.Map<CommentGuest, List<CommentGuestResponse>> commentGuestMap = new LinkedHashMap<CommentGuest, List<CommentGuestResponse>>();

        for (CommentGuest comment : commentGuestService.get(0L, 8L)) {
            commentGuestMap.put(comment, commentGuestService.findResponse(comment));
        }
        mav.addObject("commentGuestMap", commentGuestMap);

        return mav;
    }


}
