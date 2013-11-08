package com.integrasolusi.pusda.sisda.web.controller.admin.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosKlimatologi;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.hidrologi.PosKlimatologiService;
import com.integrasolusi.pusda.sisda.web.form.sda.hidrologi.PosKlimatologiForm;
import com.integrasolusi.utils.ContentTypeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: pancara
 * Date: 8/6/12
 * Time: 11:39 AM
 */

@Controller("adminKlimatologiController")
@RequestMapping("/admin/sda/hidrologi/klimatologi")
public class KlimatologiController {

    @Autowired
    private PosKlimatologiService posKlimatologiService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private DasService dasService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping(value = "list.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("admin/sda/hidrologi/klimatologi/list_ws");
        mav.addObject("wsList", wilayahSungaiService.findAlls());
        return mav;
    }

    @RequestMapping(value = "list/{wsId}.html")
    public ModelAndView list(@PathVariable("wsId") Long wsId) {
        ModelAndView mav = new ModelAndView("admin/sda/hidrologi/klimatologi/list_pos");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        mav.addObject("listPosKlimatologi", posKlimatologiService.findByWs(ws));
        return mav;
    }


    @RequestMapping(value = "{wsId}/add.html", method = RequestMethod.GET)
    public ModelAndView addPos(@PathVariable("wsId") Long wsId,
                               @ModelAttribute("form") PosKlimatologiForm form) {
        return createPosFormView(wsId);
    }

    @RequestMapping(value = "{wsId}/add.html", method = RequestMethod.POST)
    public ModelAndView addPos(@PathVariable("wsId") Long wsId,
                               @ModelAttribute("form") PosKlimatologiForm form,
                               Errors errors) throws IOException {
        validatePosForm(form, errors);
        if (!errors.hasErrors()) {
            savePosForm(null, form);
            return createPosSuccessView(wsId);
        }

        return createPosFormView(wsId);
    }

    @RequestMapping(value = "{wsId}/edit/{posId}.html", method = RequestMethod.GET)
    public ModelAndView editPos(@PathVariable("wsId") Long wsId,
                                @PathVariable("posId") Long posId,
                                @ModelAttribute("form") PosKlimatologiForm form) {
        PosKlimatologi pos = posKlimatologiService.findById(posId);
        form.setName(pos.getName());
        form.setDescription(pos.getDescription());

        form.setLongitude(pos.getLongitude());
        form.setLatitude(pos.getLatitude());

        form.setMapUrl(pos.getMapUrl());
        if (pos.getDas() != null) {
            form.setDas(pos.getDas().getId());
        }

        return createPosFormView(wsId);
    }

    @RequestMapping(value = "{wsId}/edit/{posId}.html", method = RequestMethod.POST)
    public ModelAndView editPos(@PathVariable("wsId") Long wsId,
                                @PathVariable("posId") Long posId,
                                @ModelAttribute("form") PosKlimatologiForm form,
                                Errors errors) throws IOException {
        validatePosForm(form, errors);
        if (!errors.hasErrors()) {
            savePosForm(posId, form);
            return createPosSuccessView(wsId);
        }

        return createPosFormView(wsId);
    }

    @RequestMapping(value = "{wsId}/remove.html", method = RequestMethod.POST)
    public ModelAndView removePos(@PathVariable("wsId") Long wsId,
                                  @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            posKlimatologiService.removeByIds(ids);
        }
        return createPosSuccessView(wsId);
    }

    private ModelAndView createPosSuccessView(Long wsId) {
        String path = String.format("redirect:/admin/sda/hidrologi/klimatologi/list/%d.html", wsId);
        return new ModelAndView(path);
    }

    private ModelAndView createPosFormView(Long wsId) {
        ModelAndView mav = new ModelAndView("/admin/sda/hidrologi/klimatologi/form_pos");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        mav.addObject("listDas", dasService.findByWilayahSungai(ws));

        return mav;
    }

    private void validatePosForm(PosKlimatologiForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

    }

    private void savePosForm(Long id, PosKlimatologiForm form) throws IOException {
        PosKlimatologi pos = (id == null ?
                new PosKlimatologi() :
                posKlimatologiService.findById(id));

        pos.setName(form.getName());
        pos.setDescription(form.getDescription());

        Das das = dasService.findById(form.getDas());
        pos.setDas(das);

        pos.setLongitude(form.getLongitude());
        pos.setLatitude(form.getLatitude());

        pos.setMapUrl(form.getMapUrl());

        if (form.getFile() == null || form.getFile().isEmpty()) {
            posKlimatologiService.save(pos);
        } else {
            pos.setFilename(form.getFile().getOriginalFilename());
            posKlimatologiService.save(pos, form.getFile().getInputStream());
        }
    }

    @RequestMapping("{id}/**")
    public void downloadPos(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        PosKlimatologi posCH = posKlimatologiService.findById(id);
        if (posCH == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(posCH.getFilename()));
        posKlimatologiService.getBlob(id, response.getOutputStream());
    }
}
