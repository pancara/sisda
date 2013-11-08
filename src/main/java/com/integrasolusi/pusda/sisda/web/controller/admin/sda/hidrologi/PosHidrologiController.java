package com.integrasolusi.pusda.sisda.web.controller.admin.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosHidrologi;
import com.integrasolusi.pusda.sisda.service.sda.hidrologi.PosHidrologiService;
import com.integrasolusi.pusda.sisda.web.form.sda.hidrologi.PosHidrologiForm;
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
 * Date       : 10/28/12
 * Time       : 11:58 AM
 */
@Controller("adminPosHidrologiController")
@RequestMapping("/admin/sda/hidrologi/pos_hidrologi")
public class PosHidrologiController {
    @Autowired
    private PosHidrologiService posHidrologiService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping(value = "list.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("admin/sda/hidrologi/pos_hidrologi/list");
        mav.addObject("listPosHidrologi", posHidrologiService.findAlls());
        return mav;
    }


    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") PosHidrologiForm form) {
        ModelAndView mav = createFormView();
        return mav;
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") PosHidrologiForm form,
                            Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(null, form);
            return createSuccessView();
        }

        return createFormView();
    }

    private ModelAndView createSuccessView() {
        String path = "redirect:/admin/sda/hidrologi/pos_hidrologi/list.html";
        return new ModelAndView(path);
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id,
                             @ModelAttribute("form") PosHidrologiForm form) {
        PosHidrologi pos = posHidrologiService.findById(id);
        form.setDescription(pos.getDescription());
        form.setActive(pos.getActive());

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id,
                             @ModelAttribute("form") PosHidrologiForm form,
                             Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(id, form);
            return createSuccessView();
        }
        return createFormView();
    }

    @RequestMapping(value = "remove.html", method = RequestMethod.POST)
    public ModelAndView remove(@RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            posHidrologiService.removeByIds(ids);
        }
        return createSuccessView();
    }

    private void saveForm(Long id, PosHidrologiForm form) throws IOException {
        PosHidrologi ph = (id == null ?
                new PosHidrologi() :
                posHidrologiService.findById(id));

        ph.setDescription(form.getDescription());
        ph.setActive(form.getActive());

        if (form.getFile() == null || form.getFile().isEmpty()) {
            posHidrologiService.save(ph);
        } else {
            ph.setFilename(form.getFile().getOriginalFilename());
            posHidrologiService.save(ph, form.getFile().getInputStream());
        }
    }

    private void validateForm(PosHidrologiForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }

    }

    private ModelAndView createFormView() {
        return new ModelAndView("/admin/sda/hidrologi/pos_hidrologi/form");
    }

    @RequestMapping("{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        PosHidrologi ph = posHidrologiService.findById(id);
        if (ph == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(ph.getFilename()));
        posHidrologiService.getBlob(id, response.getOutputStream());
    }


}
