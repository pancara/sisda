package com;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.regulation.Folder;
import com.integrasolusi.pusda.sisda.persistence.sda.Kekeringan;
import com.integrasolusi.pusda.sisda.service.sda.KekeringanService;
import com.integrasolusi.pusda.sisda.service.sda.WilayahSungaiService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import com.integrasolusi.utils.StreamHelper;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;

/**
 * User: pancara
 * Date: 8/30/12
 * Time: 1:50 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/utils.xml", "/spring/persistence.xml", "/spring/dao.xml",
        "/spring/service.xml", "/spring/repository.xml", "/spring/aspect.xml"})
public class KekeringanDataPopulator {
    @Autowired
    private YearService yearService;

    @Autowired
    private KekeringanService kekeringanService;

    @Autowired
    private WilayahSungaiService wilayahSungaiService;


    @Test
    public void populateFolder() throws IOException {
        File root = new File("D:/data/pusda/web/materi web/Data Kekeringan");
        iterateWilayahSungai(root);
    }

    private void iterateWilayahSungai(File root) {
        File[] files = root.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        for (File f : files) {
            String[] tokens = StringUtils.split(f.getName(), " ");
            Long id = Long.valueOf(tokens[1]);
            WilayahSungai ws = wilayahSungaiService.findById(id);
            iterateYear(f, ws);
        }
    }

    private void iterateYear(File dir, WilayahSungai ws) {
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory();
            }
        });

        for (File f : files) {
            String yearString = f.getName();
            Integer yearValue = Integer.valueOf(yearString);
            Year year = yearService.findByValue(yearValue);

            populateKekeringanFile(f, ws, year);
        }
    }

    private void populateKekeringanFile(File dir, WilayahSungai ws, Year year) {
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return !f.isDirectory() && f.getName().toLowerCase().endsWith("pdf");
            }
        });

        for (File f : files) {
            Kekeringan kekeringan = new Kekeringan();
            kekeringan.setDescription(getDescriptionFromFileName(f.getName()));
            kekeringan.setFilename(f.getName());
            kekeringan.setWilayahSungai(ws);
            kekeringan.setYear(year);
            InputStream is = null;
            try {
                is = new FileInputStream(f);
                kekeringanService.save(kekeringan, is);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                StreamHelper.closeQuiet(is);
            }
        }
    }

    private String getDescriptionFromFileName(String filename) {
        String[] tokens = StringUtils.split(filename, "-");
        return tokens[0].trim();

    }

    private void populatePath(Folder parent, File f) throws IOException {
//        File[] files = f.listFiles();
//        int idx = 1;
//        for (File child : files) {
//            if (child.isDirectory()) {
//                Folder folder = new Folder();
//                folder.setName(FilenameUtils.removeExtension(child.getName()));
//                folder.setDescription(FilenameUtils.removeExtension(child.getName()));
//                folder.setIndex(idx);
//                folder.setParent(parent);
//                regulationFolderService.save(folder);
//                populatePath(folder, child);
//            } else {
//                Regulation regulation = new Regulation();
//                regulation.setTitle(FilenameUtils.removeExtension(child.getName()));
//                regulation.setDescription(FilenameUtils.removeExtension(child.getName()));
//                regulation.setFilename(child.getName());
//                regulation.setFolder(parent);
//                regulation.setIndex(idx);
//                InputStream is = null;
//                try {
//                    is = new FileInputStream(child);
//                    regulationService.save(regulation, is);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    is.close();
//                }
//            }
//            idx++;
//        }
    }
}
