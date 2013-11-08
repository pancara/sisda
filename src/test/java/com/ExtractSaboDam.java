package com;

import com.integrasolusi.pusda.sisda.dao.sda.SaboDamDao;
import com.integrasolusi.pusda.sisda.dao.sda.SungaiSaboDamDao;
import com.integrasolusi.pusda.sisda.persistence.region.SungaiSaboDam;
import com.integrasolusi.pusda.sisda.persistence.sda.SaboDam;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * User: pancara
 * Date: 8/9/12
 * Time: 8:26 PM
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/utils.xml", "/spring/persistence.xml", "/spring/dao.xml"})
public class ExtractSaboDam {

    @Autowired
    private SungaiSaboDamDao sungaiSaboDamDao;

    @Autowired
    private SaboDamDao saboDamDao;

    @Test
    public void extractSaboDam() throws IOException {
        Assert.assertNotNull(saboDamDao);

        File file = new File("E:/sabodam.csv");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> doubleCodeList = new LinkedList<String>();
        while (reader.ready()) {
            String line = reader.readLine();

            if (StringUtils.isEmpty(line))
                continue;
            String[] tokens = StringUtils.split(line, ",");

            String sungaiName = tokens[0];
            SungaiSaboDam sungai = sungaiSaboDamDao.findUniqueByProperty("name", sungaiName);

            String code = extractCode(tokens[1]);
            Long x = null;
            Long y = null;
            if (tokens.length > 2) {
                x = Long.parseLong(tokens[2]);
                y = Long.parseLong(tokens[3]);
            }
            System.out.println(String.format("%s >>>> %d, %d", code, x, y));

            SaboDam saboDam = saboDamDao.findUniqueByProperty("code", code);

            if (saboDam != null) {
                doubleCodeList.add(code);
            } else {
                saboDam = new SaboDam();
                saboDam.setCode(code);
                saboDam.setSungai(sungai);
                saboDam.setX(x);
                saboDam.setY(y);
            }

            saboDamDao.save(saboDam);
        }
        reader.close();

        for (String code : doubleCodeList) {
            System.out.println(code);
        }
    }

    private String extractCode(String name) {
        return name;
    }

    @Test
    public void testExtractCode() {
        String s = "ABC (DSDFSD)";
        String code = extractCode(s);
        System.out.println(code);
    }
}

