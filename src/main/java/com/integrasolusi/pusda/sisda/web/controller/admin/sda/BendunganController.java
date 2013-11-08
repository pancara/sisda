package com.integrasolusi.pusda.sisda.web.controller.admin.sda;

import com.integrasolusi.pusda.sisda.persistence.sda.Bendungan;
import com.integrasolusi.pusda.sisda.service.sda.BendunganService;
import com.integrasolusi.pusda.sisda.web.form.sda.BendunganForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import java.io.IOException;
import java.util.List;

/**
 * User: pancara
 * Date: 8/6/12
 * Time: 12:18 AM
 */
@Controller("adminBendunganController")
public class BendunganController {
    private BendunganService bendunganService;

    @Autowired
    public void setBendunganService(BendunganService bendunganService) {
        this.bendunganService = bendunganService;
    }

    @RequestMapping("/admin/sda/bendungan/view.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("admin/sda/bendungan/list");
        List<Bendungan> bendunganList = bendunganService.findAlls();
        mav.addObject("bendunganList", bendunganList);
        return mav;
    }

    @RequestMapping("/admin/sda/bendungan/remove/{id}.html")
    public MappingJacksonJsonView remove(@PathVariable("id") Long id) {
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        try {
            bendunganService.removeById(id);
            view.addStaticAttribute("result", true);
        } catch (Exception e) {
            view.addStaticAttribute("result", false);
        }
        return view;
    }

    @RequestMapping(value = "/admin/sda/bendungan/new.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") BendunganForm form) {
        return new ModelAndView("/admin/sda/bendungan/form");
    }

    @RequestMapping(value = "/admin/sda/bendungan/new.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") BendunganForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Bendungan bendungan = new Bendungan();
            bendungan.setName(form.getName());
            bendungan.setDescription(form.getDescription());
            bendungan.setContent(form.getFullContent());
            if (form.getPicture() == null || form.getPicture().isEmpty()) {
                bendunganService.save(bendungan);
            } else {
                bendungan.setPictureFilename(form.getPicture().getOriginalFilename());
                bendunganService.save(bendungan, form.getPicture().getInputStream());
            }
            return new ModelAndView("redirect:/admin/sda/bendungan/view.html");
        }
        return new ModelAndView("/admin/sda/bendungan/form");
    }

    @RequestMapping(value = "/admin/sda/bendungan/edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") BendunganForm form) {
        Bendungan bendungan = bendunganService.findById(id);
        form.setName(bendungan.getName());
        form.setDescription(bendungan.getDescription());
        form.setFullContent(bendungan.getContent());
        return new ModelAndView("/admin/sda/bendungan/form");
    }

    @RequestMapping(value = "/admin/sda/bendungan/edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") BendunganForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Bendungan bendungan = bendunganService.findById(id);
            bendungan.setName(form.getName());
            bendungan.setDescription(form.getDescription());
            bendungan.setContent(form.getFullContent());
            if (form.getPicture() == null || form.getPicture().isEmpty()) {
                bendunganService.save(bendungan);
            } else {
                bendungan.setPictureFilename(form.getPicture().getOriginalFilename());
                bendunganService.save(bendungan, form.getPicture().getInputStream());
            }
            return new ModelAndView("redirect:/admin/sda/bendungan/view.html");
        }
        return new ModelAndView("/admin/sda/bendungan/form");
    }

    private void validateForm(BendunganForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama  belum diisi");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan singkat belum diisi");
        }

        if (StringUtils.isEmpty(form.getFullContent())) {
            errors.reject("fullContent.empty", "Content lengkap belum diisi");
        }

    }
}

