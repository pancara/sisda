package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.Slide;
import com.integrasolusi.pusda.sisda.service.SlideService;
import com.integrasolusi.pusda.sisda.web.form.SearchMapForm;
import com.integrasolusi.pusda.sisda.web.form.SlideForm;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.PagingHelper;
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
@Controller("adminSlideController")
@RequestMapping("/admin/slide")
public class SlideController {
    private static Logger logger = Logger.getLogger(SlideController.class);
    @Autowired
    private SlideService slideService;

    @Autowired
    private PagingHelper pagingHelper;

    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") SearchMapForm form) {
        return list(1L, form);
    }

    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @ModelAttribute("form") SearchMapForm form) {
        ModelAndView mav = new ModelAndView("admin/slide/list");
        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);

        Long count = getCount(form.getKeyword());
        mav.addObject("count", count);

        mav.addObject("last", pagingHelper.calcPageCount(count));
        mav.addObject("pages", pagingHelper.getDisplayedPages(page, count));
        mav.addObject("slideList", getSlidelist(form.getKeyword(), start, pagingHelper.getRowPerPage()));

        return mav;
    }

    private String createKeywordString(String text) {
        String keyword = String.format("%%%s%%", text);
        logger.info("keyword = " + keyword);
        return keyword;

    }

    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return slideService.countAlls();
        else
            return slideService.countByKeyword(createKeywordString(keyword));
    }

    private List<Slide> getSlidelist(String keyword, Long start, Long count) {
        if (StringUtils.isEmpty(keyword))
            return slideService.findAlls(start, count);
        else
            return slideService.findByKeyword(createKeywordString(keyword), start, count);

    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@RequestParam("page") Long page,
                            @ModelAttribute("form") SlideForm form) {
        return createFormView(null, page);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    private ModelAndView add(@RequestParam("page") Long page,
                             @ModelAttribute("form") SlideForm form,
                             Errors errors) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            return createFormView(null, page);
        }
        saveForm(null, form);

        return new ModelAndView(String.format("redirect:/admin/slide/page/%d.html", page));
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id,
                             @ModelAttribute("form") SlideForm form,
                             @RequestParam("page") Long page) throws IOException {
        Slide slide = slideService.findById(id);
        form.setTitle(slide.getTitle());
        form.setDescription(slide.getDescription());

        return createFormView(id, page);
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id,
                             @ModelAttribute("form") SlideForm form,
                             Errors errors,
                             @RequestParam("page") Long page) throws IOException {
        validateForm(form, errors);
        if (errors.hasErrors()) {
            createFormView(id, page);
        }
        saveForm(id, form);
        return new ModelAndView(String.format("redirect:/admin/slide/page/%d.html", page));
    }

    private ModelAndView createFormView(Long id, Long page) {
        ModelAndView mav = new ModelAndView("admin/slide/form");
        if (id != null) {
            mav.addObject("slide", slideService.findById(id));
        }
        mav.addObject("page", page);
        return mav;
    }

    private void validateForm(SlideForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getTitle())) {
            errors.reject("title.empty", "Judul belum diisi");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }
    }

    private void saveForm(Long id, SlideForm form) throws IOException {
        Slide slide = id == null ?
                new Slide() :
                slideService.findById(id);
        slide.setTitle(form.getTitle());
        slide.setDescription(form.getDescription());
        if (form.getFile() != null && !form.getFile().isEmpty()) {
            slide.setFilename(form.getFile().getOriginalFilename());
            slideService.save(slide, form.getFile().getInputStream());
        } else {
            slideService.save(slide);
        }
    }


    @RequestMapping(value = "manage.html")
    public ModelAndView manage(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {
        return createRedirectView(page, keyword);
    }

    private ModelAndView createRedirectView(Long page, String keyword) {
        return new ModelAndView(String.format("redirect:/admin/slide/page/%d.html?keyword=%s", page, keyword));
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            try {
                slideService.removeByIds(ids);
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        }
        return createRedirectView(page, keyword);

    }

    @RequestMapping(value = "manage.html", params = "publish")
    public ModelAndView publish(@RequestParam(value = "page", required = false) Long page,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            slideService.publishByIds(ids);
        }
        return createRedirectView(page, keyword);

    }

    @RequestMapping(value = "manage.html", params = "unpublish")
    public ModelAndView unpublish(@RequestParam(value = "page", required = false) Long page,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            slideService.unpublishByIds(ids);
        }
        return createRedirectView(page, keyword);

    }

    @RequestMapping("{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Slide slide = slideService.findById(id);
        if (slide != null) {
            response.setContentType(contentTypeUtils.getContentType(slide.getFilename()));
            slideService.getBlob(id, response.getOutputStream());
            response.getOutputStream().flush();
        }
    }

    @RequestMapping("thumb/{id}/**")
    public void thumb(@PathVariable("id") Long id,
                      @RequestParam(value = "width", required = false, defaultValue = "100") Integer width,
                      @RequestParam(value = "height", required = false, defaultValue = "0") Integer height,
                      HttpServletResponse response) throws IOException {
        Slide slide = slideService.findById(id);
        if (slide != null) {
            response.setContentType(contentTypeUtils.getContentType(slide.getFilename()));
            slideService.getBlob(id, width, height, response.getOutputStream());
            response.getOutputStream().flush();
        }
    }


}
