package com.integrasolusi.pusda.sisda.web.controller.admin.sda.di;

import com.integrasolusi.pusda.sisda.persistence.sda.di.DiType;
import com.integrasolusi.pusda.sisda.persistence.sda.di.DiDi;
import com.integrasolusi.pusda.sisda.persistence.sda.di.DiData;
import com.integrasolusi.pusda.sisda.service.sda.di.DiDataService;
import com.integrasolusi.pusda.sisda.service.sda.di.DiDiService;
import com.integrasolusi.pusda.sisda.service.sda.di.DiTypeService;
import com.integrasolusi.pusda.sisda.web.form.sda.di.DiDataForm;
import com.integrasolusi.pusda.sisda.web.form.sda.di.DiDiForm;
import com.integrasolusi.pusda.sisda.web.form.sda.di.TypeForm;
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

@Controller("adminDaerahIrigasiController")
@RequestMapping("/admin/sda/daerah_irigasi")
public class DaerahIrigasiController {
    @Autowired
    private DiDataService diDataService;

    @Autowired
    private DiTypeService diTypeService;

    @Autowired
    private DiDiService diDiService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;

    @RequestMapping("list.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("admin/sda/daerah_irigasi/list_types");
        mav.addObject("types", diTypeService.findAlls());
        return mav;
    }

    @RequestMapping("list/{type}.html")
    public ModelAndView list(@PathVariable("type") Long typeId) {
        ModelAndView mav = new ModelAndView("admin/sda/daerah_irigasi/list_di");
        DiType type = diTypeService.findById(typeId);
        mav.addObject("type", type);
        mav.addObject("listDi", diDiService.findByDiType(type));
        return mav;
    }

    @RequestMapping("list/{type}/{di}.html")
    public ModelAndView list(@PathVariable("type") Long typeId,
                             @PathVariable("di") Long dataTypeId) {
        ModelAndView mav = new ModelAndView("admin/sda/daerah_irigasi/list_data");
        DiType type = diTypeService.findById(typeId);
        mav.addObject("type", type);

        DiDi di = diDiService.findById(dataTypeId);
        mav.addObject("di", di);

        mav.addObject("datas", diDataService.findByDi(di));
        return mav;
    }


    @RequestMapping(value = "add/{type}/{di}.html", method = RequestMethod.GET)
    public ModelAndView addData(@PathVariable("type") Long typeId,
                                @PathVariable("di") Long diId,
                                @ModelAttribute("form") DiDataForm form) {
        return createViewDiDataForm(typeId, diId);
    }

    @RequestMapping(value = "add/{type}/{di}.html", method = RequestMethod.POST)
    public ModelAndView addData(@PathVariable("type") Long typeId,
                                @PathVariable("di") Long diId,
                                @ModelAttribute("form") DiDataForm form,
                                Errors errors) throws IOException {
        validateDataForm(form, errors);
        if (!errors.hasErrors()) {
            saveDataForm(null, form, diId);
            return createDataRedirectView(typeId, diId);
        }

        return createViewDiDataForm(typeId, diId);
    }

    @RequestMapping(value = "edit/{type}/{di}/{data}.html", method = RequestMethod.GET)
    public ModelAndView editData(@PathVariable("type") Long typeId,
                                 @PathVariable("di") Long diId,
                                 @PathVariable("data") Long dataId,
                                 @ModelAttribute("form") DiDataForm form) {
        DiData diData = diDataService.findById(dataId);
        form.setName(diData.getName());
        form.setDescription(diData.getDescription());
        return createViewDiDataForm(typeId, diId);
    }

    @RequestMapping(value = "edit/{type}/{di}/{data}.html", method = RequestMethod.POST)
    public ModelAndView editData(@PathVariable("type") Long typeId,
                                 @PathVariable("di") Long diId,
                                 @PathVariable("data") Long dataId,
                                 @ModelAttribute("form") DiDataForm form,
                                 Errors errors) throws IOException {
        validateDataForm(form, errors);
        if (!errors.hasErrors()) {
            saveDataForm(dataId, form, diId);
            return createDataRedirectView(typeId, diId);
        }
        return createViewDiDataForm(typeId, diId);
    }

