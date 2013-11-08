package com.integrasolusi.pusda.sisda.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Programmer : pancara
 * Date       : 7/22/13
 * Time       : 3:40 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence-test.xml", "/spring/dao.xml", "/spring/service.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class VisitorCounterDaoImplTest {

    @Autowired
    private VisitorCounterDao visitorCounterDao;

    @Test
    public void testGetTotal() throws Exception {
        Long count = visitorCounterDao.getTotal();
        System.out.println(count);

    }

    @Test
    public void testGetTotalInDate() throws Exception {
        Calendar cal = new GregorianCalendar();

        System.out.println(cal);
        Calendar start = (Calendar) cal.clone();
        start.add(Calendar.DAY_OF_WEEK, start.getFirstDayOfWeek() - start.get(Calendar.DAY_OF_WEEK));

        // and add six days to the end date
        Calendar end = (Calendar) start.clone();
        end.add(Calendar.DAY_OF_YEAR, 6);

        Long count = visitorCounterDao.getTotal(start.getTime(), end.getTime());
        System.out.println(count);
    }

    @Test
    public void testGetTotalInMonth() throws Exception {
        Calendar cal = new GregorianCalendar();

        Long count = visitorCounterDao.getTotal(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
        System.out.println(count);
    }
}
