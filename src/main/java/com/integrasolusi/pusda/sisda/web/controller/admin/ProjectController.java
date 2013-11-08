package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.Project;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.service.ProjectService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.web.form.ProjectForm;
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
@Controller("adminProjectController")
@RequestMapping("/admin/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private YearService yearService;

    @RequestMapping("index.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("admin/project/index");
        List<Project> listProject = projectService.findAlls();
        mav.addObject("listProject", listProject);
        return mav;
    }

    @RequestMapping("remove/{id}.html")
    public MappingJacksonJsonView remove(@PathVariable("id") Long id) {
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        try {
            projectService.removeById(id);
            view.addStaticAttribute("result", true);
        } catch (Exception e) {
            view.addStaticAttribute("result", false);
        }
        return view;
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") ProjectForm form) {
        return createFormView();
    }

    private ModelAndView createFormView() {
        ModelAndView mav = new ModelAndView("/admin/project/form");
        populateLookupData(mav);
        return mav;
    }

    private void populateLookupData(ModelAndView mav) {
        mav.addObject("lookupYear", yearService.findAlls());
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") ProjectForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Project project = new Project();

            project.setName(form.getName());
            project.setDescription(form.getDescription());
            project.setContent(form.getFullContent());

            project.setLatitude(form.getLatitude());
            project.setLongitude(form.getLongitude());

            Year year = yearService.findById(form.getYear());
            project.setYear(year);

            Long index = form.getIndex() != null ? form.getIndex() : projectService.getNextIndex();
            project.setIndex(index);

            if (form.getPicture() == null || form.getPicture().isEmpty()) {
                projectService.save(project);
            } else {
                project.setPicture(form.getPicture().getOriginalFilename());
                projectService.save(project, form.getPicture().getInputStream());
            }
            return createRedirectSuccess();
        }
        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") ProjectForm form) {
        Project project = projectService.findById(id);
        form.setName(project.getName());
        form.setDescription(project.getDescription());
        form.setFullContent(project.getContent());

        if (project.getYear() != null) {
            form.setYear(project.getYear().getId());
        }

        form.setLatitude(project.getLatitude());
        form.setLongitude(project.getLongitude());
        form.setIndex(project.getIndex());

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") ProjectForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            Project project = projectService.findById(id);
            
            project.setName(form.getName());
            project.setDescription(form.getDescription());
            project.setContent(form.getFullContent());

            project.setLatitude(form.getLatitude());
            project.setLongitude(form.getLongitude());

            Year year = yearService.findById(form.getYear());
            project.setYear(year);

            Long index = form.getIndex() != null ? form.getIndex() : projectService.getNextIndex();
            project.setIndex(index);



            if (form.getPicture() == null || form.getPicture().isEmpty()) {
                projectService.save(project);
            } else {
                project.setPicture(form.getPicture().getOriginalFilename());
                projectService.save(project, form.getPicture().getInputStream());
            }
            return createRedirectSuccess();
        }
        return createFormView();
    }

    public ModelAndView createRedirectSuccess() {
        return new ModelAndView("redirect:/admin/project/index.html");
    }

    private void validateForm(ProjectForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama  belum diisi");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan singkat belum diisi");
        }

        if (form.getYear() == null) {
            errors.reject("year.empty", "Tahun belum diisi");
        }

    }
}

