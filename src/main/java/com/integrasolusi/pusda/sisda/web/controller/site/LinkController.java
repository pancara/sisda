package com.integrasolusi.pusda.sisda.web.controller.site;

/**
 * Programmer   : pancara
 * Date         : 6/27/11
 * Time         : 7:46 PM
 */

import com.integrasolusi.pusda.sisda.service.LinkService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LinkController {
    private static Logger logger = Logger.getLogger(LinkController.class);
    private LinkService linkService;

    @Autowired
    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }

    @RequestMapping("/link/picture/{id}/**")
    public void getPicture(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        linkService.getBlob(id, response.getOutputStream());
    }

}
