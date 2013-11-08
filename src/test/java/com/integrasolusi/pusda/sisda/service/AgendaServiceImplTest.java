package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Agenda;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Programmer   : pancara
 * Date         : 6/27/11
 * Time         : 9:44 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/service.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class AgendaServiceImplTest {
    @Autowired
    private AgendaService agendaService;

    @Test
    public void testGetNearest() throws Exception {
        java.util.List<Agenda> agendaList = agendaService.getNearest(new java.util.Date());
        for(Agenda agenda : agendaList) {
            System.out.println(agenda.getTitle());
        }

    }
}
