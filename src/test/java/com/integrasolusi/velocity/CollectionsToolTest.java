package com.integrasolusi.velocity;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Programmer : pancara
 * Date       : 10/23/13
 * Time       : 2:19 AM
 */
public class CollectionsToolTest {

    private CollectionsTool tool = new CollectionsTool();

    @Test
    public void isMap() {
        java.util.Map map = new LinkedHashMap<>();
        assertTrue(tool.isMap(map));
        assertFalse(tool.isList(map));

        java.util.List list = new LinkedList();
        assertTrue(tool.isList(list));
        assertFalse(tool.isMap(list));

    }
}
