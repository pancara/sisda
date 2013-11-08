package com.integrasolusi.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Programmer : pancara
 * Date       : 6/20/13
 * Time       : 4:37 PM
 */
public class JsonUtil {
    public static List<HashMap> toJsonMap(List objects, String[] propertyNames) {
        List jsonMap = new LinkedList();
        for (Object o : objects) {
            Map<String, Object> map = new HashMap<>();
            for (String propertyName : propertyNames) {
                map.put(propertyName, readProperty(o, propertyName));
            }
            jsonMap.add(map);
        }
        return jsonMap;
    }

    private static Object readProperty(Object o, String propertyName) {
        try {
            return BeanUtils.getProperty(o, propertyName);
        } catch (Exception e) {
            return null;
        }
    }
}
