package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.Link;
import com.integrasolusi.pusda.sisda.persistence.LinkType;
import com.integrasolusi.pusda.sisda.service.LinkService;
import com.integrasolusi.pusda.sisda.service.LinkTypeService;
import com.integrasolusi.pusda.sisda.web.form.LinkForm;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 2:07 PM
 */
@Controller("adminLinkController")
@RequestMapping("/admin/link")
public class LinkController {
    private static Logger logger = Logger.getLogger(LinkController.class);
    @Autowired
    private LinkService linkService;

    @Autowired
    private LinkTypeService linkTypeService;


    @RequestMapping("list.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("admin/link/list");
        mav.addObject("count", linkService.countAlls());
        mav.addObject("linkList", linkService.findAlls());
        return mav;
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView createNew(@ModelAttribute("form") LinkForm form) throws IOException {
        ModelAndView mav = createFormView();
        return mav;
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView createNew(@ModelAttribute("form") LinkForm form,
                                  Errors errors) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            return createFormView();
        }

        Link link = new Link();
        link.setUrl(form.getUrl());
        link.setDescription(form.getDescription());
        LinkType type = form.getType() == null ?
                null :
                linkService.getTypeEntity(form.getType());
        link.setType(type);

        if (form.getPicture() != null && !form.getPicture().isEmpty()) {
            linkService.save(link, form.getPicture().getInputStream());
        } else {
            linkService.save(link);
        }
        return new ModelAndView("redirect:/admin/link/list.html");
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id,
                             @ModelAttribute("form") LinkForm form) throws IOException {

        Link link = linkService.findById(id);
        if (link == null) {
            return createRedirectView();
        }

        ModelAndView mav = createFormView();
        mav.addObject("link", link);

        form.setUrl(link.getUrl());
        form.setDescription(link.getDescription());
        if (link.getType() != null) {
            form.setType(link.getType().getId());
        }

        return mav;
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id,
                             @ModelAttribute("form") LinkForm form,
                             Errors errors) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            return createFormView();
        }

        Link link = linkService.findById(id);
        link.setUrl(form.getUrl());
        link.setDescription(form.getDescription());
        LinkType type = form.getType() == null ?
                null :
                linkTypeService.findById(form.getType());
        link.setType(type);

        if (form.getPicture() != null && !form.getPicture().isEmpty()) {
            link.setFilename(form.getPicture().getOriginalFilename());
            linkService.save(link, form.getPicture().getInputStream());
        } else {
            linkService.save(link);
        }
        return createRedirectView();
    }


    @RequestMapping("pic/{id}/**")
    public void getPicture(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        linkService.getBlob(id, response.getOutputStream());
    }

    @RequestMapping(value = "manage.html")
    public ModelAndView manage(@RequestParam(value = "ids", required = false) Long[] ids) {
        return createRedirectView();
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "ids", required = false) Long[] ids) {
        if (ids != null) {
            linkService.removeByIds(ids);
        }
        return createRedirectView();

    }

    @RequestMapping(value = "manage.html", params = "publish")
    public ModelAndView publish(@RequestParam(value = "ids", required = false) Long[] ids) {
        if (ids != null) {
            linkService.publishByIds(ids);
        }
        return createRedirectView();

    }

    @RequestMapping(value = "manage.html", params = "unpublish")
    public ModelAndView unpublish(@RequestParam(value = "ids", required = false) Long[] ids) {
        if (ids != null) {
            linkService.unpublishByIds(ids);
        }
        return createRedirectView();
    }

    private java.util.Map<Long, String> populateTypes() {
        List<LinkType> types = linkService.findAvailableTypes();
        java.util.Map<Long, String> mapTypes = new java.util.HashMap<Long, String>();
        for (LinkType type : types) {
            mapTypes.put(type.getId(), type.getDescription());
        }
        return mapTypes;
    }

    private ModelAndView createRedirectView() {
        return new ModelAndView("redirect:/admin/link/list.html");
    }

    private ModelAndView createFormView() {
        ModelAndView mav = new ModelAndView("admin/link/form");
        mav.addObject("types", populateTypes());
        return mav;
    }

    private void validateForm(LinkForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getUrl())) {
            errors.reject("url.empty", "URL belum diisi");
        }

        if (form.getType() == null) {
            errors.reject("type.empty", "Jenis link belum diisi");
        }
    }
}
