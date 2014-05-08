package com;

import com.integrasolusi.pusda.sisda.persistence.regulation.Folder;
import com.integrasolusi.pusda.sisda.persistence.regulation.Regulation;
import com.integrasolusi.pusda.sisda.service.RegulationFolderService;
import com.integrasolusi.pusda.sisda.service.RegulationService;
import com.integrasolusi.utils.StreamHelper;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * User: pancara
 * Date: 8/30/12
 * Time: 1:50 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/utils.xml", "/spring/persistence.xml", "/spring/dao.xml",
        "/spring/service.xml", "/spring/repository.xml", "/spring/aspect.xml"})
public class RegulationDataPopulator {
    @Autowired
    private RegulationFolderService regulationFolderService;

    @Autowired
    private RegulationService regulationService;


    @Test
    public void populateFolder() throws IOException {
        File root = new File("D:/data/pusda/web/DATA SISDA - OK/Peraturan & UU");
        populatePath(null, root);
    }

    private void populatePath(Folder parent, File f) throws IOException {
        File[] files = f.listFiles();
        int idx = 1;
        for (File child : files) {
            if (child.isDirectory()) {
                Folder folder = new Folder();
                folder.setName(FilenameUtils.removeExtension(child.getName()));
                folder.setDescription(FilenameUtils.removeExtension(child.getName()));
                folder.setIndex(idx);
                folder.setParent(parent);
                regulationFolderService.save(folder);
                populatePath(folder, child);
            } else {
                Regulation regulation = new Regulation();
                regulation.setTitle(FilenameUtils.removeExtension(child.getName()));
                regulation.setDescription(FilenameUtils.removeExtension(child.getName()));
                regulation.setFilename(child.getName());
                regulation.setFolder(parent);
                regulation.setIndex(idx);
                InputStream is = null;
                try {
                    is = new FileInputStream(child);
                    regulationService.save(regulation, is);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    StreamHelper.closeQuiet(is);
                }
            }
            idx++;
        }
    }
}
