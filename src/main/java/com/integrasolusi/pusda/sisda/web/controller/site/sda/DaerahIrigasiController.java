package com.integrasolusi.pusda.sisda.web.controller.site.sda;

import com.integrasolusi.pusda.sisda.persistence.sda.di.DiData;
import com.integrasolusi.pusda.sisda.persistence.sda.di.DiDi;
import com.integrasolusi.pusda.sisda.persistence.sda.di.DiType;
import com.integrasolusi.pusda.sisda.service.sda.SdaService;
import com.integrasolusi.pusda.sisda.service.sda.di.DiDataService;
import com.integrasolusi.pusda.sisda.service.sda.di.DiDiService;
import com.integrasolusi.pusda.sisda.service.sda.di.DiTypeService;
import com.integrasolusi.utils.ContentTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 10:44 AM
 */

@Controller
public class DaerahIrigasiController {
    private static final Long SDA_ID = 2L;

    @Autowired
    private DiTypeService diTypeService;

    @Autowired
    private DiDiService diDIService;

    @Autowired
    private DiDataService diDataService;

    @Autowired
    private SdaService sdaService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;


    private void populateSda(ModelAndView mav) {
        mav.addObject("sda", sdaService.findById(DaerahIrigasiController.SDA_ID));
    }

    @RequestMapping("/sda/daerah_irigasi.html")
    public ModelAndView intro() {
        ModelAndView mav = new ModelAndView("site/sda/daerah_irigasi/index");
        populateSda(mav);

        Map mapDi = new LinkedHashMap();
        for (DiType type : diTypeService.findAlls()) {
            List<DiDi> listDi = diDIService.findByDiType(type);
            Map mapDiDi = new LinkedHashMap();
            for (DiDi di : listDi) {
                mapDiDi.put(di, diDataService.findByDi(di));
            }
            mapDi.put(type, mapDiDi);
        }

        mav.addObject("mapDi", mapDi);
        return mav;
    }


    @RequestMapping("/sda/daerah_irigasi/type/{type}/**")
    public void downloadType(@PathVariable("type") Long typeId,
                             HttpServletResponse response) throws IOException {
        DiType type = diTypeService.findById(typeId);
        String contentType = contentTypeUtils.getContentType(type.getFilename());
        response.setContentType(contentType);
        diTypeService.getBlob(typeId, response.getOutputStream());
    }

    @RequestMapping("/sda/daerah_irigasi/di/{type}/{di}/**")
    public void downloadDI(@PathVariable("type") Long typeId,
                             @PathVariable("di") Long diId,
                             HttpServletResponse response) throws IOException {
        DiDi di = diDIService.findById(diId);
        String contentType = contentTypeUtils.getContentType(di.getFilename());
        response.setContentType(contentType);
        diDIService.getBlob(diId, response.getOutputStream());
    }

    @RequestMapping("/sda/daerah_irigasi/data/{type}/{di}/{data}/**")
    public void downloadData(@PathVariable("type") Long typeId,
                             @PathVariable("di") Long diId,
                             @PathVariable("data") Long dataId,
                             HttpServletResponse response) throws IOException {
        DiData diData = diDataService.findById(dataId);
        String contentType = contentTypeUtils.getContentType(diData.getFilename());
        response.setContentType(contentType);
        diDataService.getBlob(dataId, response.getOutputStream());
    }


}
