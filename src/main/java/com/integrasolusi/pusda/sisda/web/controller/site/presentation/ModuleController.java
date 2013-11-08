package com.integrasolusi.pusda.sisda.web.controller.site.presentation;

import com.integrasolusi.pusda.sisda.persistence.presentation.Module;
import com.integrasolusi.pusda.sisda.service.presetantion.ModuleService;
import com.integrasolusi.utils.ContentTypeUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 9:03 AM
 */

@Controller
public class ModuleController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ModuleController.class);

    @Autowired
    private ModuleService moduleService;


    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("presentation/module/{id}/**")
    public void downloadModule(@PathVariable("id") Long id,
                               HttpServletResponse response) throws Exception {
        Module module = moduleService.findById(id);
        if (module != null) {
            response.setContentType(contentTypeUtils.getContentType(module.getFilename()));
            moduleService.getDocument(id, response.getOutputStream());
        }
    }

}
