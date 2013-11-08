package com.integrasolusi.pusda.sisda.web.controller.site.tkpsda;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.Activity;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.ActivityDocument;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.service.tkpsda.activity.ActivityDocumentService;
import com.integrasolusi.pusda.sisda.service.tkpsda.activity.ActivityService;
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
public class ActivityController {
    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private YearService yearService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityDocumentService activityDocumentService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("/tkpsda/hasil_sidang/{ws}/view.html")
    public ModelAndView list(@PathVariable("ws") Long wilayahSungaiId) {
        ModelAndView mav = new ModelAndView("site/tkpsda/hasil_sidang/list");
        WilayahSungai ws = wilayahSungaiService.findById(wilayahSungaiId);
        mav.addObject("ws", ws);
        List<Year> yearList = yearService.findAlls();

        Map<Year, Object> data = new LinkedHashMap<>();
        for (Year year : yearList) {
            List<Activity> activities = activityService.findByWilayahSungaiAndYear(ws, year);
            if (activities.size() > 0) {
                HashMap<Activity, Object> activitiesDocuments = new LinkedHashMap<>();
                for (Activity activity : activities) {
                    List<ActivityDocument> documents = activityDocumentService.findByActivity(activity);
                    activitiesDocuments.put(activity, documents);
                }

                data.put(year, activitiesDocuments);
            }
        }
        mav.addObject("data", data);
        return mav;
    }

    @RequestMapping("/tkpsda/hasil_sidang/{ws}/{year}/{activity}/{document}/**")
    public void downloadDocument(@PathVariable("ws") Long wsId,
                                 @PathVariable("year") Long yearId,
                                 @PathVariable("activity") Long activityId,
                                 @PathVariable("document") Long documentId,
                                 HttpServletResponse response) throws IOException {
        ActivityDocument document = activityDocumentService.findById(documentId);
        if (document == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (!BooleanUtils.isTrue(document.getActive())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
            

        response.setContentType(contentTypeUtils.getContentType(document.getFilename()));
        activityDocumentService.getBlob(document.getId(), response.getOutputStream());
    }

}
