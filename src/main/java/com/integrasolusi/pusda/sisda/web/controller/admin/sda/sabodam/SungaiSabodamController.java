package com.integrasolusi.pusda.sisda.web.controller.admin.sda.sabodam;

import com.integrasolusi.pusda.sisda.persistence.region.SungaiSaboDam;
import com.integrasolusi.pusda.sisda.service.sda.SungaiSaboDamService;
import com.integrasolusi.pusda.sisda.web.form.SaboDamSearchForm;
import com.integrasolusi.pusda.sisda.web.form.sda.SungaiSabodamForm;
import com.integrasolusi.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User: pancara
 * Date: 10/8/12
 * Time: 12:13 PM
 */
@Controller("adminSungaiSabodamController")
@RequestMapping("/admin/sda/sabodam/sungai")
public class SungaiSabodamController {
    private static Logger logger = LoggerFactory.getLogger(SungaiSabodamController.class);

    @Autowired
    private StreamHelper streamHelper;

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private SungaiSaboDamService sungaiSaboDamService;

    @Autowired
    private PagingHelper pagingHelper;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") SaboDamSearchForm form) {
        return list(1L, form);
    }

    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @ModelAttribute("form") SaboDamSearchForm form) {
        ModelAndView mav = new ModelAndView("admin/sda/sabodam/sungai/list");
        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start_row", start);
        Long count = getCount(form.getKeyword());
        mav.addObject("count", count);
        mav.addObject("last", pagingHelper.calcPageCount(count));

        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<SungaiSaboDam> sungaiList = getSungaiList(form.getKeyword(), start, pagingHelper.getRowPerPage());
        mav.addObject("sungaiList", sungaiList);

        return mav;
    }

    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return sungaiSaboDamService.countAlls();
        else
            return sungaiSaboDamService.countByKeyword(createKeywordString(keyword));
    }

    private List<SungaiSaboDam> getSungaiList(String keyword, Long start, Long count) {
        if (StringUtils.isEmpty(keyword))
            return sungaiSaboDamService.findAlls(start, count);
        else
            return sungaiSaboDamService.findByKeyword(createKeywordString(keyword), start, count);

    }

    private String createKeywordString(String keyword) {
        return String.format("%%%s%%", keyword);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") SungaiSabodamForm form) {
        return createFormView();
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") SungaiSabodamForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            SungaiSaboDam sungai = new SungaiSaboDam();
            sungai.setName(form.getName());
            if (form.getMap() != null && !form.getMap().isEmpty()) {
                sungai.setFilename(form.getMap().getOriginalFilename());
                sungaiSaboDamService.save(sungai, form.getMap().getInputStream());
            } else {
                sungaiSaboDamService.save(sungai);
            }
            return new ModelAndView(String.format("redirect:/admin/sda/sabodam/sungai/list.html"));
        }
        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") SungaiSabodamForm form) {
        SungaiSaboDam sungai = sungaiSaboDamService.findById(id);
        form.setName(sungai.getName());
        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") SungaiSabodamForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            SungaiSaboDam sungai = sungaiSaboDamService.findById(id);
            sungai.setName(form.getName());
            if (form.getMap() != null && !form.getMap().isEmpty()) {
                sungai.setFilename(form.getMap().getOriginalFilename());
                sungaiSaboDamService.save(sungai, form.getMap().getInputStream());
            } else {
                sungaiSaboDamService.save(sungai);
            }
            return new ModelAndView(String.format("redirect:/admin/sda/sabodam/sungai/list.html"));
        }
        return createFormView();
    }

    private ModelAndView createFormView() {
        return new ModelAndView("/admin/sda/sabodam/sungai/form");
    }


    private void validateForm(SungaiSabodamForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi.");
        }

    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            sungaiSaboDamService.removeByIds(ids);
        }

        return createViewRedirectToPage(page, keyword);
    }

    private ModelAndView createViewRedirectToPage(Long page, String keyword) {
        String sPage = page == null ? "" : String.format("%d", page);
        return new ModelAndView(String.format("redirect:/admin/sda/sabodam/sungai/page/%s.html?keyword=%s", sPage, keyword));
    }

    @RequestMapping("{id}/**")
    public void downloadMap(@PathVariable("id") Long id,
                            @RequestParam(value = "w", required = false, defaultValue = "0") Integer w,
                            @RequestParam(value = "h", required = false, defaultValue = "0") Integer h,
                            HttpServletResponse response) throws IOException {
        SungaiSaboDam sungaiSaboDam = sungaiSaboDamService.findById(id);

        String contentType = contentTypeUtils.getContentType(sungaiSaboDam.getFilename());
        response.setContentType(contentType);


        sungaiSaboDamService.getMapBlob(id, w, h, response.getOutputStream());
    }

//    private Dimension calcImageSize(Integer originW, Integer originH, Integer scaledW, Integer scaledH) {
//        if (scaledW == null && scaledH == null)
//            return new Dimension(originW, originH);
//
//        if (scaledW != null) {
//            Double h = scaledW * (originH.doubleValue() / originW.doubleValue());
//            return new Dimension(scaledW, h.intValue());
//        }
//
//        if (scaledH != null) {
//            Double w = scaledH * (originW.doubleValue() / originH.doubleValue());
//            return new Dimension(scaledW, w.intValue());
//        }
//        return new Dimension(scaledW, scaledH);
//    }

}
