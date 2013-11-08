package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.Dokumentasi;
import com.integrasolusi.pusda.sisda.service.DokumentasiPhotoService;
import com.integrasolusi.pusda.sisda.service.DokumentasiService;
import com.integrasolusi.pusda.sisda.web.form.DokumentasiForm;
import com.integrasolusi.pusda.sisda.web.form.DokumentasiSearchForm;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 10/31/12
 * Time       : 3:18 PM
 */
@Controller("adminDokumentasiController")
@RequestMapping("/admin/dokumentasi")
public class DokumentasiController {
    @Autowired
    private DokumentasiService dokumentasiService;

    @Autowired
    private DokumentasiPhotoService dokumentasiPhotoService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @Autowired
    private PagingHelper pagingHelper;

    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") DokumentasiSearchForm form) {
        return list(form, 1L);
    }

    @RequestMapping(value = "page/{page}.html")
    public ModelAndView list(@ModelAttribute("form") DokumentasiSearchForm form,
                             @PathVariable(value = "page") Long page) {

        ModelAndView mav = new ModelAndView("admin/dokumentasi/list");

        mav.addObject("current", page);
        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);

        Long count = getDataCount(form);
        mav.addObject("count", count);

        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);


        mav.addObject("last", pagingHelper.calcPageCount(count));


        List<Dokumentasi> dokumentasiList = findData(form, start, pagingHelper.getRowPerPage());
        mav.addObject("dokumentasiList", dokumentasiList);

        return mav;
    }

    private Long getDataCount(DokumentasiSearchForm form) {
        return StringUtils.isEmpty(form.getKeyword()) ?
                dokumentasiService.countAlls() :
                dokumentasiService.countByKeyword(createKeywordString(form.getKeyword()));

    }

    private List<Dokumentasi> findData(DokumentasiSearchForm form, Long start, Long count) {
        return StringUtils.isEmpty(form.getKeyword()) ?
                dokumentasiService.findAlls(start, count) :
                dokumentasiService.findByKeyword(createKeywordString(form.getKeyword()), start, count);

    }

    private String createKeywordString(String keyword) {
        return "%" + keyword + "%";
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") DokumentasiForm form) {
        return createFormView();
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") DokumentasiForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(null, form);
            return createRedirectToListView();
        }

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") DokumentasiForm form) {
        Dokumentasi doc = dokumentasiService.findById(id);
        form.setTitle(doc.getTitle());
        
        Calendar cal = new GregorianCalendar();
        cal.setTime(doc.getPublishedDate());
        form.getPublishedDate().setCalendar(cal);

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") DokumentasiForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(id, form);
            return createRedirectToListView();
        }
        return createFormView();
    }

    private ModelAndView createFormView() {
        return new ModelAndView("/admin/dokumentasi/form");
    }

    private ModelAndView createRedirectToListView() {
        return new ModelAndView("redirect:/admin/dokumentasi/list.html");
    }

    private void saveForm(Long id, DokumentasiForm form) throws IOException {
        Dokumentasi doc = id == null ?
                new Dokumentasi() :
                dokumentasiService.findById(id);
        doc.setTitle(form.getTitle());
        doc.setPublishedDate(form.getPublishedDate().getCalendar().getTime());
        dokumentasiService.save(doc);
    }

    private void validateForm(DokumentasiForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getTitle())) {
            errors.reject("title.empty", "Judul dokumentasi belum diisi");
        }

    }

    @RequestMapping(value = "manage.html", params = "remove", method = RequestMethod.POST)
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            dokumentasiService.removeByIds(ids);
        }
        return createRedirectToListAtPageView(page, keyword);
    }

    @RequestMapping(value = "manage.html", params = "publish", method = RequestMethod.POST)
    public ModelAndView publish(@RequestParam(value = "page", required = false) Long page,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            dokumentasiService.publishByIds(ids);
        }
        return createRedirectToListAtPageView(page, keyword);
    }

    @RequestMapping(value = "manage.html", params = "unpublish", method = RequestMethod.POST)
    public ModelAndView unpublish(@RequestParam(value = "page", required = false) Long page,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            dokumentasiService.unpublishByIds(ids);
        }
        return createRedirectToListAtPageView(page, keyword);
    }
    

    private ModelAndView createRedirectToListAtPageView(Long page, String keyword) {
        if (page == null) {
            return new ModelAndView(String.format("redirect:/admin/dokumentasi/list.html?&keyword=%s", keyword));
        } else {
            String sPage = String.valueOf(page);
            return new ModelAndView(String.format("redirect:/admin/dokumentasi/page/%s.html?keyword=%s", sPage, keyword));
        }
    }

}
