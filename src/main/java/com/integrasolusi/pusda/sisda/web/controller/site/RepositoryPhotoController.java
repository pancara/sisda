package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.Photo;
import com.integrasolusi.pusda.sisda.service.PhotoService;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.ImageUtils;
import com.integrasolusi.utils.StreamHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 2:32 PM
 */
@Controller
public class RepositoryPhotoController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RepositoryPhotoController.class);

    @Autowired
    private PhotoService photoService;

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("/repo/photo/{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Photo photo = photoService.findById(id);
        if (photo != null) {
            response.setContentType(contentTypeUtils.getContentType(photo.getFilename()));
            photoService.getBlob(id, response.getOutputStream());
            response.getOutputStream().flush();
        }
    }

    @RequestMapping("/repo/photo/thumb/{id}/**")
    public void thumb(@PathVariable("id") Long id,
                      @RequestParam(value = "width", required = false, defaultValue = "100") Integer width,
                      @RequestParam(value = "height", required = false, defaultValue = "0") Integer height,
                      HttpServletResponse response) throws IOException {
        Photo photo = photoService.findById(id);
        if (photo != null) {
            response.setContentType(contentTypeUtils.getContentType(photo.getFilename()));
            photoService.getBlob(id, width, height, response.getOutputStream());
            response.getOutputStream().flush();
        }
    }
}
