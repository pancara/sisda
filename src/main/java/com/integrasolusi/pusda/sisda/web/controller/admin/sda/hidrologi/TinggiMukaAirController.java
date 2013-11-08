package com.integrasolusi.pusda.sisda.web.controller.admin.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosTMA;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.TinggiMukaAir;
import com.integrasolusi.pusda.sisda.service.sda.DasService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.pusda.sisda.service.sda.hidrologi.PosTMAService;
import com.integrasolusi.pusda.sisda.service.sda.hidrologi.TinggiMukaAirService;
import com.integrasolusi.pusda.sisda.web.form.sda.hidrologi.PosTMAForm;
import com.integrasolusi.pusda.sisda.web.form.sda.hidrologi.TinggiMukaAirForm;
import com.integrasolusi.utils.ContentTypeUtils;
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

@Controller("adminTinggiMukaAirController")
@RequestMapping("/admin/sda/hidrologi/tinggi_muka_air")
public class TinggiMukaAirController {

    @Autowired
    private PosTMAService posTMAService;

    @Autowired
    private TinggiMukaAirService tinggiMukaAirService;

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
        ModelAndView mav = new ModelAndView("admin/sda/hidrologi/tinggi_muka_air/list_ws");

        mav.addObject("listWs", wilayahSungaiService.findAlls());
        return mav;
    }

    @RequestMapping(value = "list/{wsId}.html")
    public ModelAndView list(@PathVariable("wsId") Long wsId) {
        ModelAndView mav = new ModelAndView("admin/sda/hidrologi/tinggi_muka_air/list_das");

        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        mav.addObject("listDas", dasService.findByWilayahSungai(ws));
        return mav;
    }


    @RequestMapping(value = "list/{wsId}/{dasId}.html")
    public ModelAndView list(@PathVariable("wsId") Long wsId,
                             @PathVariable("dasId") Long dasId) {
        ModelAndView mav = new ModelAndView("admin/sda/hidrologi/tinggi_muka_air/list_pos");

        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        Das das = dasService.findById(dasId);
        mav.addObject("das", das);

        mav.addObject("listPos", posTMAService.findByDas(das));
        return mav;
    }

    @RequestMapping(value = "list/{wsId}/{dasId}/{posId}.html")
    public ModelAndView list(@PathVariable("wsId") Long wsId,
                             @PathVariable("dasId") Long dasId,
                             @PathVariable("posId") Long posId) {
        ModelAndView mav = new ModelAndView("admin/sda/hidrologi/tinggi_muka_air/list_tma");

        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        Das das = dasService.findById(dasId);
        mav.addObject("das", das);

        PosTMA pos = posTMAService.findById(posId);
        mav.addObject("pos", pos);

        mav.addObject("listTMA", tinggiMukaAirService.findByPos(pos));

        return mav;
    }

    @RequestMapping(value = "{wsId}/{dasId}/add.html", method = RequestMethod.GET)
    public ModelAndView addPos(@PathVariable("wsId") Long wsId,
                               @PathVariable("dasId") Long dasId,
                               @ModelAttribute("form") PosTMAForm form) {
        return createPosFormView(wsId, dasId);
    }

    private ModelAndView createPosFormView(Long wsId, Long dasId) {
        ModelAndView mav = new ModelAndView("/admin/sda/hidrologi/tinggi_muka_air/form_pos");
        WilayahSungai ws = wilayahSungaiService.findById(wsId);
        mav.addObject("ws", ws);

        Das das = dasService.findById(dasId);
        mav.addObject("das", das);

        return mav;
    }

    @RequestMapping(value = "{wsId}/{dasId}/add.html", method = RequestMethod.POST)
    public ModelAndView addPos(@PathVariable("wsId") Long wsId,
                               @PathVariable("dasId") Long dasId,
                               @ModelAttribute("form") PosTMAForm form,
                               Errors errors) throws IOException {
        validatePosForm(form, errors);
        if (!errors.hasErrors()) {
            savePosForm(null, form, dasId);
            return createPosSuccessView(wsId, dasId);
        }

        return createPosFormView(wsId, dasId);
    }


    private void validatePosForm(PosTMAForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

    }

    private void savePosForm(Long id, PosTMAForm form, Long dasId) throws IOException {
        PosTMA pos = (id == null ?
                new PosTMA() :
                posTMAService.findById(id));

        pos.setName(form.getName());
        pos.setDescription(form.getDescription());

        Das das = dasService.findById(dasId);
        pos.setDas(das);

        pos.setLongitude(form.getLongitude());
        pos.setLatitude(form.getLatitude());

        if (form.getFile() == null || form.getFile().isEmpty()) {
            posTMAService.save(pos);
        } else {
            pos.setFilename(form.getFile().getOriginalFilename());
            posTMAService.save(pos, form.getFile().getInputStream());
        }
    }

    @RequestMapping(value = "{wsId}/{dasId}/edit/{posId}.html", method = RequestMethod.GET)
    public ModelAndView editPos(@PathVariable("wsId") Long wsId,
                                @PathVariable("dasId") Long dasId,
                                @PathVariable("posId") Long posId,
                                @ModelAttribute("form") PosTMAForm form) {
        PosTMA pos = posTMAService.findById(posId);
        form.setName(pos.getName());
        form.setDescription(pos.getDescription());
        form.setLongitude(pos.getLongitude());
        form.setLatitude(pos.getLatitude());

        return createPosFormView(wsId, dasId);
    }

    @RequestMapping(value = "{wsId}/{dasId}/edit/{posId}.html", method = RequestMethod.POST)
    public ModelAndView editPos(@PathVariable("wsId") Long wsId,
                                @PathVariable("dasId") Long dasId,
                                @PathVariable("posId") Long posId,
                                @ModelAttribute("form") PosTMAForm form,
                                Errors errors) throws IOException {
        validatePosForm(form, errors);
        if (!errors.hasErrors()) {
            savePosForm(posId, form, dasId);
            return createPosSuccessView(wsId, dasId);
        }

        return createPosFormView(wsId, dasId);
    }

    private ModelAndView createPosSuccessView(Long wsId, Long dasId) {
        String path = String.format("redirect:/admin/sda/hidrologi/tinggi_muka_air/list/%d/%d.html", wsId, dasId);
        return new ModelAndView(path);
    }

    
    @RequestMapping(value = "{wsId}/{dasId}/remove.html", method = RequestMethod.POST)
    public ModelAndView removePos(@PathVariable("wsId") Long wsId,
                                  @PathVariable("dasId") Long dasId,
                                  @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            posTMAService.removeByIds(ids);
        }
        return createPosSuccessView(wsId, dasId);
    }

    @RequestMapping(value = "{wsId}/{dasId}//{posId}/add.html", method = RequestMethod.GET)
    public ModelAndView addTMA(@PathVariable("wsId") Long wsId,
                               @PathVariable("dasId") Long dasId,
                               @PathVariable("posId") Long posId,
                               @ModelAttribute("form") TinggiMukaAirForm form) {
        return createTMAFormView(wsId, dasId, posId);
    }

    @RequestMapping(value = "{wsId}/{dasId}/{posId}/add.html", method = RequestMethod.POST)
    public ModelAndView addTMA(@PathVariable("wsId") Long wsId,
                               @PathVariable("dasId") Long dasId,
                               @PathVariable("posId") Long posId,
                               @ModelAttribute("form") TinggiMukaAirForm form,
                               Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveTMAForm(null, form, posId);
            return createTMASuccessView(wsId, dasId, posId);
        }

        return createTMAFormView(wsId, dasId, posId);
    }

    private ModelAndView createTMASuccessView(Long wsId, Long dasId, Long posId) {
        String path = String.format("redirect:/admin/sda/hidrologi/tinggi_muka_air/list/%d/%d/%d.html", wsId, dasId, posId);
        return new ModelAndView(path);
    }

    @RequestMapping(value = "{wsId}/{dasId}/{posId}/edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView editTMA(@PathVariable("wsId") Long wsId,
                                @PathVariable("dasId") Long dasId,
                                @PathVariable("posId") Long posId,
                                @PathVariable("id") Long id,
                                @ModelAttribute("form") TinggiMukaAirForm form) {
        TinggiMukaAir tma = tinggiMukaAirService.findById(id);
        form.setDescription(tma.getDescription());
        if (tma.getYear() != null) {
            form.setYear(tma.getYear().getId());
        }

        return createTMAFormView(wsId, dasId, posId);
    }

    @RequestMapping(value = "{wsId}/{dasId}/{posId}/edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView editTMA(@PathVariable("wsId") Long wsId,
                                @PathVariable("dasId") Long dasId,
                                @PathVariable("posId") Long posId,
                                @PathVariable("id") Long id,
                                @ModelAttribute("form") TinggiMukaAirForm form,
                                Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveTMAForm(id, form, posId);
            return createTMASuccessView(wsId, dasId, posId);
        }
        return createTMAFormView(wsId, dasId, posId);
    }

    @RequestMapping(value = "{wsId}/{dasId}/{posId}/remove.html", method = RequestMethod.POST)
    public ModelAndView removeTMA(@PathVariable("wsId") Long wsId,
                                  @PathVariable("dasId") Long dasId,
                                  @PathVariable("posId") Long posId,
                                  @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            tinggiMukaAirService.removeByIds(ids);
        }
        return createTMASuccessView(wsId, dasId, posId);
    }

    private void saveTMAForm(Long id, TinggiMukaAirForm form, Long posId) throws IOException {
        TinggiMukaAir tma = (id == null ?
                new TinggiMukaAir() :
                tinggiMukaAirService.findById(id));

        tma.setDescription(form.getDescription());

        Year year = yearService.findById(form.getYear());
        tma.setYear(year);

        PosTMA pos = posTMAService.findById(posId);
        tma.setPos(pos);


        if (form.getFile() == null || form.getFile().isEmpty()) {
            tinggiMukaAirService.save(tma);
        } else {
            tma.setFilename(form.getFile().getOriginalFilename());
            tinggiMukaAirService.save(tma, form.getFile().getInputStream());
        }
    }

    private void validateForm(TinggiMukaAirForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan belum diisi");
        }

        if (form.getYear() == null) {
            errors.reject("year.empty", "Tahun belum diisi");
        }
    }

    private ModelAndView createTMAFormView(Long wsId, Long dasId, Long posId) {
        ModelAndView mav = new ModelAndView("/admin/sda/hidrologi/tinggi_muka_air/form_data");

        mav.addObject("ws", wilayahSungaiService.findById(wsId));
        mav.addObject("das", dasService.findById(dasId));
        mav.addObject("pos", posTMAService.findById(posId));

        mav.addObject("yearLookup", getYearLookup());
        return mav;
    }

    private Map getYearLookup() {
        Map<Long, Integer> lookupData = new LinkedHashMap<>();
        List<Year> yearList = yearService.findAlls();
        for (Year year : yearList) {
            lookupData.put(year.getId(), year.getValue());
        }
        return lookupData;
    }

    @RequestMapping("TMA/{id}/**")
    public void downloadTMA(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        TinggiMukaAir tma = tinggiMukaAirService.findById(id);
        if (tma == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(tma.getFilename()));
        tinggiMukaAirService.getBlob(id, response.getOutputStream());
    }

    @RequestMapping("pos/{id}/**")
    public void downloadPos(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        PosTMA pos = posTMAService.findById(id);
        if (pos == null)
            return;

        response.setContentType(contentTypeUtils.getContentType(pos.getFilename()));
        posTMAService.getBlob(id, response.getOutputStream());
    }
}
