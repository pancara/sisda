package com.integrasolusi.pusda.sisda.web.controller.admin.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.sungai.Sungai;
import com.integrasolusi.pusda.sisda.persistence.sda.sungai.SungaiSummary;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.service.sda.sungai.SungaiService;
import com.integrasolusi.pusda.sisda.service.sda.sungai.SungaiSummaryService;
import com.integrasolusi.pusda.sisda.web.form.sda.sungai.SungaiForm;
import com.integrasolusi.pusda.sisda.web.form.sda.sungai.SungaiSummaryForm;
import com.integrasolusi.utils.ContentTypeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pancara
 * Date: 8/6/12
 * Time: 11:39 AM
 */

@Controller("adminSungaiController")
@RequestMapping("/admin/sda/sungai")
public class SungaiController {
    @Autowired
    private SungaiService sungaiService;

    @Autowired
    private SungaiSummaryService sungaiSummaryService;

    @Autowired
    private YearService yearService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private DasService dasService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping(value = "list.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("admin/sda/sungai/list_ws");
        List<WilayahSungai> listWs = wilayahSungaiService.findAlls();
        mav.addObject("listWs", listWs);

        Map<WilayahSungai, SungaiSummary> mapSummary = new HashMap<>();
        for (WilayahSungai ws : listWs) {
            mapSummary.put(ws, sungaiSummaryService.findByWilayahSungai(ws));
        }

        mav.addObject("mapSummary", mapSummary);
        return mav;
    }

    @RequestMapping(value = "list/{wsId}.html")
    public ModelAndView list(@PathVariable("wsId") Long wsId) {
        ModelAndView mav = new ModelAndView("admin/sda/sungai/list_das");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        List<Das> listDas = dasService.findByWilayahSungai(ws);
        mav.addObject("listDas", listDas);


        return mav;
    }

    @RequestMapping(value = "list/{wsId}/{dasId}.html")
    public ModelAndView list(@PathVariable("wsId") Long wsId,
                             @PathVariable("dasId") Long dasId) {
        ModelAndView mav = new ModelAndView("admin/sda/sungai/list_sungai");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        Das das = dasService.findById(dasId);
        mav.addObject("das", das);

        mav.addObject("listSungai", sungaiService.findByDas(das));
        return mav;
    }

    @RequestMapping(value = "add/{wsId}/{dasId}.html", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable("wsId") Long wsId,
                            @PathVariable("dasId") Long dasId,
                            @ModelAttribute("form") SungaiForm form) {
        return createFormView(wsId, dasId);
    }

    @RequestMapping(value = "add/{wsId}/{dasId}.html", method = RequestMethod.POST)
    public ModelAndView add(@PathVariable("wsId") Long wsId,
                            @PathVariable("dasId") Long dasId,
                            @ModelAttribute("form") SungaiForm form,
                            Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(null, form, dasId);
            return createSuccessView(wsId, dasId);
        }

