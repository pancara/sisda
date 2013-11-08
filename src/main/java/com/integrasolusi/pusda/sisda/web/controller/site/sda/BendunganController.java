package com.integrasolusi.pusda.sisda.web.controller.site.sda;

import com.integrasolusi.pusda.sisda.persistence.sda.Bendungan;
import com.integrasolusi.pusda.sisda.service.sda.BendunganService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 6:14 PM
 */
@Controller
public class BendunganController {
    private Logger logger = LoggerFactory.getLogger(BendunganController.class);

    private BendunganService bendunganService;

    @Autowired
    public void setBendunganService(BendunganService bendunganService) {
        this.bendunganService = bendunganService;
    }

    @RequestMapping("/sda/bendungan.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("site/sda/bendungan/intro");

        List<Bendungan> bendunganList = bendunganService.findAlls();
        mav.addObject("bendunganList", bendunganList);

        return mav;
    }

    @RequestMapping("/popup/sda/bendungan/{id}.html")
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("site/sda/bendungan/detail");
        Bendungan bendungan = bendunganService.findById(id);
        if (bendungan == null) {
            return list();
        }
        mav.addObject("bendungan", bendungan);
        return mav;
    }

    @RequestMapping("/sda/bendungan/photo/{id}/**")
    public void thumb(@PathVariable("id") Long id, HttpServletResponse response) {
        try {
            bendunganService.getThumbPhoto(id, response.getOutputStream());
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
        }
    }
}
