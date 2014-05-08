package com.integrasolusi.velocity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Programmer : pancara
 * Date       : 10/23/13
 * Time       : 2:17 AM
 */
public class CollectionsTool {

    public void configure(java.util.Map params) {
        // do nothing
    }

    public boolean isMap(Object obj) {
        return obj instanceof Map;
    }

    public boolean isList(Object obj) {
        return obj instanceof List;
    }

    public List newList() {
        return new LinkedList();
    }
}