        return createFormView(wsId, dasId);
    }

    private ModelAndView createSuccessView(Long wsId, Long dasId) {
        String path = String.format("redirect:/admin/sda/sungai/list/%d/%d.html", wsId, dasId);
        return new ModelAndView(path);
    }

    @RequestMapping(value = "edit/{wsId}/{dasId}/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("wsId") Long wsId,
                             @PathVariable("dasId") Long dasId,
                             @PathVariable("id") Long id,
                             @ModelAttribute("form") SungaiForm form) {
        Sungai sungai = sungaiService.findById(id);
        form.setName(sungai.getName());
        form.setDescription(sungai.getDescription());
        form.setMapUrl(sungai.getMapUrl());

        return createFormView(wsId, dasId);
    }

    @RequestMapping(value = "edit/{wsId}/{dasId}/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("wsId") Long wsId,
                             @PathVariable("dasId") Long dasId,
                             @PathVariable("id") Long id,
                             @ModelAttribute("form") SungaiForm form,
                             Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(id, form, dasId);
            return createSuccessView(wsId, dasId);
        }
        return createFormView(wsId, dasId);
    }

    @RequestMapping(value = "{wsId}/{dasId}/remove.html", method = RequestMethod.POST)
    public ModelAndView remove(@PathVariable("wsId") Long wsId,
                               @PathVariable("dasId") Long dasId,
                               @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            sungaiService.removeByIds(ids);
        }
        return createSuccessView(wsId, dasId);
    }

    private void saveForm(Long id, SungaiForm form, Long dasId) throws IOException {
        Sungai sungai = (id == null ?
                new Sungai() :
                sungaiService.findById(id));

        sungai.setName(form.getName());
        sungai.setDescription(form.getDescription());
        sungai.setMapUrl(form.getMapUrl());
        
        Das das = dasService.findById(dasId);
        sungai.setDas(das);
        


        if (form.getFile() == null || form.getFile().isEmpty()) {
            sungaiService.save(sungai);
        } else {
            sungai.setFilename(form.getFile().getOriginalFilename());
            sungaiService.save(sungai, form.getFile().getInputStream());
        }
    }

    private void validateForm(SungaiForm form, Errors errors) {

        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

    }

    private ModelAndView createFormView(Long wsId, Long dasId) {
        ModelAndView mav = new ModelAndView("/admin/sda/sungai/form");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        Das das = dasService.findById(dasId);
        mav.addObject("das", das);

        return mav;
    }

    @RequestMapping("{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Sungai sungai = sungaiService.findById(id);
        if (sungai == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(sungai.getFilename()));
        sungaiService.getBlob(id, response.getOutputStream());
    }

    @RequestMapping(value = "summary/upload/{wsId}.html", method = RequestMethod.GET)
    public ModelAndView addSummary(@PathVariable("wsId") Long wsId,
                                   @ModelAttribute("form") SungaiSummaryForm form) {
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        SungaiSummary summary = sungaiSummaryService.findByWilayahSungai(ws);
        
        return createSummaryFormView(form, wsId);
    }

    @RequestMapping(value = "summary/upload/{wsId}.html", method = RequestMethod.POST)
    public ModelAndView addSummary(@PathVariable("wsId") Long wsId,
                                   @ModelAttribute("form") SungaiSummaryForm form,
                                   Errors errors) throws IOException {
        validateSummaryForm(form, errors);
        if (!errors.hasErrors()) {
            saveSummaryForm(wsId, form);
            return createSuccessSummaryView();
        }

        return createSummaryFormView(form, wsId);
    }

    private ModelAndView createSummaryFormView(SungaiSummaryForm form, Long wsId) {
        ModelAndView mav = new ModelAndView("/admin/sda/sungai/summary_form");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        return mav;
    }

    private void validateSummaryForm(SungaiSummaryForm form, Errors errors) {
         
    }

    private void saveSummaryForm(Long wsId, SungaiSummaryForm form) throws IOException {
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        SungaiSummary summary = sungaiSummaryService.findByWilayahSungai(ws);
        if (summary == null) {
            summary = new SungaiSummary();
            summary.setWs(ws);
        }

        if (form.getFile() == null || form.getFile().isEmpty()) {
            sungaiSummaryService.save(summary);
        } else {
            summary.setFilename(form.getFile().getOriginalFilename());
            sungaiSummaryService.save(summary, form.getFile().getInputStream());
        }
    }

    private ModelAndView createSuccessSummaryView() {
        String path = "redirect:/admin/sda/sungai/list.html";
        return new ModelAndView(path);
    }

    @RequestMapping("summary/{summaryId}/**")
    public void downloadSummary(@PathVariable("summaryId") Long summaryId, HttpServletResponse response) throws IOException {

        SungaiSummary summary = sungaiSummaryService.findById(summaryId);
        if (summary == null) {
            return;
        }

        response.setContentType(contentTypeUtils.getContentType(summary.getFilename()));
        sungaiSummaryService.getBlob(summary.getId(), response.getOutputStream());
    }

}
