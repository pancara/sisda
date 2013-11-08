package com.integrasolusi.pusda.sisda.web.controller.admin.presentation;

import com.integrasolusi.pusda.sisda.persistence.presentation.Meeting;
import com.integrasolusi.pusda.sisda.persistence.presentation.Module;
import com.integrasolusi.pusda.sisda.service.presetantion.MeetingService;
import com.integrasolusi.pusda.sisda.service.presetantion.ModuleService;
import com.integrasolusi.pusda.sisda.web.form.presentation.ModuleForm;
import com.integrasolusi.utils.PagingHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 9:03 AM
 */

@Controller("adminModuleController")
@RequestMapping("/admin/presentation/{meetingId}/module")
public class ModuleController {
    private static Logger logger = Logger.getLogger(ModuleController.class);

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private ModuleService moduleService;


    @Autowired
    private PagingHelper pagingHelper;


    @RequestMapping("list.html")
    public ModelAndView list(@PathVariable("meetingId") Long meetingId) {
        return list(meetingId, 1L);
    }

    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("meetingId") Long meetingId,
                             @PathVariable("page") Long page) {

        ModelAndView mav = new ModelAndView("admin/presentation/module/list");

        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);

        Meeting meeting = meetingService.findById(meetingId);
        mav.addObject("meeting", meeting);

        Long count = moduleService.countByMeeting(meeting);

        mav.addObject("count", count);
        mav.addObject("last", pagingHelper.calcPageCount(count));

        List<Long> pages = pagingHelper.getDisplayedPages(page, count);
        mav.addObject("pages", pages);

        List<Module> moduleList = moduleService.findByMeeting(meeting, start, count);
        mav.addObject("moduleList", moduleList);

        return mav;
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@PathVariable("meetingId") Long meetingId, @ModelAttribute("form") ModuleForm form) {
        return createFormView(meetingId);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@PathVariable("meetingId") Long meetingId,
                            @ModelAttribute("form") ModuleForm form,
                            Errors errors) throws IOException {
        validateForm(form, errors, false);

        if (!errors.hasErrors()) {
            Module module = new Module();
            module.setName(form.getName());
            module.setDescription(form.getDescription());

            Meeting meeting = meetingService.findById(meetingId);
            module.setMeeting(meeting);

            module.setFilename(form.getFile().getOriginalFilename());

            moduleService.save(module, form.getFile().getInputStream());
            String redirectView = String.format("redirect:/admin/presentation/%d/module/list.html", meetingId);
            return new ModelAndView(redirectView);
        }

        return createFormView(meetingId);
    }

    private ModelAndView createFormView(Long meetingId) {
        ModelAndView mav = new ModelAndView("admin/presentation/module/form");
        mav.addObject("meeting", meetingService.findById(meetingId));
        return mav;
    }

    @RequestMapping(value = "edit/{moduleId}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("meetingId") Long meetingId,
                             @PathVariable("moduleId") Long moduleId,
                             @ModelAttribute("form") ModuleForm form) {
        ModelAndView mav = createFormView(meetingId);

        Module module = moduleService.findById(moduleId);
        form.setName(module.getName());
        form.setDescription(module.getDescription());

        return mav;
    }

    @RequestMapping(value = "edit/{moduleId}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("meetingId") Long meetingId,
                             @PathVariable("moduleId") Long moduleId,
                             @ModelAttribute("form") ModuleForm form,
                             Errors errors) throws IOException {
        validateForm(form, errors, true);

        if (!errors.hasErrors()) {
            Module module = moduleService.findById(moduleId);
            module.setName(form.getName());
            module.setDescription(form.getDescription());

            if (form.getFile() != null && !form.getFile().isEmpty()) {
                module.setFilename(form.getFile().getOriginalFilename());
                moduleService.save(module, form.getFile().getInputStream());
            } else {
                moduleService.save(module);
            }

            String redirectView = String.format("redirect:/admin/presentation/%d/module/list.html", meetingId);
            return new ModelAndView(redirectView);
        }
        return createFormView(meetingId);
    }

    private void validateForm(ModuleForm form, Errors errors, boolean allowEmptyFile) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }

        if (!allowEmptyFile) {
            if (form.getFile() == null || form.getFile().isEmpty()) {
                errors.reject("file.empty", "File belum diisi");
            }
        }

    }

    @RequestMapping(value = "remove.html")
    public ModelAndView remove(@PathVariable("meetingId") Long meetingId,
                               @RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            moduleService.removeByIds(ids);
        }

        return createViewRedirectToPage(meetingId, page);

    }

    private ModelAndView createViewRedirectToPage(Long meetingId, Long page) {
        page = page == null ? 1L : page;
        return new ModelAndView(String.format("redirect:/admin/presentation/%d/module/page/%d.html", meetingId, page));
    }
}
