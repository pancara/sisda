package com.integrasolusi.pusda.sisda.web.controller.editor;

import com.integrasolusi.pusda.sisda.web.controller.map.OrganizationType;
import org.apache.commons.lang.WordUtils;

import java.beans.PropertyEditorSupport;

/**
 * Programmer   : pancara
 * Date         : 7/20/11
 * Time         : 2:59 PM
 */
public class OrganizationTypeEnumConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(OrganizationType.valueOf(text));
    }
}
