package com.integrasolusi.pusda.sisda.web.interceptor;

import com.integrasolusi.pusda.sisda.web.utils.SecurityUtils;
import com.integrasolusi.utils.ServletSessionHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/24/11
 * Time         : 8:23 PM
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = Logger.getLogger(SecurityInterceptor.class);

    private List<String> adminUrls = new LinkedList<String>();
    private String redirectUrl;
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private SecurityUtils securityUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!validateRequest(request)) {
            securityUtils.storeTargetUrl(request);
            response.sendRedirect(getRedirectUrl());
            return false;
        }

        return true;
    }

    public boolean validateRequest(HttpServletRequest request) {
        String url = request.getRequestURI();
        if (!isAdminUrl(url))
            return true;

        Object adminId = ServletSessionHelper.getCurrentUser(request);
        return (adminId != null);
    }

    private boolean isAdminUrl(String url) {
        for (String path : adminUrls) {
            if (pathMatcher.isPattern(path)) {
                if (pathMatcher.match(path, url))
                    return true;
            } else {
                if (StringUtils.equals(path, url))
                    return true;
            }

        }
        return false;
    }

    public void setAdminUrls(List<String> adminUrls) {
        this.adminUrls = adminUrls;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setSecurityUtils(SecurityUtils securityUtils) {
        this.securityUtils = securityUtils;
    }
}
