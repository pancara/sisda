package com.integrasolusi.pusda.sisda.web.controller.admin.tkpsda;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscDocument;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscFolder;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.service.tkpsda.misc.MiscDocumentService;
import com.integrasolusi.pusda.sisda.service.tkpsda.misc.MiscFolderService;
import com.integrasolusi.pusda.sisda.web.form.tkpsda.MiscDocumentForm;
import com.integrasolusi.pusda.sisda.web.form.tkpsda.MiscFolderForm;
import com.integrasolusi.utils.ContentTypeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Programmer : pancara
 * Date       : 10/31/12
 * Time       : 3:18 PM
 */
@Controller("adminMiscDocumentController")
@RequestMapping("/admin/tkpsda/lain_lain")

public class MiscDocumentController {

    @Autowired
    private MiscFolderService miscFolderService;

    @Autowired
    private MiscDocumentService miscDocumentService;

    @Autowired
    private YearService yearService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("view.html")
    public ModelAndView view() {
        ModelAndView mav = new ModelAndView("admin/tkpsda/lain_lain/list_ws");
        mav.addObject("wsList", wilayahSungaiService.findAlls());
        return mav;
    }

    @RequestMapping(value = "view/{ws}.html")
    public ModelAndView view(@PathVariable(value = "ws") Long wsId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/lain_lain/list_year");
        mav.addObject("wilayahSungai", wilayahSungaiService.findById(wsId));

        mav.addObject("years", yearService.findAlls());
        return mav;
    }

    @RequestMapping(value = "view/{ws}/{year}.html")
    public ModelAndView view(@PathVariable(value = "ws") Long wsId,
                             @PathVariable(value = "year") Long yearId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/lain_lain/list_folder");

        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("wilayahSungai", ws);

        Year year = yearService.findById(yearId);
        mav.addObject("year", year);

        mav.addObject("folders", miscFolderService.findByWilayahSungaiAndYear(ws, year));
        return mav;
    }

    @RequestMapping(value = "view/{ws}/{year}/{folder}.html")
    public ModelAndView view(@PathVariable(value = "ws") Long wsId,
                             @PathVariable(value = "year") Long yearId,
                             @PathVariable(value = "folder") Long folderId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/lain_lain/list_document");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("wilayahSungai", ws);

        Year year = yearService.findById(yearId);
        mav.addObject("year", year);

        MiscFolder folder = miscFolderService.findById(folderId);
        mav.addObject("folder", folder);

        mav.addObject("documents", miscDocumentService.findByFolder(folder));
        return mav;
    }


    @RequestMapping(value = "add/{ws}/{year}.html", method = RequestMethod.GET)
    public ModelAndView addActivity(@PathVariable(value = "ws") Long wsId,
                                    @PathVariable(value = "year") Long yearId,
                                    @ModelAttribute("form") MiscFolderForm form) {
        return createFolderFormView(wsId, yearId, null);
    }

    private ModelAndView createFolderFormView(Long wsId, Long yearId, Long folderId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/lain_lain/folder_form");

        mav.addObject("wilayahSungai", wilayahSungaiService.findById(wsId));
        mav.addObject("year", yearService.findById(yearId));

        if (folderId != null) {
            mav.addObject("folder", miscFolderService.findById(folderId));
        }
        return mav;

    }

    @RequestMapping(value = "add/{ws}/{year}.html", method = RequestMethod.POST)
    public ModelAndView addActivity(@PathVariable(value = "ws") Long wsId,
                                    @PathVariable(value = "year") Long yearId,
                                    @ModelAttribute("form") MiscFolderForm form,
                                    Errors errors) throws IOException {
        validateFolderForm(form, errors);
        if (!errors.hasErrors()) {
            MiscFolder folder = new MiscFolder();

            folder.setName(form.getName());
            folder.setIndex(form.getIndex());

            folder.setYear(yearService.findById(yearId));
            folder.setWs(wilayahSungaiService.findById(wsId));

            miscFolderService.save(folder);

            return createRedirectFolderView(wsId, yearId);
        }

        return createFolderFormView(wsId, yearId, null);
    }

    private ModelAndView createRedirectFolderView(Long wsId, Long yearId) {
        return new ModelAndView(String.format("redirect:/admin/tkpsda/lain_lain/view/%d/%d.html", wsId, yearId));
    }

