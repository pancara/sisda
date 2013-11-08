package com.integrasolusi.utils;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.Map;

/**
 * Programmer   : pancara
 * Date         : Jan 19, 2011
 * Time         : 9:06:32 PM
 */
public class TextGenerator {
    public static enum NotifyType {
        NOTIFY_COMMENT_GUEST
    }

    private VelocityEngine velocityEngine;

    private String notifyCommentGuestTemplate;

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void setNotifyCommentGuestTemplate(String notifyCommentGuestTemplate) {
        this.notifyCommentGuestTemplate = notifyCommentGuestTemplate;
    }

    private String applyTemplate(String templateName, Map model) {
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, model);
    }

    public String generateText(NotifyType type, Map model) {
        switch (type) {
            case NOTIFY_COMMENT_GUEST:
                return applyTemplate(notifyCommentGuestTemplate, model);
        }
        return null;
    }


}
