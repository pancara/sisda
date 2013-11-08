package com;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

/**
 * Programmer   : pancara
 * Date         : 7/10/11
 * Time         : 5:29 PM
 */
public class RandomUtilsTest {
    @Test
    public void testGenerate() {

        for(int i = 0; i < 10; i++) {
            System.out.println("RandomUtils.nextInt() = " + RandomUtils.nextInt(10));
        }

    }
}
