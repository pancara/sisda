package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.sda.sungai.Sungai;
import com.integrasolusi.pusda.sisda.service.sda.sungai.SungaiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 10/12/12
 * Time       : 12:47 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/service.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class SungaiServiceTest {
    @Autowired
    private SungaiService sungaiService;

    @Test
    public void updateSungaiDescription() {
        List<Sungai> sungaiList = sungaiService.findAlls();
        for (Sungai sungai : sungaiList) {
            sungaiService.save(sungai);
        }
    }
}
