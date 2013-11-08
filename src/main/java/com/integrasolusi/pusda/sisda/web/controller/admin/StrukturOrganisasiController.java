package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.StrukturOrganisasi;
import com.integrasolusi.pusda.sisda.service.StrukturOrganisasiService;
import com.integrasolusi.pusda.sisda.web.form.StrukturOrganisasiForm;
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
 * Date       : 11/27/12
 * Time       : 3:59 PM
 */
@Controller("adminStrukturOrganisasiController")
@RequestMapping("/admin/struktur_organisasi")
public class StrukturOrganisasiController {
    @Autowired
    private StrukturOrganisasiService strukturOrganisasiService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping("list.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("admin/struktur_organisasi/list");
        mav.addObject("soList", strukturOrganisasiService.findAlls());
        return mav;
    }

    @RequestMapping("{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        StrukturOrganisasi so = strukturOrganisasiService.findById(id);
        if (so != null) {
            response.setContentType(contentTypeUtils.getContentType(so.getFilename()));
            strukturOrganisasiService.getBlob(id, response.getOutputStream());
            response.getOutputStream().flush();
        }
    }

    @RequestMapping("thumb/{id}/**")
    public void thumb(@PathVariable("id") Long id,
                      @RequestParam(value = "width", required = false, defaultValue = "100") Integer width,
                      @RequestParam(value = "height", required = false, defaultValue = "0") Integer height,
                      HttpServletResponse response) throws IOException {
        StrukturOrganisasi so = strukturOrganisasiService.findById(id);
        if (so != null) {
            response.setContentType(contentTypeUtils.getContentType(so.getFilename()));
            strukturOrganisasiService.getBlob(id, width, height, response.getOutputStream());
            response.getOutputStream().flush();
        }
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") StrukturOrganisasiForm form) {
        ModelAndView mav = createFormView();
        StrukturOrganisasi so = strukturOrganisasiService.findById(id);
        form.setName(so.getName());
        form.setDescription(so.getDescription());
        return mav;
    }

    private ModelAndView createFormView() {
        return new ModelAndView("admin/struktur_organisasi/form");
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") StrukturOrganisasiForm form, Errors errors) throws IOException {
        validateForm(form, errors);

        if (!errors.hasErrors()) {
            saveForm(id, form);
            return new ModelAndView("redirect:/admin/struktur_organisasi/list.html");
        }
        return createFormView();
    }

    private void saveForm(Long id, StrukturOrganisasiForm form) throws IOException {
        StrukturOrganisasi so = strukturOrganisasiService.findById(id);
        so.setName(form.getName());
        so.setDescription(form.getDescription());

        if (form.getFile() != null && !form.getFile().isEmpty()) {
            so.setFilename(form.getFile().getOriginalFilename());
            strukturOrganisasiService.save(so, form.getFile().getInputStream());
        } else {
            strukturOrganisasiService.save(so);
        }
    }


    private void validateForm(StrukturOrganisasiForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }
    }
}
