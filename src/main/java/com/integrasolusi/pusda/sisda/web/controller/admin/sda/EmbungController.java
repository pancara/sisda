package com.integrasolusi.pusda.sisda.web.controller.admin.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.Embung;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.EmbungService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.web.form.EmbungForm;
import com.integrasolusi.pusda.sisda.web.form.EmbungSearchForm;
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

@Controller("adminEmbungController")
@RequestMapping("/admin/sda/embung")
public class EmbungController {
    @Autowired
    private EmbungService embungService;

    @Autowired
    private DasService dasService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private PagingHelper pagingHelper;

    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") EmbungSearchForm form) {
        return list(form, 1L);
    }

    @RequestMapping(value = "page/{page}.html")
    public ModelAndView list(@ModelAttribute("form") EmbungSearchForm form,
                             @PathVariable(value = "page") Long page) {

        ModelAndView mav = new ModelAndView("admin/sda/embung/list");
        mav.addObject("wilayahSungaiList", wilayahSungaiService.findAlls());
        mav.addObject("dasList", dasService.findAlls());

        mav.addObject("current", page);

        Long count = getEmbungCount(form.getDas(), form.getKeyword());
        mav.addObject("count", count);

        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        Long start = pagingHelper.getStartRow(page);
        List<Embung> embungList = getEmbungList(form.getDas(), form.getKeyword(), start, pagingHelper.getRowPerPage());

        mav.addObject("start", start);
        mav.addObject("embungList", embungList);
        mav.addObject("last", pagingHelper.calcPageCount(count));

        return mav;
    }

    private Long getEmbungCount(Long das, String keyword) {
        return das == null ?
                StringUtils.isEmpty(keyword) ?
                        embungService.countAlls() :
                        embungService.countByKeyword(createKeywordString(keyword))
                :
                StringUtils.isEmpty(keyword) ?
                        embungService.countByDas(das) :
                        embungService.countByDasAndKeyword(das, createKeywordString(keyword));
    }

    private List<Embung> getEmbungList(Long das, String keyword, Long start, Long count) {
        return das == null ?
                StringUtils.isEmpty(keyword) ?
                        embungService.findAlls(start, count) :
                        embungService.findByKeyword(createKeywordString(keyword), start, count)
                :
                StringUtils.isEmpty(keyword) ?
                        embungService.findByDas(das, start, count) :
                        embungService.findByDasAndKeyword(das, createKeywordString(keyword), start, count);

    }

    private String createKeywordString(String keyword) {
        return String.format("%%%s%%", keyword);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") EmbungForm form) {
        return createFormView();
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") EmbungForm form, Errors errors) throws IOException {
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
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") EmbungForm form) {
        Embung embung = embungService.findById(id);
        form.setName(embung.getName());
        if (embung.getDas() != null) {
            form.setDas(embung.getDas().getId());
        }
        form.setContent(embung.getContent());

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") EmbungForm form, Errors errors) throws IOException {
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
            embungService.removeByIds(ids);
        }
        return createViewRedirectToPage(page, das, keyword);
    }

    private void saveForm(Long id, EmbungForm form) throws IOException {
        Embung embung = (id == null ?
                new Embung() :
                embungService.findById(id));

        embung.setName(form.getName());

        Das das = dasService.findById(form.getDas());
        embung.setDas(das);

        embung.setContent(form.getContent());

        embungService.save(embung);
    }

    private void validateForm(EmbungForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

        if (form.getDas() == null) {
            errors.reject("das.empty", "DAS belum diisi");
        }

    }

    private ModelAndView createFormView() {
        ModelAndView mav = new ModelAndView("/admin/sda/embung/form");
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
        return new ModelAndView("redirect:/admin/sda/embung/list.html");
    }

    private ModelAndView createViewRedirectToPage(Long page, Long das, String keyword) {
        String sPage = page == null ? "" : String.format("%d", page);
        String sDas = das == null ? "" : String.format("%d", das);
        return new ModelAndView(String.format("redirect:/admin/sda/embung/page/%s.html?das=%s&keyword=%s", sPage, sDas, keyword));
    }
}
