package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.Dokumentasi;
import com.integrasolusi.pusda.sisda.persistence.DokumentasiPhoto;
import com.integrasolusi.pusda.sisda.service.DokumentasiPhotoService;
import com.integrasolusi.pusda.sisda.service.DokumentasiService;
import com.integrasolusi.pusda.sisda.web.form.DokumentasiPhotoForm;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Programmer : pancara
 * Date       : 10/31/12
 * Time       : 3:18 PM
 */
@Controller("adminDokumentasiPhotoController")
@RequestMapping("/admin/dokumentasi/{docId}/photo")
public class DokumentasiPhotoController {
    @Autowired
    private DokumentasiService dokumentasiService;

    @Autowired
    private DokumentasiPhotoService dokumentasiPhotoService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @Autowired
    private PagingHelper pagingHelper;

    private ModelAndView redirectToMain(Long page, Long wilayahSungai, String keyword) {
        if (page == null) {
            return new ModelAndView(String.format("redirect:/admin/dokumentasi/list.html?keyword=%s", keyword));
        } else {
            String sPage = String.valueOf(page);
            return new ModelAndView(String.format("redirect:/admin/dokumentasi/page/%s.html?keyword=%s", sPage, keyword));
        }
    }

    @RequestMapping(value = "list.html")
    public ModelAndView list(@PathVariable("docId") Long docId, @ModelAttribute("form") DokumentasiPhotoForm form) throws IOException {
        if (docId == null) {
            return redirectToMain(1L, null, null);
        }
        ModelAndView mav = new ModelAndView("admin/dokumentasi/photo/list");
        Dokumentasi documentasi = dokumentasiService.findById(docId);
        mav.addObject("dokumentasi", documentasi);

        mav.addObject("photoList", dokumentasiPhotoService.findByDokumentasi(documentasi));

        return mav;
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable("docId") Long docId,
                            @ModelAttribute("form") DokumentasiPhotoForm form) {
        return createFormView(docId);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@PathVariable("docId") Long docId,
                            @ModelAttribute("form") DokumentasiPhotoForm form,
                            Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(docId, null, form);
            return createRedirectToListView(docId);
        }

        return createFormView(docId);
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("docId") Long docId,
                             @PathVariable("id") Long id,
                             @ModelAttribute("form") DokumentasiPhotoForm form) {
        DokumentasiPhoto photo = dokumentasiPhotoService.findById(id);
        form.setTitle(photo.getTitle());

        return createFormView(docId);
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("docId") Long docId,
                             @PathVariable("id") Long id,
                             @ModelAttribute("form") DokumentasiPhotoForm form,
                             Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(docId, id, form);
            return createRedirectToListView(docId);
        }
        return createFormView(docId);
    }

    private ModelAndView createFormView(Long docId) {
        ModelAndView mav = new ModelAndView("/admin/dokumentasi/photo/form");
        mav.addObject("dokumentasi", dokumentasiService.findById(docId));
        return mav;
    }

    private ModelAndView createRedirectToListView(Long docId) {
        return new ModelAndView(String.format("redirect:/admin/dokumentasi/%d/photo/list.html", docId));
    }

    private void saveForm(Long docId, Long id, DokumentasiPhotoForm form) throws IOException {
        DokumentasiPhoto photo = id == null ?
                new DokumentasiPhoto() :
                dokumentasiPhotoService.findById(id);

        Dokumentasi dokumentasi = dokumentasiService.findById(docId);
        photo.setDokumentasi(dokumentasi);

        photo.setTitle(form.getTitle());

        if (form.getFile() == null || form.getFile().isEmpty()) {
            dokumentasiPhotoService.save(photo);
        } else {
            photo.setFilename(form.getFile().getOriginalFilename());
            dokumentasiPhotoService.save(photo, form.getFile().getInputStream());
        }
    }

    private void validateForm(DokumentasiPhotoForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getTitle())) {
            errors.reject("title.empty", "Judul dokumentasi belum diisi");
        }
    }


    @RequestMapping(value = "remove.html", method = RequestMethod.POST)
    public ModelAndView remove(@PathVariable("docId") Long docId,
                               @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            dokumentasiPhotoService.removeByIds(ids);
        }
        return createRedirectToListAtPageView(docId);
    }


    private ModelAndView createRedirectToListAtPageView(Long docId) {
        return new ModelAndView(String.format("redirect:/admin/dokumentasi/%d/photo/list.html", docId));
    }
}
