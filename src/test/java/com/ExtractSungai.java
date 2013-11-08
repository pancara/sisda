package com;

import com.integrasolusi.pusda.sisda.dao.sda.SungaiSaboDamDao;
import com.integrasolusi.pusda.sisda.persistence.region.SungaiSaboDam;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User: pancara
 * Date: 8/9/12
 * Time: 8:26 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/utils.xml", "/spring/persistence.xml", "/spring/dao.xml"})
public class ExtractSungai {

    @Autowired
    private SungaiSaboDamDao sungaiSaboDamDao;

    @Test
    public void extractSungai() throws IOException {
        Assert.assertNotNull(sungaiSaboDamDao);

        File file = new File("E:/sungai.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Set<String> sungaiList = new HashSet<String>();
        while (reader.ready()) {
            String sungai = reader.readLine();
            sungaiList.add(sungai);
        }
        reader.close();

        for (String s : sungaiList) {
            SungaiSaboDam sungai = new SungaiSaboDam();
            sungai.setName(s);
            sungaiSaboDamDao.save(sungai);
        }
    }
}
