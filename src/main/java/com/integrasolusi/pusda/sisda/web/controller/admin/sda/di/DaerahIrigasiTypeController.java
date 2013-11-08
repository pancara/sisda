package com.integrasolusi.pusda.sisda.web.controller.admin.sda.di;

import com.integrasolusi.pusda.sisda.persistence.sda.di.DiType;
import com.integrasolusi.pusda.sisda.service.sda.di.DiTypeService;
import com.integrasolusi.pusda.sisda.web.form.SearchForm;
import com.integrasolusi.pusda.sisda.web.form.sda.DaerahIrigasiTypeForm;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * User: pancara
 * Date: 8/6/12
 * Time: 11:39 AM
 */

@Controller("adminDaerahIrigasiTypeController")
@RequestMapping("/admin/sda/daerah_irigasi/type")
public class DaerahIrigasiTypeController {
    private DiTypeService diTypeService;
    private PagingHelper pagingHelper;

    @Autowired
    public void setDiTypeService(DiTypeService diTypeService) {
        this.diTypeService = diTypeService;
    }

    @Autowired
    public void setPagingHelper(PagingHelper pagingHelper) {
        this.pagingHelper = pagingHelper;
    }

    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") SearchForm form) {
        return list(form, 1L);
    }

    @RequestMapping(value = "page/{page}.html")
    public ModelAndView list(@ModelAttribute("form") SearchForm form,
                             @PathVariable(value = "page") Long page) {

        ModelAndView mav = new ModelAndView("admin/sda/daerah_irigasi/type/list");

        mav.addObject("current", page);
        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);


        Long count = null;
        java.util.List<Long> pages = null;
        java.util.List<DiType> typeList = null;

        if (StringUtils.isEmpty(form.getKeyword())) {
            count = diTypeService.countAlls();
            pages = pagingHelper.getDisplayedPages(page, count);
            typeList = diTypeService.findAlls(start, pagingHelper.getRowPerPage());
        } else {
            String keyword = "%" + form.getKeyword() + "%";
            count = diTypeService.countByKeyword(keyword);
            pages = pagingHelper.getDisplayedPages(page, count);
            typeList = diTypeService.findByKeyword(keyword, start, pagingHelper.getRowPerPage());
        }

        mav.addObject("count", count);
        mav.addObject("last", pagingHelper.calcPageCount(count));

        mav.addObject("pages", pages);
        mav.addObject("typeList", typeList);

        return mav;
    }


    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") DaerahIrigasiTypeForm form) {
        return createFormView();
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") DaerahIrigasiTypeForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(null, form);
            return createListView();
        }

        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") DaerahIrigasiTypeForm form) {
        DiType type = diTypeService.findById(id);
        form.setName(type.getName());
        return createFormView();
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id, @ModelAttribute("form") DaerahIrigasiTypeForm form, Errors errors) throws IOException {
        validateForm(form, errors);
        if (!errors.hasErrors()) {
            saveForm(id, form);
            return createListView();
        }
        return createFormView();
    }

    @RequestMapping(value = "manage.html", params = "remove", method = RequestMethod.POST)
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) throws IOException {
        if (ids != null) {
            diTypeService.removeByIds(ids);
        }
        return createViewRedirectToPage(page, keyword);
    }

    private void saveForm(Long id, DaerahIrigasiTypeForm form) throws IOException {
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

    private void validateForm(DaerahIrigasiTypeForm form, Errors errors) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama  belum diisi");
        }
    }

    private ModelAndView createFormView() {
        return new ModelAndView("/admin/sda/daerah_irigasi/type/form");
    }

    private ModelAndView createListView() {
        return new ModelAndView("redirect:/admin/sda/daerah_irigasi/type/list.html");
    }

    private ModelAndView createViewRedirectToPage(Long page, String keyword) {
        String sPage = page == null ? "" : String.format("%d", page);
        return new ModelAndView(String.format("redirect:/admin/sda/daerah_irigasi/type/page/%s.html?keyword=%s", sPage, keyword));
    }
}
