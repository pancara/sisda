package com.integrasolusi.pusda.sisda.web.controller.admin.sda.sabodam;

import com.integrasolusi.pusda.sisda.persistence.region.SungaiSaboDam;
import com.integrasolusi.pusda.sisda.persistence.sda.SaboDam;
import com.integrasolusi.pusda.sisda.service.sda.SaboDamService;
import com.integrasolusi.pusda.sisda.service.sda.SungaiSaboDamService;
import com.integrasolusi.pusda.sisda.web.form.SaboDamSearchForm;
import com.integrasolusi.pusda.sisda.web.form.sda.SabodamForm;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pancara
 * Date: 10/8/12
 * Time: 12:13 PM
 */
@Controller("adminSabodamController")
@RequestMapping("/admin/sda/sabodam")
public class SabodamController {
    @Autowired
    private SaboDamService saboDamService;

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
        ModelAndView mav = new ModelAndView("admin/sda/sabodam/list");
        List<SungaiSaboDam> sungaiList = createSungaiList();
        mav.addObject("sungaiList", sungaiList);

        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start_row", start);
        Long count = getCount(form.getSungai(), form.getKeyword());
        mav.addObject("count", count);
        mav.addObject("last_page", pagingHelper.calcPageCount(count));

        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<SaboDam> sabodamList = getSabodamList(form.getSungai(), form.getKeyword(), start, pagingHelper.getRowPerPage());
        mav.addObject("sabodamList", sabodamList);

        return mav;
    }

    private List<SungaiSaboDam> createSungaiList() {
        List<SungaiSaboDam> sungaiList = sungaiSaboDamService.findAlls();
        SungaiSaboDam all = new SungaiSaboDam();
        all.setName("- SEMUA -");
        sungaiList.add(0, all);
        return sungaiList;
    }

    private Long getCount(Long sungai, String keyword) {
        if (sungai == null) {
            if (StringUtils.isEmpty(keyword))
                return saboDamService.countAlls();
            else
                return saboDamService.countByKeyword(createKeywordString(keyword));
        } else {
            if (StringUtils.isEmpty(keyword))
                return saboDamService.countBySungai(sungai);
            else {
                return saboDamService.countBySungaiAndKeyword(sungai, createKeywordString(keyword));
            }
        }
    }

    private List<SaboDam> getSabodamList(Long sungai, String keyword, Long start, Long count) {
        if (sungai == null) {
            if (StringUtils.isEmpty(keyword))
                return saboDamService.findAlls(start, count);
            else
                return saboDamService.findByKeyword(createKeywordString(keyword), start, count);
        } else {
            if (StringUtils.isEmpty(keyword))
                return saboDamService.findBySungai(sungai, start, count);
            else
                return saboDamService.findBySungaiAndKeyword(sungai, createKeywordString(keyword), start, count);
        }
    }

    private String createKeywordString(String keyword) {
        return String.format("%%%s%%", keyword);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") SabodamForm form) {
        return createFormView();
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") SabodamForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            SaboDam sabodam = new SaboDam();
            sabodam.setCode(form.getCode());
            sabodam.setDescription(form.getDescription());
            sabodam.setX(form.getX());
            sabodam.setY(form.getY());

            if (form.getSungai() != null)
                sabodam.setSungai(sungaiSaboDamService.findById(form.getSungai()));

            if (form.getFile() != null && !form.getFile().isEmpty()) {
                sabodam.setFilename(form.getFile().getOriginalFilename());
                saboDamService.save(sabodam, form.getFile().getInputStream());
            } else {
                saboDamService.save(sabodam);
            }
            return new ModelAndView(String.format("redirect:/admin/sda/sabodam/list.html?keyword=%s", form.getCode()));
        }
        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") SabodamForm form) {
        SaboDam sabodam = saboDamService.findById(id);
        form.setCode(sabodam.getCode());
        form.setDescription(sabodam.getDescription());
        if (sabodam.getSungai() != null)
            form.setSungai(sabodam.getSungai().getId());

        form.setX(sabodam.getX());
        form.setY(sabodam.getY());

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") SabodamForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            SaboDam sabodam = saboDamService.findById(id);
            sabodam.setCode(form.getCode());
            sabodam.setDescription(form.getDescription());
            if (form.getSungai() != null)
                sabodam.setSungai(sungaiSaboDamService.findById(form.getSungai()));

            sabodam.setX(form.getX());
            sabodam.setY(form.getY());
            if (form.getFile() != null && !form.getFile().isEmpty()) {
                sabodam.setFilename(form.getFile().getOriginalFilename());
                saboDamService.save(sabodam, form.getFile().getInputStream());
            } else {
                saboDamService.save(sabodam);
            }
            return new ModelAndView(String.format("redirect:/admin/sda/sabodam/list.html?keyword=%s", form.getCode()));
        }
        return createFormView();
    }

    private ModelAndView createFormView() {
        ModelAndView mav = new ModelAndView("/admin/sda/sabodam/form");
        mav.addObject("sungaiLookup", getSungaiLookup());
        return mav;
    }

    private Map getSungaiLookup() {
        Map lookup = new LinkedHashMap();
        for (SungaiSaboDam sungai : sungaiSaboDamService.findAlls()) {
            lookup.put(sungai.getId(), sungai.getName());
        }
        return lookup;
    }

    private void validateForm(SabodamForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getCode())) {
            errors.reject("code.empty", "Kode belum diisi.");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "sungai", required = false) Long sungai,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            saboDamService.removeByIds(ids);
        }

        return createViewRedirectToPage(page, sungai, keyword);
    }

    private ModelAndView createViewRedirectToPage(Long page, Long sungai, String keyword) {
        String sPage = page == null ? "" : String.format("%d", page);
        String sSungai = sungai == null ? "" : String.format("%d", sungai);
        return new ModelAndView(String.format("redirect:/admin/sda/sabodam/page/%s.html?sungai=%s&keyword=%s", sPage, sSungai, keyword));
    }

  
}
