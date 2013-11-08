package com.integrasolusi.pusda.sisda.web.utils;

import com.integrasolusi.pusda.sisda.persistence.Sda;

import java.util.HashMap;
import java.util.Map;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 6:12 PM
 */
public class SdaForwardPathResolver {
    private Map<Long, String> pathMapping = new HashMap<Long, String>();

    public void setPathMapping(Map<Long, String> pathMapping) {
        this.pathMapping = pathMapping;
    }

    public String getForwardPath(Sda sda) {
        return pathMapping.get(sda.getId());
    }
}
