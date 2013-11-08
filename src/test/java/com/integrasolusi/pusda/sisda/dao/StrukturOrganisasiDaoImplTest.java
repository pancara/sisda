package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.pusda.sisda.persistence.StrukturOrganisasi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Programmer : pancara
 * Date       : 11/27/12
 * Time       : 2:49 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class StrukturOrganisasiDaoImplTest {
    @Autowired
    private StrukturOrganisasiDao strukturOrganisasiDao;

    @Test
    public void save() {
        String[] data = new String[]{
                "BBWS Serayu Opak",
                "Satker BBWS Serayu Opak",
                "Satker PJSA",
                "Satker PJPA"
        };
        for (String name : data) {
            StrukturOrganisasi so = new StrukturOrganisasi();
            so.setName(name);
            so.setDescription(name);
            so.setFilename(name + ".jpg");
            strukturOrganisasiDao.save(so);
        }
    }
}
