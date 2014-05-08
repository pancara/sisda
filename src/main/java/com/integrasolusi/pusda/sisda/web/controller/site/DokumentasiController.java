package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.Dokumentasi;
import com.integrasolusi.pusda.sisda.persistence.DokumentasiPhoto;
import com.integrasolusi.pusda.sisda.service.DokumentasiPhotoService;
import com.integrasolusi.pusda.sisda.service.DokumentasiService;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/20/11
 * Time         : 4:25 PM
 */
@Controller("dokumentasiController")
public class DokumentasiController {
    private static Logger logger = LoggerFactory.getLogger(DokumentasiController.class);
    private PagingHelper pagingHelper;

    private DokumentasiService dokumentasiService;
    private DokumentasiPhotoService dokumentasiPhotoService;

    private ImageUtils imageUtils;
    private ContentTypeUtils contentTypeUtils;

    @Autowired
    public void setContentTypeUtils(ContentTypeUtils contentTypeUtils) {
        this.contentTypeUtils = contentTypeUtils;
    }

    @Autowired
    public void setDokumentasiPhotoService(DokumentasiPhotoService dokumentasiPhotoService) {
        this.dokumentasiPhotoService = dokumentasiPhotoService;
    }

    @Autowired
    public void setDokumentasiService(DokumentasiService dokumentasiService) {
        this.dokumentasiService = dokumentasiService;
    }

    @Autowired
    public void setPagingHelper(PagingHelper pagingHelper) {
        this.pagingHelper = pagingHelper;
    }

    @Autowired
    public void setImageUtils(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    @RequestMapping("/dokumentasi.html")
    public ModelAndView list(HttpServletRequest request) {
        return list(request, 1L);
    }

    @RequestMapping("/dokumentasi/page/{page}.html")
    public ModelAndView list(HttpServletRequest request,
                             @PathVariable("page") Long page) {
        ModelAndView mav = new ModelAndView("site/dokumentasi/list");

        mav.addObject("current", page);
        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);

        Long count = dokumentasiService.countPublished();
        mav.addObject("last", pagingHelper.calcPageCount(count));
        java.util.List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<Dokumentasi> dokumentasiList = dokumentasiService.getPublished(start, pagingHelper.getRowPerPage());

        java.util.Map<Dokumentasi, List<DokumentasiPhoto>> dokumentasiData = new LinkedHashMap<Dokumentasi, List<DokumentasiPhoto>>();
        for (Dokumentasi dokumentasi : dokumentasiList) {
            dokumentasiData.put(dokumentasi, dokumentasiPhotoService.findByDokumentasi(dokumentasi));
        }
        mav.addObject("dokumentasiData", dokumentasiData);


        return mav;
    }

    @RequestMapping("/dokumentasi/thumb/{id}/**")
    public void thumb(HttpServletResponse response,
                      @PathVariable("id") Long id,
                      @RequestParam(value = "width", required = false, defaultValue = "100") Integer width,
                      @RequestParam(value = "height", required = false, defaultValue = "0") Integer height) throws IOException {

        try {
            response.setContentType("image/jpg");
            dokumentasiService.getResizedTitlePicture(id, width, height, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

    }

}
