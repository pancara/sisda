package com.integrasolusi.pusda.sisda.web.controller.site.tkpsda;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscDocument;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscFolder;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.service.tkpsda.misc.MiscDocumentService;
import com.integrasolusi.pusda.sisda.service.tkpsda.misc.MiscFolderService;
import com.integrasolusi.utils.ContentTypeUtils;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Programmer : pancara
 * Date       : 11/10/12
 * Time       : 10:55 PM
 */
@Controller
public class MiscController {
    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private YearService yearService;

    @Autowired
    private MiscFolderService miscFolderService;

    @Autowired
    private MiscDocumentService miscDocumentService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("/tkpsda/lain_lain/{ws}/view.html")
    public ModelAndView list(@PathVariable("ws") Long wilayahSungaiId) {
        ModelAndView mav = new ModelAndView("site/tkpsda/lain_lain/list");
        WilayahSungai ws = wilayahSungaiService.findById(wilayahSungaiId);
        mav.addObject("ws", ws);
        List<Year> yearList = yearService.findAlls();

        Map<Year, Object> yearFolderMap = new LinkedHashMap<>();
        for (Year year : yearList) {
            List<MiscFolder> folders = miscFolderService.findByWilayahSungaiAndYear(ws, year);
            if (folders.size() > 0) {
                HashMap<MiscFolder, Object> folderDocumentMap = new LinkedHashMap<>();
                for (MiscFolder folder : folders) {
                    List<MiscDocument> documents = miscDocumentService.findByFolder(folder);
                    folderDocumentMap.put(folder, documents);
                }

                yearFolderMap.put(year, folderDocumentMap);
            }
        }
        mav.addObject("yearFolderMap", yearFolderMap);
        return mav;
    }

    @RequestMapping("/tkpsda/lain_lain/{ws}/{year}/{folder}/{document}/**")
    public void downloadDocument(@PathVariable("ws") Long wsId,
                                 @PathVariable("year") Long yearId,
                                 @PathVariable("folder") Long folderId,
                                 @PathVariable("document") Long documentId,
                                 HttpServletResponse response) throws IOException {
        MiscDocument document = miscDocumentService.findById(documentId);
        if (document == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (!BooleanUtils.isTrue(document.getActive())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }


        response.setContentType(contentTypeUtils.getContentType(document.getFilename()));
        miscDocumentService.getBlob(document.getId(), response.getOutputStream());
    }

}
