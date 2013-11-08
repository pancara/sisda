package com.integrasolusi.pusda.sisda.web.controller.admin;

import com.integrasolusi.pusda.sisda.persistence.User;
import com.integrasolusi.pusda.sisda.service.UserService;
import com.integrasolusi.pusda.sisda.web.form.LoginForm;
import com.integrasolusi.pusda.sisda.web.utils.SecurityUtils;
import com.integrasolusi.utils.ImageUtils;
import com.integrasolusi.utils.ServletSessionHelper;
import com.integrasolusi.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Programmer   : pancara
 * Date         : 7/24/11
 * Time         : 8:32 PM
 */
@Controller
public class LoginController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    public static final String LOGIN_CAPTCHA = "captcha_login_key";
    @Autowired
    private UserService userService;

    @Autowired
    private ImageUtils imageUtils;

    @Autowired
    private SecurityUtils securityUtils;


    @RequestMapping(value = "/admin.html", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("redirect:/login.html");
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request,
                              @ModelAttribute("form") LoginForm form) {

        User currentUser = (User) ServletSessionHelper.getCurrentUser(request);
        if (currentUser != null) {
            return new ModelAndView("redirect:/admin/comment/guest.html");
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login.html", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request,
                              @ModelAttribute("form") LoginForm form,
                              Errors errors) {
        User currentUser = (User) ServletSessionHelper.getCurrentUser(request);
        if (currentUser != null) {
            return login(request, form);
        }

        validateLoginForm(request, form, errors);
        if (errors.hasErrors()) {
            return new ModelAndView("login");
        }

        if (userService.authenticate(form.getUserId(), form.getPassword())) {
            User user = userService.findUserByUserId(form.getUserId());
            ServletSessionHelper.setCurrentUser(request, user);
            String targetURI = securityUtils.getAndClearTargetUrl(request);
            if (targetURI != null) {
                String path = targetURI.substring(request.getContextPath().length());
                return new ModelAndView("redirect:" + path);
            } else {
                return new ModelAndView("redirect:/admin/comment/guest.html");
            }
        } else {
            errors.reject("userId.password.not.valid", "User ID atau password tidak valid");
            return new ModelAndView("login");
        }
    }

    private void validateLoginForm(HttpServletRequest request, LoginForm form, Errors errors) {
        String sessionCaptcha = (String) ServletSessionHelper.getAndClearSessionAttribute(request, LOGIN_CAPTCHA);
        if (StringUtils.isEmpty(sessionCaptcha) || !StringUtils.equalsIgnoreCase(sessionCaptcha, form.getCaptcha())) {
            errors.reject("captcha.not.valid", "Captcha tidak valid");
            return;
        }

        if (StringUtils.isEmpty(form.getUserId())) {
            errors.reject("userId.empty", "User Id belum diisi");
        }

        if (StringUtils.isEmpty(form.getPassword())) {
            errors.reject("password.empty", "Password belum diisi");
        }
    }

    @RequestMapping(value = "/login/captcha.html")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        String captchaText = StringHelper.generateRandomText(4);
        ServletSessionHelper.setSessionAttribute(request, LOGIN_CAPTCHA, captchaText);
        try {
            imageUtils.createCaptchaImage(captchaText, response.getOutputStream());
        } catch (Exception e) {
            logger.error(e);
        }

    }


    @RequestMapping(value = "/logout.html")
    public ModelAndView logout(HttpServletRequest request) {
        ServletSessionHelper.invalidateSession(request);
        return new ModelAndView("redirect:/login.html");
    }

}