    private void validateFolderForm(MiscFolderForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama folder belum diisi");
        }
    }

    @RequestMapping(value = "edit/{ws}/{year}/{folder}.html", method = RequestMethod.GET)
    public ModelAndView editActivity(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable("folder") Long folderId,
                                     @ModelAttribute("form") MiscFolderForm form) {
        MiscFolder folder = miscFolderService.findById(folderId);
        form.setName(folder.getName());
        form.setIndex(folder.getIndex());

        return createFolderFormView(wsId, yearId, folderId);
    }

    @RequestMapping(value = "edit/{ws}/{year}/{folder}.html", method = RequestMethod.POST)
    public ModelAndView editActivity(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable("folder") Long folderId,
                                     @ModelAttribute("form") MiscFolderForm form,
                                     Errors errors) throws IOException {
        validateFolderForm(form, errors);
        if (!errors.hasErrors()) {
            MiscFolder folder = miscFolderService.findById(folderId);
            folder.setName(form.getName());
            folder.setIndex(form.getIndex());

            miscFolderService.save(folder);

            return createRedirectFolderView(wsId, yearId);
        }

        return createFolderFormView(wsId, yearId, null);
    }

    @RequestMapping(value = "remove/{ws}/{year}.html", method = RequestMethod.POST)
    public ModelAndView removFolder(@PathVariable(value = "ws") Long wsId,
                                    @PathVariable(value = "year") Long yearId,
                                    @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            miscFolderService.removeByIds(ids);
        }
        return createRedirectFolderView(wsId, yearId);
    }

    @RequestMapping(value = "add/{ws}/{year}/{folder}.html", method = RequestMethod.GET)
    public ModelAndView addDocument(@PathVariable(value = "ws") Long wsId,
                                    @PathVariable(value = "year") Long yearId,
                                    @PathVariable(value = "folder") Long folderId,
                                    @ModelAttribute("form") MiscDocumentForm form) {
        return createDocumentFormView(wsId, yearId, folderId, null);
    }

    private ModelAndView createDocumentFormView(Long wsId, Long yearId, Long folderId, Long documentId) {
        ModelAndView mav = new ModelAndView("admin/tkpsda/lain_lain/document_form");

        mav.addObject("wilayahSungai", wilayahSungaiService.findById(wsId));
        mav.addObject("year", yearService.findById(yearId));
        mav.addObject("folder", miscFolderService.findById(folderId));

        if (documentId != null) {
            mav.addObject("document", miscDocumentService.findById(documentId));
        }

        return mav;
    }

    @RequestMapping(value = "add/{ws}/{year}/{folder}.html", method = RequestMethod.POST)
    public ModelAndView addDocument(@PathVariable(value = "ws") Long wsId,
                                    @PathVariable(value = "year") Long yearId,
                                    @PathVariable(value = "folder") Long folderId,
                                    @ModelAttribute("form") MiscDocumentForm form,
                                    Errors errors) throws IOException {
        validateDocumentForm(form, errors, false);
        if (!errors.hasErrors()) {
            MiscDocument document = new MiscDocument();
            document.setName(form.getName());
            document.setIndex(form.getIndex());

            document.setActive(true);

            document.setFilename(form.getFile().getOriginalFilename());

            MiscFolder folder = miscFolderService.findById(folderId);
            document.setFolder(folder);

            miscDocumentService.save(document, form.getFile().getInputStream());

            return createRedirectDocumentView(wsId, yearId, folderId);
        }

        return createDocumentFormView(wsId, yearId, folderId, null);
    }

    private ModelAndView createRedirectDocumentView(Long wsId, Long yearId, Long folderId) {
        return new ModelAndView(String.format("redirect:/admin/tkpsda/lain_lain/view/%d/%d/%d.html", wsId, yearId, folderId));
    }

    private void validateDocumentForm(MiscDocumentForm form, Errors errors, boolean allowFileEmpty) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama dokumen belum diisi");
        }

        if (allowFileEmpty == false) {
            if (form.getFile() == null || form.getFile().isEmpty()) {
                errors.reject("file.empty", "File belum diisi");
            }
        }
    }

    @RequestMapping(value = "edit/{ws}/{year}/{folder}/{document}.html", method = RequestMethod.GET)
    public ModelAndView editDocument(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable(value = "folder") Long folderId,
                                     @PathVariable(value = "document") Long documentId,
                                     @ModelAttribute("form") MiscDocumentForm form) {
        MiscDocument document = miscDocumentService.findById(documentId);
        form.setName(document.getName());
        form.setIndex(document.getIndex());

        return createDocumentFormView(wsId, yearId, folderId, documentId);
    }

    @RequestMapping(value = "edit/{ws}/{year}/{folder}/{document}.html", method = RequestMethod.POST)
    public ModelAndView editDocument(@PathVariable(value = "ws") Long wsId,
                                     @PathVariable(value = "year") Long yearId,
                                     @PathVariable(value = "folder") Long folderId,
                                     @PathVariable(value = "document") Long documentId,
                                     @ModelAttribute("form") MiscDocumentForm form,
                                     Errors errors) throws IOException {
        validateDocumentForm(form, errors, true);
        if (!errors.hasErrors()) {
            MiscDocument document = miscDocumentService.findById(documentId);
            document.setName(form.getName());
            document.setIndex(form.getIndex());

            if (form.getFile() != null && !form.getFile().isEmpty()) {
                document.setFilename(form.getFile().getOriginalFilename());
                miscDocumentService.save(document, form.getFile().getInputStream());
            } else {
                miscDocumentService.save(document);
            }


            return createRedirectDocumentView(wsId, yearId, folderId);
        }

        return createDocumentFormView(wsId, yearId, folderId, null);
    }

    @RequestMapping(value = "remove/{ws}/{year}/{folder}.html", method = RequestMethod.POST)
    public ModelAndView removeDocument(@PathVariable(value = "ws") Long wsId,
                                       @PathVariable(value = "year") Long yearId,
                                       @PathVariable(value = "folder") Long folderId,
                                       @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            miscDocumentService.removeByIds(ids);
        }
        return createRedirectDocumentView(wsId, yearId, folderId);
    }

    @RequestMapping("get/{ws}/{year}/{folder}/{document}/**")
    public void downloadPicture(@PathVariable(value = "ws") Long wsId,
                                @PathVariable(value = "year") Long yearId,
                                @PathVariable(value = "folder") Long folderId,
                                @PathVariable(value = "document") Long documentId,
                                HttpServletResponse response) throws Exception {
        MiscDocument document = miscDocumentService.findById(documentId);
        if (document == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setContentType(contentTypeUtils.getContentType(document.getFilename()));
        miscDocumentService.getBlob(documentId, response.getOutputStream());

    }
}
