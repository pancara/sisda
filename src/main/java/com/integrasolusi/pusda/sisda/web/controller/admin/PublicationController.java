package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.Publication;
import com.integrasolusi.pusda.sisda.persistence.PublicationAttachment;
import com.integrasolusi.pusda.sisda.persistence.Ticker;
import com.integrasolusi.pusda.sisda.service.PublicationAttachmentService;
import com.integrasolusi.pusda.sisda.service.PublicationService;
import com.integrasolusi.pusda.sisda.service.TickerService;
import com.integrasolusi.pusda.sisda.web.form.AttachmentForm;
import com.integrasolusi.pusda.sisda.web.form.PublicationForm;
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
@Controller("adminPublicationController")
@RequestMapping("/admin")
public class PublicationController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(PublicationController.class);

    @Autowired
    private PublicationService publicationService;

    @Autowired
    private TickerService tickerService;

    @Autowired
    private PublicationAttachmentService publicationAttachmentService;

    @Autowired
    private PagingHelper pagingHelper;

    @RequestMapping("publication.html")
    public ModelAndView list(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return list(1L, keyword);
    }


    @RequestMapping("publication/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        ModelAndView mav = new ModelAndView("admin/publication/list");
        mav.addObject("keyword", keyword);

        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);

        Long count = getCount(keyword);
        mav.addObject("count", count);

        java.util.List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        mav.addObject("last", pagingHelper.calcPageCount(count));

        List<Publication> publicationList = getPublicationList(keyword, start, pagingHelper.getRowPerPage());
        mav.addObject("publicationList", publicationList);
        return mav;
    }

    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return publicationService.countAlls();
        else
            return publicationService.countByKeyword(keyword);
    }

    private java.util.List<Publication> getPublicationList(String keyword, Long start, Long count) {
        if (StringUtils.isEmpty(keyword))
            return publicationService.get(start, count);
        else
            return publicationService.getByKeyword(keyword, start, count);
    }

    @RequestMapping(value = "publication/add.html", method = RequestMethod.GET)
    public ModelAndView createNew(@ModelAttribute("form") PublicationForm form) {
        return new ModelAndView("admin/publication/form");
    }

    @RequestMapping(value = "publication/add.html", method = RequestMethod.POST)
    public ModelAndView createNew(@ModelAttribute("form") PublicationForm form, Errors errors) {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Publication publication = new Publication();
            publication.setTitle(form.getTitle());
            publication.setShortDescription(form.getShortDescription());
            publication.setContent(form.getFullContent());
            publication.setPublishedDate(form.getPublishedDate().getCalendar().getTime());

            Date now = new Date();
            publication.setPostDate(now);
            publication.setLastEditDate(now);


            publication.setPublished(false);
            publicationService.save(publication);
            return new ModelAndView("redirect:/admin/publication.html");
        }
        return new ModelAndView("admin/publication/form");
    }

    @RequestMapping(value = "publication/edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") PublicationForm form) {
        Publication publication = publicationService.findById(id);
        form.setTitle(publication.getTitle());
        form.setShortDescription(publication.getShortDescription());
        form.setFullContent(publication.getContent());

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(publication.getPublishedDate());
        form.getPublishedDate().setCalendar(cal);
        return new ModelAndView("admin/publication/form");
    }

    @RequestMapping(value = "publication/edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") PublicationForm form, Errors errors) {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Publication publication = publicationService.findById(id);
            publication.setTitle(form.getTitle());
            publication.setShortDescription(form.getShortDescription());
            publication.setContent(form.getFullContent());
            publication.setPublishedDate(form.getPublishedDate().getCalendar().getTime());

            publication.setLastEditDate(new Date());

            publicationService.save(publication);
            return new ModelAndView("redirect:/admin/publication.html");
        }
        return new ModelAndView("admin/publication/form");
    }

    private void validateForm(PublicationForm form, Errors errors) {
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


    @RequestMapping(value = "publicaton/manage.html")
    public ModelAndView manage(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {
        return new ModelAndView(String.format("redirect:/admin/publication/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "publicaton/manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            publicationService.removeByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/publication/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "publication/manage.html", params = "publish")
    public ModelAndView publish(@RequestParam(value = "page", required = false) Long page,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            publicationService.publishByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/publication/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "publication/manage.html", params = "unpublish")
    public ModelAndView unpublish(@RequestParam(value = "page", required = false) Long page,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            publicationService.unpublishByIds(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/publication/%d.html?keyword=%s", page, keyword));

    }

    @RequestMapping(value = "publication/manage.html", params = "ticker")
    public ModelAndView createTicker(@RequestParam(value = "page", required = false) Long page,
                                     @RequestParam(value = "keyword", required = false) String keyword,
                                     @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            createTicker(ids);
        }
        return new ModelAndView(String.format("redirect:/admin/publication/%d.html?keyword=%s", page, keyword));

    }

    private void createTicker(Long[] ids) {
        List<Long> idList = new LinkedList<>();
        for (Long id : ids) {
            idList.add(id);
        }
        Collections.sort(idList);
        for (Long id : idList) {
            Publication publication = publicationService.findById(id);
            Ticker ticker = new Ticker();
            ticker.setTitle(publication.getTitle());
            ticker.setUrl(createPublicationUrl(publication));
            tickerService.save(ticker);
        }
    }

    private String createPublicationUrl(Publication publication) {
        return String.format("/publication/full/%d.html", publication.getId());
    }

    @RequestMapping(value = "publication/{id}/photo/clear.html")
    public ModelAndView removePhoto(@PathVariable("id") Long id,
                                    @RequestParam(value = "page", required = false) Long page,
                                    @RequestParam(value = "keyword", required = false) String keyword) {
        publicationService.removePicture(id);
        return new ModelAndView(String.format("redirect:/admin/publication/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "popup/publication/picture/upload/{id}.html", method = RequestMethod.GET)
    public ModelAndView uploadPicture(@PathVariable("id") Long id,
                                      @ModelAttribute("form") RepositoryPhotoForm form) {
        ModelAndView mav = new ModelAndView("admin/publication/photo_form");
        mav.addObject("publication", publicationService.findById(id));
        return mav;
    }

    @RequestMapping(value = "popup/publication/picture/upload/{id}.html", method = RequestMethod.POST)
    public ModelAndView uploadPicture(@PathVariable("id") Long id,
                                      @ModelAttribute("form") RepositoryPhotoForm form, Errors errors) throws IOException {

        Publication publication = publicationService.findById(id);
        if (form.getFile() != null && !form.getFile().isEmpty()) {
            publication.setPicture(form.getFile().getOriginalFilename());
            publicationService.save(publication, form.getFile().getInputStream());

            ModelAndView mav = new ModelAndView("admin/publication/photo_form_success");
            mav.addObject("publication", publication);
            return mav;
        }

        ModelAndView mav = new ModelAndView("admin/publication/photo_form");
        mav.addObject("publication", publication);
        return mav;
    }

    @RequestMapping(value = "popup/publication/attachment/{id}/manage.html", method = RequestMethod.GET)
    public ModelAndView uploadAttachment(@PathVariable("id") Long id,
                                         @ModelAttribute("form") AttachmentForm form) {
        return createUploadAttachmentView(id);
    }

    @RequestMapping(value = "popup/publication/attachment/{id}/manage.html", method = RequestMethod.POST)
    public ModelAndView uploadAttachment(@PathVariable("id") Long id,
                                         @ModelAttribute("form") AttachmentForm form, Errors errors) throws IOException {

        Publication publication = publicationService.findById(id);
        if (form.getFile() != null && !form.getFile().isEmpty()) {
            PublicationAttachment attachment = new PublicationAttachment();
            attachment.setPublication(publication);
            attachment.setDescription(form.getDescription());
            attachment.setFilename(form.getFile().getOriginalFilename());
            attachment.setSize(form.getFile().getSize());
            publicationAttachmentService.save(attachment, form.getFile().getInputStream());
        }

        return createUploadAttachmentView(id);
    }

    private ModelAndView createUploadAttachmentView(Long id) {
        ModelAndView mav = new ModelAndView("admin/publication/attachment_manage");
        Publication publication = publicationService.findById(id);
        mav.addObject("publication", publication);
        mav.addObject("attachments", publicationAttachmentService.findByPublication(publication));

        return mav;
    }

    @RequestMapping(value = "popup/publication/attachment/{id}/remove.html")
    public ModelAndView remove(@PathVariable("id") Long attachmentId) throws IOException {
        PublicationAttachment attachment = publicationAttachmentService.findById(attachmentId);
        if (attachment != null) {
            publicationAttachmentService.removeById(attachmentId);
        }
        return new ModelAndView(String.format("redirect:/admin/popup/publication/attachment/{id}/manage.html", attachmentId));
    }
}
