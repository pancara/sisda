package com;

import com.integrasolusi.pusda.sisda.persistence.Map;
import com.integrasolusi.pusda.sisda.persistence.MapCategory;
import com.integrasolusi.pusda.sisda.service.MapCategoryService;
import com.integrasolusi.pusda.sisda.service.MapService;
import org.apache.commons.io.FilenameUtils;
import org.apache.velocity.util.StringUtils;
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
public class SavePetaUtils {

    @Autowired
    private MapService mapService;

    @Autowired
    private MapCategoryService mapCategoryService;


    @Test
    public void populateFolder() throws IOException {
        File root = new File("D:/data/pusda/web/materi web/Peta/");
        File[] files = root.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                Long id = getCategoryId(f.getName());
                if (id.equals(3L)) {
                    MapCategory category = mapCategoryService.findById(id);
                    populateFile(category, f);
                }
            }
        }
    }

    private Long getCategoryId(String folderName) {
        String[] tokens = StringUtils.split(folderName, "-");
        return Long.parseLong(tokens[0].trim());
    }

    private void populateFile(MapCategory category, File dir) throws IOException {
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = org.apache.commons.lang.StringUtils.lowerCase(file.getName());
                return name.endsWith("jpg") ||
                        name.endsWith("tif");
            }
        });

        for (File f : files) {
            if (!f.isDirectory()) {
                Map map = new Map();
                map.setCategory(category);
                map.setPublished(true);
                map.setName(FilenameUtils.removeExtension(f.getName()));
                map.setDescription(FilenameUtils.removeExtension(f.getName()));
                map.setFilename(f.getName());
                map.setSize(f.length());

                InputStream is = null;
                try {
                    is = new FileInputStream(f);
                    mapService.save(map, is);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    is.close();
                }
            }
        }
    }
}
