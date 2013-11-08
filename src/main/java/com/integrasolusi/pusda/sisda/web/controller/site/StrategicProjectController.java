package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.Project;
import com.integrasolusi.pusda.sisda.service.ProjectService;
import com.integrasolusi.utils.ContentTypeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 9:03 AM
 */

@Controller
public class StrategicProjectController {
    private static Logger logger = Logger.getLogger(StrategicProjectController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("/proyek_strategis.html")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("site/project_strategic/index");
        mav.addObject("listProject", projectService.findAlls());
        return mav;
    }

    @RequestMapping("/popup/proyek_strategis/{id}.html")
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("site/project_strategic/detail");
        Project project = projectService.findById(id);
        if (project == null) {
            return new ModelAndView("redirect:/proyek_strategis.html");
        }
        mav.addObject("project", project);
        return mav;
    }

    @RequestMapping("/proyek_strategis/picture/{id}/**")
    public void thumb(@PathVariable("id") Long id, HttpServletResponse response) {
        Project project = projectService.findById(id);
        if (project == null)
            return;

        try {
            response.setContentType(contentTypeUtils.getContentType(project.getPicture()));
            projectService.getPicture(id, response.getOutputStream());
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
        }
    }

}
