package com.integrasolusi.pusda.sisda.web.controller.admin.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.KualitasAir;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.hidrologi.KualitasAirService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.web.form.sda.hidrologi.KualitasAirForm;
import com.integrasolusi.utils.ContentTypeUtils;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pancara
 * Date: 8/6/12
 * Time: 11:39 AM
 */

@Controller("adminKualitasAirController")
@RequestMapping("/admin/sda/hidrologi/kualitas_air")
public class KualitasAirController {
    @Autowired
    private KualitasAirService kualitasAirService;

    @Autowired
    private PagingHelper pagingHelper;

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
        ModelAndView mav = new ModelAndView("admin/sda/hidrologi/kualitas_air/list_ws");
        mav.addObject("wsList", wilayahSungaiService.findAlls());
        return mav;
    }

    @RequestMapping(value = "list/{wsId}.html")
    public ModelAndView list(@PathVariable("wsId") Long wsId) {
        ModelAndView mav = new ModelAndView("admin/sda/hidrologi/kualitas_air/list_das");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        List<Das> dasList = dasService.findByWilayahSungai(ws);
        mav.addObject("dasList", dasList);
        return mav;
    }

    @RequestMapping(value = "list/{wsId}/{dasId}.html")
    public ModelAndView list(@PathVariable("wsId") Long wsId,
                             @PathVariable("dasId") Long dasId) {
        ModelAndView mav = new ModelAndView("admin/sda/hidrologi/kualitas_air/list_ka");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        Das das = dasService.findById(dasId);
        mav.addObject("das", das);

        mav.addObject("listKa", kualitasAirService.findByDas(das));
        return mav;
    }

    @RequestMapping(value = "{wsId}/{dasId}/add.html", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable("wsId") Long wsId,
                            @PathVariable("dasId") Long dasId,
                            @ModelAttribute("form") KualitasAirForm form) {
        ModelAndView mav = createFormView(wsId, dasId);
        return mav;
    }

    @RequestMapping(value = "{wsId}/{dasId}/add.html", method = RequestMethod.POST)
    public ModelAndView add(@PathVariable("wsId") Long wsId,
                            @PathVariable("dasId") Long dasId,
                            @ModelAttribute("form") KualitasAirForm form,
                            Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(null, form, dasId);
            return createSuccessView(wsId, dasId);
        }

        return createFormView(wsId, dasId);
    }

    private ModelAndView createSuccessView(Long wsId, Long dasId) {
        String path = String.format("redirect:/admin/sda/hidrologi/kualitas_air/list/%d/%d.html", wsId, dasId);
        return new ModelAndView(path);
    }

    @RequestMapping(value = "{wsId}/{dasId}/edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("wsId") Long wsId,
                             @PathVariable("dasId") Long dasId,
                             @PathVariable("id") Long id,
                             @ModelAttribute("form") KualitasAirForm form) {
        KualitasAir tma = kualitasAirService.findById(id);
        form.setDescription(tma.getDescription());
        if (tma.getYear() != null) {
            form.setYear(tma.getYear().getId());
        }

        return createFormView(wsId, dasId);
    }

    @RequestMapping(value = "{wsId}/{dasId}/edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("wsId") Long wsId,
                             @PathVariable("dasId") Long dasId,
                             @PathVariable("id") Long id,
                             @ModelAttribute("form") KualitasAirForm form,
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
            kualitasAirService.removeByIds(ids);
        }
        return createSuccessView(wsId, dasId);
    }

    private void saveForm(Long id, KualitasAirForm form, Long dasId) throws IOException {
        KualitasAir tma = (id == null ?
                new KualitasAir() :
                kualitasAirService.findById(id));

        tma.setDescription(form.getDescription());

        Year year = yearService.findById(form.getYear());
        tma.setYear(year);

        Das das = dasService.findById(dasId);
        tma.setDas(das);


        if (form.getFile() == null || form.getFile().isEmpty()) {
            kualitasAirService.save(tma);
        } else {
            tma.setFilename(form.getFile().getOriginalFilename());
            kualitasAirService.save(tma, form.getFile().getInputStream());
        }
    }

    private void validateForm(KualitasAirForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }

        if (form.getYear() == null) {
            errors.reject("year.empty", "Tahun belum diisi");
        }
    }

    private ModelAndView createFormView(Long wsId, Long dasId) {
        ModelAndView mav = new ModelAndView("/admin/sda/hidrologi/kualitas_air/form");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        Das das = dasService.findById(dasId);
        mav.addObject("das", das);

        mav.addObject("yearLookup", getYearLookup());
        return mav;
    }

    private Map getYearLookup() {
        Map lookupData = new LinkedHashMap();
        List<Year> yearList = yearService.findAlls();
        for (Year year : yearList) {
            lookupData.put(year.getId(), year.getValue());
        }
        return lookupData;
    }

    @RequestMapping("{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        KualitasAir ka = kualitasAirService.findById(id);
        if (ka == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(ka.getFilename()));
        kualitasAirService.getBlob(id, response.getOutputStream());
    }
}
