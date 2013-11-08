package com.integrasolusi.pusda.sisda.web.controller.admin.sda;

import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.AirTanah;
import com.integrasolusi.pusda.sisda.service.sda.AirTanahService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.web.form.AirTanahSearchForm;
import com.integrasolusi.pusda.sisda.web.form.sda.AirTanahForm;
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
@Controller("adminAirTanahController")
@RequestMapping("/admin/sda/air_tanah")
public class AirTanahController {
    @Autowired
    private AirTanahService airTanahService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private PagingHelper pagingHelper;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") AirTanahSearchForm form) {
        return list(1L, form);
    }

    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @ModelAttribute("form") AirTanahSearchForm form) {
        ModelAndView mav = new ModelAndView("admin/sda/air_tanah/list");
        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);
        Long count = getCount(form.getKeyword());
        mav.addObject("count", count);
        mav.addObject("last", pagingHelper.calcPageCount(count));

        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<AirTanah> airTanahList = getAirTanahList(form.getKeyword(), start, pagingHelper.getRowPerPage());
        mav.addObject("airTanahList", airTanahList);

        return mav;
    }

    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return airTanahService.countAlls();
        else
            return airTanahService.countByKeyword(createKeywordString(keyword));
    }

    private List<AirTanah> getAirTanahList(String keyword, Long start, Long count) {

        if (StringUtils.isEmpty(keyword))
            return airTanahService.findAlls(start, count);
        else
            return airTanahService.findByKeyword(createKeywordString(keyword), start, count);

    }

    private String createKeywordString(String keyword) {
        return String.format("%%%s%%", keyword);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") AirTanahForm form) {
        return createFormView();
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") AirTanahForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            AirTanah airTanah = new AirTanah();
            airTanah.setDescription(form.getDescription());
            airTanah.setWilayahSungai(wilayahSungaiService.findById(form.getWilayahSungai()));

            if (form.getFile() != null && !form.getFile().isEmpty()) {
                airTanah.setFilename(form.getFile().getOriginalFilename());
                airTanahService.save(airTanah, form.getFile().getInputStream());
            } else {
                airTanahService.save(airTanah);
            }
            return new ModelAndView(String.format("redirect:/admin/sda/air_tanah/list.html"));
        }
        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") AirTanahForm form) {
        AirTanah airTanah = airTanahService.findById(id);
        form.setDescription(airTanah.getDescription());
        if (airTanah.getWilayahSungai() != null) {
            form.setWilayahSungai(airTanah.getWilayahSungai().getId());
        }

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") AirTanahForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            AirTanah airTanah = airTanahService.findById(id);
            airTanah.setDescription(form.getDescription());
            airTanah.setWilayahSungai(wilayahSungaiService.findById(form.getWilayahSungai()));
            if (form.getFile() != null && !form.getFile().isEmpty()) {
                airTanah.setFilename(form.getFile().getOriginalFilename());
                airTanahService.save(airTanah, form.getFile().getInputStream());
            } else {
                airTanahService.save(airTanah);
            }
            return new ModelAndView(String.format("redirect:/admin/sda/air_tanah/list.html"));
        }
        return createFormView();
    }

    private ModelAndView createFormView() {
        ModelAndView mav = new ModelAndView("/admin/sda/air_tanah/form");
        mav.addObject("wilayahSungaiLookup", getWilayahSungaiLookup());
        return mav;
    }

    private Map getWilayahSungaiLookup() {
        Map lookup = new LinkedHashMap();
        for (WilayahSungai ws : wilayahSungaiService.findAlls()) {
            lookup.put(ws.getId(), ws.getName());
        }

        return lookup;
    }

    private void validateForm(AirTanahForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }

        if (form.getWilayahSungai() == null) {
            errors.reject("wilayahSungai.empty", "Wilayah sungai belum diisi");
        }
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            airTanahService.removeByIds(ids);
        }

        return createViewRedirectToPage(page, keyword);
    }

    private ModelAndView createViewRedirectToPage(Long page, String keyword) {
        String sPage = page == null ? "" : String.format("%d", page);
        return new ModelAndView(String.format("redirect:/admin/sda/air_tanah/page/%s.html?keyword=%s", sPage, keyword));
    }
}
