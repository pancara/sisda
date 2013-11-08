package com.integrasolusi.pusda.sisda.web.controller.site.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosHidrologi;
import com.integrasolusi.pusda.sisda.service.sda.hidrologi.PosHidrologiService;
import com.integrasolusi.utils.ContentTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 8:48 PM
 */

@Controller
public class PosHidrologiController {

    @Autowired
    private PosHidrologiService posHidrologiService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping("/sda/hidrologi/pos_hidrologi.html")
    public ModelAndView index() {
        PosHidrologi pos = posHidrologiService.findActive();
        if (pos != null) {
            String path = String.format("redirect:/download/sda/hidrologi/pos_hidrologi/%d/%s", pos.getId(), pos.getFilename());
            return new ModelAndView(path);
        }
        return null;
    }

    @RequestMapping("/sda/hidrologi/pos_hidrologi/{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        PosHidrologi posHidrologi = posHidrologiService.findById(id);
        if (posHidrologi == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(posHidrologi.getFilename()));
        posHidrologiService.getBlob(id, response.getOutputStream());
    }

}
