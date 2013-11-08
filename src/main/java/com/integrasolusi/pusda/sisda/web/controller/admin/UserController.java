package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.User;
import com.integrasolusi.pusda.sisda.service.UserService;
import com.integrasolusi.pusda.sisda.web.form.SearchUserForm;
import com.integrasolusi.pusda.sisda.web.form.UserForm;
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
 * Date         : 7/29/11
 * Time         : 8:23 AM
 */
@Controller
@RequestMapping("/admin/user")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);
    private UserService userService;
    private PagingHelper pagingHelper;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPagingHelper(PagingHelper pagingHelper) {
        this.pagingHelper = pagingHelper;
    }

    @RequestMapping("list.html")
    public ModelAndView list(@ModelAttribute("form") SearchUserForm form) {
        return list(1L, form);
    }

    @RequestMapping("page/{page}.html")
    public ModelAndView list(@PathVariable("page") Long page,
                             @ModelAttribute("form") SearchUserForm form) {
        ModelAndView mav = new ModelAndView("admin/user/list");
        mav.addObject("current", page);

        Long start = pagingHelper.getStartRow(page);
        mav.addObject("start", start);

        Long count = getCount(form.getKeyword());
        mav.addObject("count", count);

        mav.addObject("last", pagingHelper.calcPageCount(count));
        mav.addObject("pages", pagingHelper.getDisplayedPages(page, count));
        mav.addObject("userList", getUserList(form.getKeyword(), start, pagingHelper.getRowPerPage()));
        return mav;
    }

    private List<User> getUserList(String keyword, Long start, Long count) {
        if (StringUtils.isEmpty(keyword))
            return userService.findAlls(start, count);
        else
            return userService.findByKeyword(createSearchKeyword(keyword), start, count);
    }

    private String createSearchKeyword(String keyword) {
        return String.format("%%%s%%", keyword);
    }

    private Long getCount(String keyword) {
        if (StringUtils.isEmpty(keyword))
            return userService.countAlls();
        else
            return userService.countByKeyword(createSearchKeyword(keyword));
    }

    @RequestMapping(value = "add.html", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("form") UserForm form,
                            @RequestParam(value = "page", required = false) Long page,
                            @RequestParam(value = "keyword", required = false) String keyword) throws IOException {
        return createFormView(page, keyword);
    }

    @RequestMapping(value = "add.html", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute("form") UserForm form,
                            Errors errors,
                            @RequestParam(value = "page", required = false) Long page,
                            @RequestParam(value = "keyword", required = false) String keyword) throws IOException {
        validateUser(form, errors, false);
        if (errors.hasErrors()) {
            return createFormView(page, keyword);
        }

        try {
            saveUserForm(null, form);
            return createRedirectView(page, keyword);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            errors.reject("saving.failed", "Penyimpanan data gagal.");
            return createFormView(page, keyword);
        }
    }

    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Long id,
                             @ModelAttribute("form") UserForm form,
                             @RequestParam(value = "page", required = false) Long page,
                             @RequestParam(value = "keyword", required = false) String keyword) throws IOException {
        User user = userService.findById(id);
        if (user == null) {
            return createRedirectView(page, keyword);
        }
        form.setUserId(user.getUserId());
        form.setName(user.getName());
        form.setPassword(user.getPassword());

        ModelAndView mav = createFormView(page, keyword);
        mav.addObject("user", userService.findById(id));
        return mav;
    }


    @RequestMapping(value = "edit/{id}.html", method = RequestMethod.POST)
    public ModelAndView edit(@PathVariable("id") Long id,
                             @ModelAttribute("form") UserForm form,
                             Errors errors,
                             @RequestParam(value = "page", required = false) Long page,
                             @RequestParam(value = "keyword", required = false) String keyword) throws IOException {
        validateUser(form, errors, true);
        if (errors.hasErrors()) {
            ModelAndView mav = createFormView(page, keyword);
            mav.addObject("user", userService.findById(id));
            return mav;
        }

        try {
            saveUserForm(id, form);
            return createRedirectView(page, keyword);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            errors.rejectValue("saving.failed", "Penyimpanan data gagal.");
            return createFormView(page, keyword);
        }
    }

    private ModelAndView createFormView(Long page, String keyword) {
        ModelAndView mav = new ModelAndView("admin/user/form");
        mav.addObject("page", page);
        mav.addObject("keyword", keyword);
        return mav;
    }

    private ModelAndView createRedirectView(Long page, String keyword) {
        return new ModelAndView(String.format("redirect:/admin/user/page/%d.html?keyword=%s", page, keyword));
    }

    private void saveUserForm(Long id, UserForm form) {
        User user = id == null ?
                new User() :
                userService.findById(id);

        if (user != null) {
            try {
                user.setUserId(form.getUserId());
                user.setName(form.getName());
                if (!StringUtils.isEmpty(form.getPassword())) {
                    user.setPassword(form.getPassword());
                }

                userService.save(user);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e.getMessage(), e);
                throw new RuntimeException("save failed");
            }
        }
    }


    private void validateUser(UserForm form, Errors errors, boolean editting) {
        if (StringUtils.isEmpty(form.getName())) {
            errors.reject("name.empty", "Nama belum diisi");
        }
        if (StringUtils.isEmpty(form.getUserId())) {
            errors.reject("userId.empty", "User ID belum diisi");
        }

        if (StringUtils.isEmpty(form.getPassword())) {
            if (!editting) {
                errors.reject("password.empty", "Password belum diisi");
            }
        }
    }

    @RequestMapping(value = "manage.html")
    public ModelAndView manage(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {
        return createRedirectView(page, keyword);
    }

    @RequestMapping(value = "manage.html", params = "remove")
    public ModelAndView remove(@RequestParam(value = "page", required = false) Long page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            try {
                userService.removeByIds(ids);
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        }
        return createRedirectView(page, keyword);

    }


    @RequestMapping(value = "manage.html", params = "activate")
    public ModelAndView activate(@RequestParam(value = "page", required = false) Long page,
                                 @RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            try {
                userService.activateUser(ids);
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        }
        return createRedirectView(page, keyword);

    }

    @RequestMapping(value = "manage.html", params = "deactivate")
    public ModelAndView deactivate(@RequestParam(value = "page", required = false) Long page,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "ids", required = false) Long[] ids) {

        if (ids != null) {
            try {
                userService.deactivateUser(ids);
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
            }
        }
        return createRedirectView(page, keyword);

    }

}
