package com.integrasolusi.utils;

import com.integrasolusi.pusda.sisda.persistence.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * Programmer   : pancara
 * Date         : 6/20/11
 * Time         : 12:38 PM
 */
public class ServletSessionHelper {
    public static final String SESSION_LOGGED_USER = "SESSION.LOGGED_USER";

    public static void setSessionAttribute(HttpSession session, String key, Object value) {
        session.setAttribute(key, value);
    }

    public static void setSessionAttribute(HttpServletRequest request, String key, Object value) {
        setSessionAttribute(request.getSession(), key, value);
    }

    public static Object getSessionAttribute(HttpSession session, String key) {
        return session.getAttribute(key);
    }

    public static Object getSessionAttribute(HttpServletRequest request, String key) {
        return getSessionAttribute(request.getSession(), key);
    }

    public static Object getAndClearSessionAttribute(HttpServletRequest request, String key) {
        Object value = getSessionAttribute(request.getSession(), key);
        removeSessionAttribute(request, key);
        return value;
    }

    public static void removeSessionAttribute(HttpSession session, String key) {
        session.removeAttribute(key);
    }

    public static void removeSessionAttribute(HttpServletRequest request, String key) {
        removeSessionAttribute(request.getSession(), key);
    }

    public static void invalidateSession(HttpSession session) {
        Enumeration<String> attributes = session.getAttributeNames();
        while (attributes.hasMoreElements()) {
            String attribute = attributes.nextElement();
            session.removeAttribute(attribute);
        }
        session.invalidate();
    }

    public static void invalidateSession(HttpServletRequest request) {
        invalidateSession(request.getSession());
    }

    public static Object getCurrentUser(HttpServletRequest request) {
        return getSessionAttribute(request, ServletSessionHelper.SESSION_LOGGED_USER);
    }

    public static void setCurrentUser(HttpServletRequest request, Object user) {
        setSessionAttribute(request, SESSION_LOGGED_USER, user);
    }

}
