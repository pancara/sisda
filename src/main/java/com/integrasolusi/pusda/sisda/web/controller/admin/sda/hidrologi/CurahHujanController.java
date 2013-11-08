package com.integrasolusi.pusda.sisda.web.controller.admin.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosCH;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.hidrologi.PosCHService;
import com.integrasolusi.pusda.sisda.web.form.sda.hidrologi.PosCHForm;
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

@Controller("adminCurahHujanController")
@RequestMapping("/admin/sda/hidrologi/curah_hujan")
public class CurahHujanController {

    @Autowired
    private PosCHService posCHService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private DasService dasService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping(value = "list.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("admin/sda/hidrologi/curah_hujan/list_ws");
        mav.addObject("wsList", wilayahSungaiService.findAlls());
        return mav;
    }

    @RequestMapping(value = "list/{wsId}.html")
    public ModelAndView list(@PathVariable("wsId") Long wsId) {
        ModelAndView mav = new ModelAndView("admin/sda/hidrologi/curah_hujan/list_pos");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        mav.addObject("listPosCH", posCHService.findByWs(ws));
        return mav;
    }


    @RequestMapping(value = "{wsId}/add.html", method = RequestMethod.GET)
    public ModelAndView addPos(@PathVariable("wsId") Long wsId,
                               @ModelAttribute("form") PosCHForm form) {
        return createPosFormView(wsId);
    }

    @RequestMapping(value = "{wsId}/add.html", method = RequestMethod.POST)
    public ModelAndView addPos(@PathVariable("wsId") Long wsId,
                               @ModelAttribute("form") PosCHForm form,
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
                                @ModelAttribute("form") PosCHForm form) {
        PosCH pos = posCHService.findById(posId);
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
                                @ModelAttribute("form") PosCHForm form,
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
            posCHService.removeByIds(ids);
        }
        return createPosSuccessView(wsId);
    }

    private ModelAndView createPosSuccessView(Long wsId) {
        String path = String.format("redirect:/admin/sda/hidrologi/curah_hujan/list/%d.html", wsId);
        return new ModelAndView(path);
    }

    private ModelAndView createPosFormView(Long wsId) {
        ModelAndView mav = new ModelAndView("/admin/sda/hidrologi/curah_hujan/form_pos");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        mav.addObject("listDas", dasService.findByWilayahSungai(ws));

        return mav;
    }

    private void validatePosForm(PosCHForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

    }

    private void savePosForm(Long id, PosCHForm form) throws IOException {
        PosCH pos = (id == null ?
                new PosCH() :
                posCHService.findById(id));

        pos.setName(form.getName());
        pos.setDescription(form.getDescription());

        Das das = dasService.findById(form.getDas());
        pos.setDas(das);

        pos.setLongitude(form.getLongitude());
        pos.setLatitude(form.getLatitude());

        pos.setMapUrl(form.getMapUrl());

        if (form.getFile() == null || form.getFile().isEmpty()) {
            posCHService.save(pos);
        } else {
            pos.setFilename(form.getFile().getOriginalFilename());
            posCHService.save(pos, form.getFile().getInputStream());
        }
    }

    @RequestMapping("{id}/**")
    public void downloadPos(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        PosCH posCH = posCHService.findById(id);
        if (posCH == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(posCH.getFilename()));
        posCHService.getBlob(id, response.getOutputStream());
    }
}
