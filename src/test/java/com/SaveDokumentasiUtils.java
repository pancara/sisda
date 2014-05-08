package com;

import com.integrasolusi.pusda.sisda.persistence.Dokumentasi;
import com.integrasolusi.pusda.sisda.persistence.DokumentasiPhoto;
import com.integrasolusi.pusda.sisda.service.DokumentasiService;
import com.integrasolusi.pusda.sisda.service.DokumentasiPhotoService;
import com.integrasolusi.utils.StreamHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.Date;

/**
 * User: pancara
 * Date: 8/30/12
 * Time: 1:50 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/utils.xml", "/spring/persistence.xml", "/spring/dao.xml",
        "/spring/service.xml", "/spring/repository.xml", "/spring/aspect.xml"})
public class SaveDokumentasiUtils {

    @Autowired
    private DokumentasiService dokumentasiService;

    @Autowired
    private DokumentasiPhotoService dokumentasiPhotoService;


    @Test
    public void populateFolder() throws IOException {
        File root = new File("D:/data/pusda/web/materi web/dokumentasi/data/");
        File[] files = root.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                Dokumentasi gallery = new Dokumentasi();
                gallery.setFilename("dokumentasi.jpg");
                gallery.setPublished(true);
                gallery.setTitle(f.getName());
                gallery.setDescription(f.getName());
                gallery.setShortDescription(f.getName());
                gallery.setPublishedDate(new Date());
                dokumentasiService.save(gallery);
                populateFoto(gallery, f);
            }
        }
    }

    private void populateFoto(Dokumentasi dokumentasi, File dir) throws IOException {
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = org.apache.commons.lang.StringUtils.lowerCase(file.getName());
                return name.endsWith("jpg") ||
                        name.endsWith("tif");
            }
        });

        int index = 0;
        for (File f : files) {
            if (!f.isDirectory()) {
                index++;
                DokumentasiPhoto picture = new DokumentasiPhoto();
                picture.setTitle("Photo " + index);
                picture.setIndex(index);
                picture.setFilename(f.getName());
                picture.setDokumentasi(dokumentasi);
                InputStream is = null;
                try {
                    is = new FileInputStream(f);
                    dokumentasiPhotoService.save(picture, is);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    StreamHelper.closeQuiet(is);
                }
            }
        }
    }
}
