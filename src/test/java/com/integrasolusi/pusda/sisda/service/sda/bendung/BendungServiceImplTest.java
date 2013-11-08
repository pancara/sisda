package com.integrasolusi.pusda.sisda.service.sda.bendung;

import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.bendung.Bendung;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 10/24/13
 * Time       : 7:26 PM
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence-test.xml", "/spring/dao.xml", "/spring/service.xml", "/spring/repository.xml", "/spring/utils.xml"})

public class BendungServiceImplTest {
    @Autowired
    private BendungService bendungService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;


    @Test
    public void findByWs() {
        WilayahSungai ws = wilayahSungaiService.findById(1L);
        List<Bendung> listBendung = bendungService.findByWs(ws);
    }
}
