package com;

import com.integrasolusi.pusda.sisda.persistence.sda.SaboDam;
import com.integrasolusi.pusda.sisda.service.sda.SaboDamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * User: pancara
 * Date: 8/30/12
 * Time: 1:50 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/utils.xml", "/spring/persistence.xml", "/spring/dao.xml",
        "/spring/service.xml", "/spring/repository.xml", "/spring/aspect.xml"})
public class SaboDamBlobVerifier {

    @Autowired
    private SaboDamService saboDamService;


    @Test
    public void verify() throws IOException {
        List<SaboDam> saboDamList = saboDamService.findAlls();
        for (SaboDam sabodam : saboDamList) {
            if (!saboDamService.hasDocumentBlob(sabodam.getId())) {
                sabodam.setFilename(null);
                saboDamService.save(sabodam);
            }
        }
    }
}