    @RequestMapping(value = "remove/{type}/{di}.html", method = RequestMethod.POST)
    public ModelAndView removeData(@PathVariable("type") Long typeId,
                                   @PathVariable("di") Long diId,
                                   @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            diDataService.removeByIds(ids);
        }
        return createDataRedirectView(typeId, diId);
    }

    private ModelAndView createDataRedirectView(Long typeId, Long diId) {
        return new ModelAndView(String.format("redirect:/admin/sda/daerah_irigasi/list/%d/%d.html", typeId, diId));
    }


    private void saveDataForm(Long id, DiDataForm form, Long diId) throws IOException {
        DiData data = (id == null ?
                new DiData() :
                diDataService.findById(id));

        data.setName(form.getName());
        data.setDescription(form.getDescription());
        data.setDi(diDiService.findById(diId));

        if (form.getFile() == null || form.getFile().isEmpty()) {
            diDataService.save(data);
        } else {
            data.setFilename(form.getFile().getOriginalFilename());
            diDataService.save(data, form.getFile().getInputStream());
        }
    }

    private void validateDataForm(DiDataForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama  belum diisi");
        }

        if (StringUtils.isEmpty(form.getDescription())) {
            errors.reject("description.empty", "Keterangan singkat belum diisi");
        }
    }

    private ModelAndView createViewDiDataForm(Long typeId, Long diId) {
        ModelAndView mav = new ModelAndView("/admin/sda/daerah_irigasi/form_data");
        mav.addObject("type", diTypeService.findById(typeId));
        mav.addObject("di", diDiService.findById(diId));
        return mav;
    }


    @RequestMapping(value = "edit/{type}.html", method = RequestMethod.GET)
    public ModelAndView editType(@PathVariable("type") Long typeId,
                                 @ModelAttribute("form") TypeForm form) {
        DiType type = diTypeService.findById(typeId);
        form.setName(type.getName());
        return createFormTypeView();
    }

    @RequestMapping(value = "edit/{type}.html", method = RequestMethod.POST)
    public ModelAndView editType(@PathVariable("type") Long typeId,
                                 @ModelAttribute("form") TypeForm form,
                                 Errors errors) throws IOException {
        validateTypeForm(form, errors);
        if (!errors.hasErrors()) {
            saveTypeForm(typeId, form);
            return createTypeRedirectView();
        }
        return createFormTypeView();
    }

    private ModelAndView createFormTypeView() {
        return new ModelAndView("/admin/sda/daerah_irigasi/form_type");
    }

    private ModelAndView createTypeRedirectView() {
        return new ModelAndView("redirect:/admin/sda/daerah_irigasi/list.html");
    }

    private void validateTypeForm(TypeForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama  belum diisi");
        }
    }

    private void saveTypeForm(Long id, TypeForm form) throws IOException {
        DiType type = (id == null ?
                new DiType() :
                diTypeService.findById(id));

        type.setName(form.getName());

        if (form.getFile() == null || form.getFile().isEmpty()) {
            diTypeService.save(type);
        } else {
            type.setFilename(form.getFile().getOriginalFilename());
            diTypeService.save(type, form.getFile().getInputStream());
        }
    }

    @RequestMapping(value = "add/{type}.html", method = RequestMethod.GET)
    public ModelAndView addDi(@PathVariable("type") Long typeId,
                              @ModelAttribute("form") DiDiForm form) {
        return createFormDiDiView(typeId);
    }

    @RequestMapping(value = "add/{type}.html", method = RequestMethod.POST)
    public ModelAndView addDi(@PathVariable("type") Long typeId,
                              @ModelAttribute("form") DiDiForm form,
                              Errors errors) throws IOException {
        validateDiForm(form, errors);
        if (!errors.hasErrors()) {
            saveDiForm(null, form, typeId);
            return createViewRedirectDi(typeId);
        }
        return createFormDiDiView(typeId);
    }


    @RequestMapping(value = "edit/{type}/{di}.html", method = RequestMethod.GET)
    public ModelAndView editDi(@PathVariable("type") Long typeId,
                               @PathVariable("di") Long diId,
                               @ModelAttribute("form") DiDiForm form) {
        DiDi dataType = diDiService.findById(diId);
        form.setName(dataType.getName());
        return createFormDiDiView(typeId);
    }

    @RequestMapping(value = "edit/{type}/{di}.html", method = RequestMethod.POST)
    public ModelAndView editDi(@PathVariable("type") Long typeId,
                               @PathVariable("di") Long diId,
                               @ModelAttribute("form") DiDiForm form,
                               Errors errors) throws IOException {
        validateDiForm(form, errors);
        if (!errors.hasErrors()) {
            saveDiForm(diId, form, typeId);
            return createViewRedirectDi(typeId);
        }
        return createFormDiDiView(typeId);
    }

    @RequestMapping(value = "remove/{type}.html", method = RequestMethod.POST)
    public ModelAndView removeDi(@PathVariable("type") Long typeId,
                                 @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            diDiService.removeByIds(ids);
        }
        return createViewRedirectDi(typeId);
    }

    private ModelAndView createFormDiDiView(Long typeId) {
        ModelAndView mav = new ModelAndView("/admin/sda/daerah_irigasi/form_di");
        mav.addObject("type", diTypeService.findById(typeId));
        return mav;
    }

    private void validateDiForm(DiDiForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama  belum diisi");
        }
    }

    private void saveDiForm(Long diId, DiDiForm form, Long typeId) throws IOException {
        DiDi di = (diId == null) ?
                new DiDi() : diDiService.findById(diId);

        di.setName(form.getName());
        di.setType(diTypeService.findById(typeId));

        if (form.getFile() == null || form.getFile().isEmpty()) {
            diDiService.save(di);
        } else {
            di.setFilename(form.getFile().getOriginalFilename());
            diDiService.save(di, form.getFile().getInputStream());
        }
    }

    private ModelAndView createViewRedirectDi(Long typeId) {
        return new ModelAndView(String.format("redirect:/admin/sda/daerah_irigasi/list/%d.html", typeId));
    }

    @RequestMapping("data/{type}/{di}/{data}/**")
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
