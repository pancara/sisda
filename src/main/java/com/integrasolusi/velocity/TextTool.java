package com.integrasolusi.velocity;

import com.integrasolusi.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.clapper.util.html.HTMLUtil;

/**
 * Programmer   : pancara
 * Date         : 6/11/11
 * Time         : 10:55 AM
 */
public class TextTool {

    public void configure(java.util.Map params) {
        // do nothing
    }

    public String toHtml(String text) {
        return HTMLUtil.textFromHTML(text);
    }

    public String substring(String text, int length) {
        return StringUtils.abbreviate(text, length);
    }

    public String removeHtmlTag(String text) {
        return HTMLUtil.stripHTMLTags(text);
//        return StringHelper.removeHtmlTag(text);
    }

    public String[] toLines(String text) {
        return StringUtils.split(text, "\n");
    }

    public String stringToHex(String text) {
        return stringToHex(text, true);
    }

    public String stringToHex(String text, boolean upperCase) {
        return StringHelper.stringToHex(text, upperCase);
    }

    public String capitalize(String str) {
        return StringUtils.capitalize(str);
    }
}
