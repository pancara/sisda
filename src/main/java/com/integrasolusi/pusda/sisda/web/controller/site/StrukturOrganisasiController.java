package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.StrukturOrganisasi;
import com.integrasolusi.pusda.sisda.service.StrukturOrganisasiService;
import com.integrasolusi.utils.ContentTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Programmer   : pancara
 * Date         : 7/20/11
 * Time         : 1:30 PM
 */
@Controller
public class StrukturOrganisasiController {

    @Autowired
    private StrukturOrganisasiService strukturOrganisasiService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("/struktur_organisasi.html")
    public ModelAndView intro() {
        ModelAndView mav = new ModelAndView("site/struktur_organisasi/intro");
        mav.addObject("soList", strukturOrganisasiService.findAlls());
        return mav;
    }

    @RequestMapping("/popup/struktur_organisasi/view/{id}.html")
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("site/struktur_organisasi/view");
        mav.addObject("so", strukturOrganisasiService.findById(id));
        return mav;
    }

    @RequestMapping("/struktur_organisasi/{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        StrukturOrganisasi so = strukturOrganisasiService.findById(id);
        if (so != null) {
            response.setContentType(contentTypeUtils.getContentType(so.getFilename()));
            strukturOrganisasiService.getBlob(id, response.getOutputStream());
        }
    }

}
