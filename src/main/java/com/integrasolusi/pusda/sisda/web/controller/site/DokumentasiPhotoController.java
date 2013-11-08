package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.DokumentasiPhoto;
import com.integrasolusi.pusda.sisda.service.DokumentasiPhotoService;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.ImageUtils;
import com.integrasolusi.utils.PagingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Programmer   : pancara
 * Date         : 6/20/11
 * Time         : 4:25 PM
 */
@Controller("dokumentasiPhotoController")
public class DokumentasiPhotoController {
    private static Logger logger = LoggerFactory.getLogger(DokumentasiPhotoController.class);
    
    @Autowired
    private PagingHelper pagingHelper;

    @Autowired
    private DokumentasiPhotoService dokumentasiPhotoService;

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("/dokumentasi/photo/thumb/{id}/**")
    public void pictureThumb(@PathVariable("id") Long id,
                             @RequestParam(value = "width", required = false, defaultValue = "100") Integer width,
                             @RequestParam(value = "height", required = false, defaultValue = "0") Integer height,
                             HttpServletResponse response) throws IOException {


        DokumentasiPhoto photo = dokumentasiPhotoService.findById(id);
        if (photo == null)
            return;

        try {
            dokumentasiPhotoService.getBlob(id, width, height, response.getOutputStream());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

    @RequestMapping("/dokumentasi/photo/{id}/**")
    public void picture(HttpServletRequest request,
                        HttpServletResponse response,
                        @PathVariable("id") Long id) throws IOException {

        DokumentasiPhoto photo = dokumentasiPhotoService.findById(id);

        if (photo == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(photo.getFilename()));
        dokumentasiPhotoService.getBlob(id, response.getOutputStream());
    }


}
