package com.integrasolusi.pusda.sisda.web.controller.site;

/**
 * Programmer   : pancara
 * Date         : 7/10/11
 * Time         : 2:43 PM
 */

import com.integrasolusi.pusda.sisda.persistence.Slide;
import com.integrasolusi.pusda.sisda.service.SlideService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class SlideController {
    private static Logger logger = Logger.getLogger(SlideController.class);
    private SlideService slideService;

    @Autowired
    public void setSlideService(SlideService slideService) {
        this.slideService = slideService;
    }

    @RequestMapping("/slide.html")
    public ModelAndView view() {
        java.util.List<Slide> slides = slideService.getPublished();
        return new ModelAndView("slide");
    }

    @RequestMapping("/slide/{id}/**")
    public void picture(HttpServletResponse response, @PathVariable("id") Long id) {
        try {
            BufferedImage image = slideService.getPicture(id);
            response.setContentType("image/jpg");
            ImageIO.write(image, "jpeg", response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @RequestMapping("/slide/next/{currentId}.html")
    public MappingJacksonJsonView next(@PathVariable("currentId") Long currentId) {
        MappingJacksonJsonView view = new MappingJacksonJsonView();
        Slide slide = slideService.getNext(currentId);
        view.addStaticAttribute("slide", slide);
        return view;
    }

}
