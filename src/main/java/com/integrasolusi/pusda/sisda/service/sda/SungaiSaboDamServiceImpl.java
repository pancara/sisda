package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.dao.sda.SaboDamDao;
import com.integrasolusi.pusda.sisda.dao.sda.SungaiSaboDamDao;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.SungaiSaboDam;
import com.integrasolusi.pusda.sisda.persistence.sda.SaboDam;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.utils.FileCacheManager;
import com.integrasolusi.utils.ImageUtils;
import com.integrasolusi.utils.StreamHelper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * User: pancara
 * Date: 8/12/12
 * Time: 11:57 PM
 */
public class SungaiSaboDamServiceImpl implements SungaiSaboDamService {
    private SaboDamDao saboDamDao;
    private SungaiSaboDamDao sungaiSaboDamDao;
    private BlobRepository blobRepository;
    private FileCacheManager fileCacheManager;
    private ImageUtils imageUtils;

    public void setSaboDamDao(SaboDamDao saboDamDao) {
        this.saboDamDao = saboDamDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    public void setSungaiSaboDamDao(SungaiSaboDamDao sungaiSaboDamDao) {
        this.sungaiSaboDamDao = sungaiSaboDamDao;
    }

    public void setFileCacheManager(FileCacheManager fileCacheManager) {
        this.fileCacheManager = fileCacheManager;
    }

    public void setImageUtils(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    @Override
    public List<SungaiSaboDam> findAlls() {
        return sungaiSaboDamDao.findAlls("name", OrderDir.ASC);
    }

    @Override
    public SungaiSaboDam findById(Long id) {
        return sungaiSaboDamDao.findById(id);
    }

    @Override
    public void getMapBlob(Long id, OutputStream os) throws IOException {
        getMapBlob(id, null, null, os);
    }

    @Override
    public void getMapBlob(Long id, Integer w, Integer h, OutputStream os) throws IOException {
        if (w == null && h == null) {
            blobRepository.copyContent(BlobDataType.SDA_SABO_DAM_MAP_SUNGAI, id, os);
            return;
        }

        // check if file is in cache
        String path = getCachePath(id);
        String key = getCacheKey(w, h);

        if (fileCacheManager.exist(path, key)) {
            InputStream is = fileCacheManager.getStream(path, key);
            try {
                StreamHelper.copy(is, os);
            } finally {
                StreamHelper.closeQuiet(is);
            }
        } else {
            SungaiSaboDam sungai = sungaiSaboDamDao.findById(id);
            if (sungai == null)
                return;
            String format = StringUtils.lowerCase(FilenameUtils.getExtension(sungai.getFilename()));

            File temp = blobRepository.getTempFile(BlobDataType.SDA_SABO_DAM_MAP_SUNGAI, id);
            InputStream is = new FileInputStream(temp);
            try {
                BufferedImage image = imageUtils.resizeImage(is, w, h);
                ImageIO.write(image, format, os);
                storeImageToFileCache(path, key, image, format);
            } finally {
                StreamHelper.closeQuiet(is);
                temp.delete();
            }
        }
    }

    private String getCacheKey(Integer w, Integer h) {
        return String.format("%d_%d", w, h);
    }

    private String getCachePath(Long id) {
        return String.format("%s/%d", SungaiSaboDam.class.getSimpleName(), id);
    }

    private void storeImageToFileCache(String path, String key, BufferedImage resized, String format) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        try {
            ImageIO.write(resized, format, os);
            fileCacheManager.store(path, key, is);
        } finally {
            StreamHelper.closeQuiet(is);
            StreamHelper.closeQuiet(os);
        }

    }

    @Override
    public Long countAlls() {
        return sungaiSaboDamDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return sungaiSaboDamDao.countByFilter(createKeywordFilter(keyword));
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("name", QueryOperator.LIKE, keyword, "nama");
    }

    @Override
    public List<SungaiSaboDam> findAlls(Long start, Long count) {
        return sungaiSaboDamDao.findAlls(start, count, "name", OrderDir.ASC);
    }

    @Override
    public List<SungaiSaboDam> findByKeyword(String keywordString, Long start, Long count) {
        return sungaiSaboDamDao.findByFilter(createKeywordFilter(keywordString), start, count, "name", OrderDir.ASC);
    }

    @Override
    public void save(SungaiSaboDam sungai) {
        sungaiSaboDamDao.save(sungai);
    }

    @Override
    public void save(SungaiSaboDam sungai, InputStream inputStream) throws IOException {
        sungaiSaboDamDao.save(sungai);
        blobRepository.store(BlobDataType.SDA_SABO_DAM_MAP_SUNGAI, sungai.getId(), inputStream);
        fileCacheManager.reset(getCachePath(sungai.getId()));
    }

    @Override
    public void removeByIds(Long[] ids) {

        for (Long id : ids) {
            // set null  sabodam sungai
            List<SaboDam> saboDamList = saboDamDao.findByFilter(new ValueFilter("sungai.id", QueryOperator.EQUALS, id, "sungai_id"));
            for (SaboDam sabodam : saboDamList) {
                sabodam.setSungai(null);
                saboDamDao.save(sabodam);
            }

            sungaiSaboDamDao.removeById(id);
            blobRepository.remove(BlobDataType.SDA_SABO_DAM_MAP_SUNGAI, id);
        }
    }

}
