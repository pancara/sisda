package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.pusda.sisda.dao.sda.DasDao;
import com.integrasolusi.pusda.sisda.dao.sda.sungai.SungaiDao;
import com.integrasolusi.pusda.sisda.dao.sda.WilayahSungaiDao;
import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.sungai.Sungai;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.utils.StreamHelper;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;

/**
 * User: pancara
 * Date: 9/3/12
 * Time: 12:52 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/persistence.xml", "/spring/dao.xml", "/spring/repository.xml", "/spring/utils.xml"})
public class SungaiDaoImplTest {
    @Autowired
    private SungaiDao sungaiDao;

    @Autowired
    private DasDao dasDao;

    @Autowired
    private WilayahSungaiDao wilayahSungaiDao;

    @Autowired
    private BlobRepository blobRepository;

    @Test
    public void populateData() throws IOException {
        File dir = new File("D:/data/pusda/web/DATA SISDA - OK/Data Sungai/");
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File path) {
                return path.getName().endsWith("pdf") && path.isFile();
            }
        });

        WilayahSungai ws = wilayahSungaiDao.findById(1L);
        for (File f : files) {
            String dasName = FilenameUtils.removeExtension(f.getName());
            Das das = dasDao.findUniqueByProperty("name", dasName);
            if (das == null) {
                das = new Das();
                das.setName(dasName);
                das.setWilayahSungai(ws);
                dasDao.save(das);
            }

            Sungai sungai = new Sungai();
            sungai.setName(f.getName());
            sungai.setDescription(f.getName());
            sungai.setFilename(f.getName());
            sungaiDao.save(sungai);

            saveFile(sungai, f);
        }
    }

    private void saveFile(Sungai sungai, File file) throws IOException {
        FileInputStream is = new FileInputStream(file);
        blobRepository.store(BlobDataType.SDA_SUNGAI, sungai.getId(), is);
        StreamHelper.closeQuiet(is);
    }
}
