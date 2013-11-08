package com.integrasolusi.utils;

import org.apache.commons.lang.StringUtils;

import javax.activation.MimetypesFileTypeMap;

/**
 * Programmer   : pancara
 * Date         : 7/12/11
 * Time         : 12:36 PM
 */
public class ContentTypeUtils {
    private MimetypesFileTypeMap mimetypesFileTypeMap;

    public ContentTypeUtils() {
        mimetypesFileTypeMap = new MimetypesFileTypeMap();
    }

    public String getContentType(String filename) {
        filename = StringUtils.lowerCase(filename);
        return mimetypesFileTypeMap.getContentType(filename);
    }

    public String encodeFilename(String filename) {
        return StringUtils.replace(filename, " ", "_");
    }


}

