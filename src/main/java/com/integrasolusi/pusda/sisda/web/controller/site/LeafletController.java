package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.Leaflet;
import com.integrasolusi.pusda.sisda.service.LeafletService;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * User: pancara
 * Date: 9/3/12
 * Time: 10:49 AM
 */

@Controller
@RequestMapping("/leaflet")
public class LeafletController {
    @Autowired
    private LeafletService leafletService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @Autowired
    private PagingHelper pagingHelper;

    @RequestMapping("list.html")
    public ModelAndView list(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        return list(1L, keyword);
    }


    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        ModelAndView mav = new ModelAndView("site/leaflet/list");
        mav.addObject("keyword", keyword);

        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);

        Long count = getCount(keyword);
        mav.addObject("last", pagingHelper.calcPageCount(count));
        
        mav.addObject("pages", pagingHelper.getDisplayedPages(page, count));

        List<Leaflet> leafletList = getLeafletList(keyword, start, pagingHelper.getRowPerPage());
        mav.addObject("leafletList", leafletList);
        java.util.Map<Leaflet, Boolean> pictures = new HashMap<Leaflet, Boolean>();
        for (Leaflet leaflet : leafletList) {
            pictures.put(leaflet, leafletService.hasPicture(leaflet.getId()));
        }
        mav.addObject("pictures", pictures);

        return mav;
    }

    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return leafletService.countPublished();
        else
            return leafletService.countPublishedByKeyword(keyword);
    }

    private java.util.List<Leaflet> getLeafletList(String keyword, Long start, Long count) {
        if (StringUtils.isEmpty(keyword))
            return leafletService.getPublished(start, count);
        else
            return leafletService.getPublishedByKeyword(keyword, start, count);
    }

    @RequestMapping("thumb/{id}/**")
    public void thumb(@PathVariable("id") Long id,
                      @RequestParam(value = "width", required = false) Integer width,
                      @RequestParam(value = "height", required = false) Integer height,
                      HttpServletResponse response) throws Exception {
        Leaflet leaflet = leafletService.findById(id);
        if (leaflet == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(leaflet.getThumbFilename()));
        if (width != null || height != null) {
            int w = width == null ? 0 : width;
            int h = height == null ? 0 : height;
            try {
                leafletService.getThumbfileBlob(id, response.getOutputStream(), w, h);
            } catch (Exception e) {
                leafletService.getThumbfileBlob(id, response.getOutputStream());
            }
        } else {
            leafletService.getThumbfileBlob(id, response.getOutputStream());
        }
    }

    @RequestMapping("document/{id}/**")
    public void document(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Leaflet leaflet = leafletService.findById(id);
        if (leaflet == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(leaflet.getFilename()));
        leafletService.getDocumentBlob(id, response.getOutputStream());
    }
}
