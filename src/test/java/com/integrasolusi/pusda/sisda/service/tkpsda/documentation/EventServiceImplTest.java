package com.integrasolusi.pusda.sisda.service.tkpsda.documentation;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.documentation.Event;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 7/30/13
 * Time       : 2:59 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence-test.xml", "/spring/dao.xml", "/spring/service.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class EventServiceImplTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;

    @Autowired
    private YearService yearService;

    @Test
    public void testFindByWilayahSungai() throws Exception {
        Year year = yearService.findById(1L);
        WilayahSungai ws = wilayahSungaiService.findById(1L);
        List<Event> events = eventService.findByWilayahSungaiAndYear(ws, year);
    }
}
