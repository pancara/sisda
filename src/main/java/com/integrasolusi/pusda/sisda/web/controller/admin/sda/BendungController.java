package com.integrasolusi.pusda.sisda.web.controller.admin.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.bendung.Bendung;
import com.integrasolusi.pusda.sisda.persistence.sda.bendung.BendungSummary;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.bendung.BendungService;
import com.integrasolusi.pusda.sisda.service.sda.bendung.BendungSummaryService;
import com.integrasolusi.pusda.sisda.web.form.sda.bendung.BendungForm;
import com.integrasolusi.pusda.sisda.web.form.sda.bendung.BendungSummaryForm;
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

@Controller("adminBendungController")
@RequestMapping("/admin/sda/bendung")
public class BendungController {
    @Autowired
    private BendungService bendungService;

    @Autowired
    private BendungSummaryService bendungSummaryService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private DasService dasService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping(value = "list.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("admin/sda/bendung/list_ws");
        List<WilayahSungai> listWs = wilayahSungaiService.findAlls();
        mav.addObject("listWs", listWs);

        Map<WilayahSungai, BendungSummary> mapSummary = new HashMap<>();
        for (WilayahSungai ws : listWs) {
            mapSummary.put(ws, bendungSummaryService.findByWilayahSungai(ws));
        }

        mav.addObject("mapSummary", mapSummary);
        return mav;
    }

    @RequestMapping(value = "list/{wsId}.html")
    public ModelAndView list(@PathVariable("wsId") Long wsId) {
        ModelAndView mav = new ModelAndView("admin/sda/bendung/list_bendung");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        mav.addObject("listBendung", bendungService.findByWs(ws));

        return mav;
    }

    @RequestMapping(value = "add/{wsId}.html", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable("wsId") Long wsId,
                            @ModelAttribute("form") BendungForm form) {
        return createFormView(wsId);
    }

    @RequestMapping(value = "add/{wsId}.html", method = RequestMethod.POST)
    public ModelAndView add(@PathVariable("wsId") Long wsId,
                            @ModelAttribute("form") BendungForm form,
                            Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(null, form);
            return createSuccessView(wsId);
        }

        return createFormView(wsId);
    }

    private ModelAndView createSuccessView(Long wsId) {
        String path = String.format("redirect:/admin/sda/bendung/list/%d.html", wsId);
        return new ModelAndView(path);
    }

    @RequestMapping(value = "edit/{wsId}/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("wsId") Long wsId,
                             @PathVariable("id") Long id,
                             @ModelAttribute("form") BendungForm form) {
        Bendung bendung = bendungService.findById(id);
        form.setName(bendung.getName());
        form.setDescription(bendung.getDescription());
        form.setMapUrl(bendung.getMapUrl());
        if (bendung.getDas() != null) {
            form.setDas(bendung.getDas().getId());
        }

        return createFormView(wsId);
    }

    @RequestMapping(value = "edit/{wsId}/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("wsId") Long wsId,
                             @PathVariable("id") Long id,
                             @ModelAttribute("form") BendungForm form,
                             Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(id, form);
            return createSuccessView(wsId);
        }
        return createFormView(wsId);
    }

    @RequestMapping(value = "{wsId}/{dasId}/remove.html", method = RequestMethod.POST)
    public ModelAndView remove(@PathVariable("wsId") Long wsId,
                               @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            bendungService.removeByIds(ids);
        }
        return createSuccessView(wsId);
    }

    private void saveForm(Long id, BendungForm form) throws IOException {
        Bendung bendung = (id == null ?
                new Bendung() :
                bendungService.findById(id));

        bendung.setName(form.getName());
        bendung.setDescription(form.getDescription());
        bendung.setMapUrl(form.getMapUrl());

        Das das = dasService.findById(form.getDas());
        bendung.setDas(das);


        if (form.getFile() == null || form.getFile().isEmpty()) {
            bendungService.save(bendung);
        } else {
            bendung.setFilename(form.getFile().getOriginalFilename());
            bendungService.save(bendung, form.getFile().getInputStream());
        }
    }

    private void validateForm(BendungForm form, Errors errors) {

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }

    }

    private ModelAndView createFormView(Long wsId) {
        ModelAndView mav = new ModelAndView("/admin/sda/bendung/form");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        mav.addObject("listDas", dasService.findByWilayahSungai(ws));

        return mav;
    }

    @RequestMapping("{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Bendung bendung = bendungService.findById(id);
        if (bendung == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(bendung.getFilename()));
        bendungService.getBlob(id, response.getOutputStream());
    }

    @RequestMapping(value = "summary/upload/{wsId}.html", method = RequestMethod.GET)
    public ModelAndView addSummary(@PathVariable("wsId") Long wsId,
                                   @ModelAttribute("form") BendungSummaryForm form) {
        return createSummaryFormView(form, wsId);
    }

    @RequestMapping(value = "summary/upload/{wsId}.html", method = RequestMethod.POST)
    public ModelAndView addSummary(@PathVariable("wsId") Long wsId,
                                   @ModelAttribute("form") BendungSummaryForm form,
                                   Errors errors) throws IOException {
        validateSummaryForm(form, errors);
        if (!errors.hasErrors()) {
            saveSummaryForm(wsId, form);
            return createSuccessSummaryView();
        }

        return createSummaryFormView(form, wsId);
    }

    private void validateSummaryForm(BendungSummaryForm form, Errors errors) {

    }

    private ModelAndView createSummaryFormView(BendungSummaryForm form, Long wsId) {
        ModelAndView mav = new ModelAndView("/admin/sda/bendung/summary_form");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        return mav;
    }


    private void saveSummaryForm(Long wsId, BendungSummaryForm form) throws IOException {
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        BendungSummary summary = bendungSummaryService.findByWilayahSungai(ws);
        if (summary == null) {
            summary = new BendungSummary();
            summary.setWs(ws);
        }

        if (form.getFile() == null || form.getFile().isEmpty()) {
            bendungSummaryService.save(summary);
        } else {
            summary.setFilename(form.getFile().getOriginalFilename());
            bendungSummaryService.save(summary, form.getFile().getInputStream());
        }
    }

    private ModelAndView createSuccessSummaryView() {
        String path = "redirect:/admin/sda/bendung/list.html";
        return new ModelAndView(path);
    }

    @RequestMapping("summary/{summaryId}/**")
    public void downloadSummary(@PathVariable("summaryId") Long summaryId, HttpServletResponse response) throws IOException {

        BendungSummary summary = bendungSummaryService.findById(summaryId);
        if (summary == null) {
            return;
        }

        response.setContentType(contentTypeUtils.getContentType(summary.getFilename()));
        bendungSummaryService.getBlob(summary.getId(), response.getOutputStream());
    }

}
