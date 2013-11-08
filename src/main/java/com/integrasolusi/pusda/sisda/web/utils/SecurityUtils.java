package com.integrasolusi.pusda.sisda.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Programmer : pancara
 * Date       : 10/31/12
 * Time       : 2:42 PM
 */
public class SecurityUtils {
    private static final String ADMINISTRATOR_TARGET_URL = "administrator.target.url";

    public void storeTargetUrl(HttpServletRequest request) {
        request.getSession().setAttribute(ADMINISTRATOR_TARGET_URL, request.getRequestURI());
    }


    public String getAndClearTargetUrl(HttpServletRequest request) {
        String targetUrl = (String) request.getSession().getAttribute(ADMINISTRATOR_TARGET_URL);
        request.getSession().removeAttribute(ADMINISTRATOR_TARGET_URL);
        return targetUrl;
    }
}
