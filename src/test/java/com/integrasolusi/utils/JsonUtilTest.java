package com.integrasolusi.utils;

import com.integrasolusi.pusda.sisda.persistence.ffws.WaterLevel;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 6/20/13
 * Time       : 4:51 PM
 */
public class JsonUtilTest {
    @Test
    public void testToJsonMap() throws Exception {
        List<WaterLevel> waterLevelList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            WaterLevel w = new WaterLevel();
            w.setId(Integer.valueOf(i).longValue());
            w.setValue(i * 9.9);
            w.setSamplingAt(new Date());

            waterLevelList.add(w);
        }

        List jsonMap = JsonUtil.toJsonMap(waterLevelList, new String[]{"id", "value"});
        System.out.println(jsonMap);
    }
}
