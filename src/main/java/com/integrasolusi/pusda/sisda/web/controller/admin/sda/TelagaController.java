package com.integrasolusi.pusda.sisda.web.controller.admin.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.Telaga;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.TelagaService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.web.form.TelagaForm;
import com.integrasolusi.pusda.sisda.web.form.TelagaSearchForm;
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
 * Date: 8/6/12
 * Time: 11:39 AM
 */

@Controller("adminTelagaController")
@RequestMapping("/admin/sda/telaga")
public class TelagaController {
    @Autowired
    private TelagaService telagaService;

    @Autowired
    private DasService dasService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private PagingHelper pagingHelper;

    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") TelagaSearchForm form) {
        return list(form, 1L);
    }

    @RequestMapping(value = "page/{page}.html")
    public ModelAndView list(@ModelAttribute("form") TelagaSearchForm form,
                             @PathVariable(value = "page") Long page) {

        ModelAndView mav = new ModelAndView("admin/sda/telaga/list");
        mav.addObject("dasList", dasService.findAlls());

        mav.addObject("current", page);

        Long count = getTelagaCount(form.getDas(), form.getKeyword());
        mav.addObject("count", count);

        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        Long start = pagingHelper.getStartRow(page);
        List<Telaga> telagaList = getTelagaList(form.getDas(), form.getKeyword(), start, pagingHelper.getRowPerPage());

        mav.addObject("start", start);
        mav.addObject("telagaList", telagaList);
        mav.addObject("last", pagingHelper.calcPageCount(count));

        return mav;
    }

    private Long getTelagaCount(Long das, String keyword) {
        return das == null ?
                StringUtils.isEmpty(keyword) ?
                        telagaService.countAlls() :
                        telagaService.countByKeyword(createKeywordString(keyword))
                :
                StringUtils.isEmpty(keyword) ?
                        telagaService.countByDas(das) :
                        telagaService.countByDasAndKeyword(das, createKeywordString(keyword));
    }

    private List<Telaga> getTelagaList(Long das, String keyword, Long start, Long count) {
        return das == null ?
                StringUtils.isEmpty(keyword) ?
                        telagaService.findAlls(start, count) :
                        telagaService.findByKeyword(createKeywordString(keyword), start, count)
                :
                StringUtils.isEmpty(keyword) ?
                        telagaService.findByDas(das, start, count) :
                        telagaService.findByDasAndKeyword(das, createKeywordString(keyword), start, count);

    }

    private String createKeywordString(String keyword) {
        return String.format("%%%s%%", keyword);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") TelagaForm form) {
        return createFormView();
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") TelagaForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            try {
                saveForm(null, form);
                return createListView();
            } catch (Exception e) {
                errors.reject(e.getMessage());
            }
        }

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") TelagaForm form) {
        Telaga telaga = telagaService.findById(id);
        form.setName(telaga.getName());
        if (telaga.getDas() != null) {
            form.setDas(telaga.getDas().getId());
        }
        form.setContent(telaga.getContent());

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") TelagaForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(id, form);
            return createListView();
        }
        return createFormView();
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "das", required = false) Long das,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            telagaService.removeByIds(ids);
        }
        return createViewRedirectToPage(page, das, keyword);
    }

    private void saveForm(Long id, TelagaForm form) throws IOException {
        Telaga telaga = (id == null ?
                new Telaga() :
                telagaService.findById(id));

        telaga.setName(form.getName());

        Das das = dasService.findById(form.getDas());
        telaga.setDas(das);

        telaga.setContent(form.getContent());

        telagaService.save(telaga);
    }

    private void validateForm(TelagaForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

        if (form.getDas() == null) {
            errors.reject("das.empty", "DAS belum diisi");
        }

    }

    private ModelAndView createFormView() {
        ModelAndView mav = new ModelAndView("/admin/sda/telaga/form");
        mav.addObject("dasLookup", populateDasLookup());
        return mav;
    }

    private Map populateDasLookup() {
        Map lookup = new LinkedHashMap();
        for (Das das : dasService.findAlls()) {
            lookup.put(das.getId(), das.getName());
        }
        return lookup;
    }

    private ModelAndView createListView() {
        return new ModelAndView("redirect:/admin/sda/telaga/list.html");
    }

    private ModelAndView createViewRedirectToPage(Long page, Long das, String keyword) {
        String sPage = page == null ? "" : String.format("%d", page);
        String sDas = das == null ? "" : String.format("%d", das);
        return new ModelAndView(String.format("redirect:/admin/sda/telaga/page/%s.html?das=%s&keyword=%s", sPage, sDas, keyword));
    }
}
