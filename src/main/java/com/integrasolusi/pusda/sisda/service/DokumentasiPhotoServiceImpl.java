package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.DokumentasiPhotoDao;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.Dokumentasi;
import com.integrasolusi.pusda.sisda.persistence.DokumentasiPhoto;
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
 * Date: 10/4/12
 * Time: 4:42 PM
 */
public class DokumentasiPhotoServiceImpl implements DokumentasiPhotoService {
    private DokumentasiPhotoDao dokumentasiPhotoDao;
    private BlobRepository blobRepository;
    private FileCacheManager fileCacheManager;
    private ImageUtils imageUtils;

    public void setDokumentasiPhotoDao(DokumentasiPhotoDao dokumentasiPhotoDao) {
        this.dokumentasiPhotoDao = dokumentasiPhotoDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    public void setFileCacheManager(FileCacheManager fileCacheManager) {
        this.fileCacheManager = fileCacheManager;
    }

    public void setImageUtils(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    @Override
    public DokumentasiPhoto findById(Long id) {
        return dokumentasiPhotoDao.findById(id);
    }

    @Override
    public void save(DokumentasiPhoto photo) {
        dokumentasiPhotoDao.save(photo);
    }

    @Override
    public void save(DokumentasiPhoto picture, InputStream is) throws IOException {
        dokumentasiPhotoDao.save(picture);
        blobRepository.store(BlobDataType.DOKUMENTASI_PHOTO, picture.getId(), is);
    }

    @Override
    public List<DokumentasiPhoto> findByDokumentasi(Dokumentasi dokumentasi) {
        return findByDokumentasi(dokumentasi, null, null);
    }

    @Override
    public List<DokumentasiPhoto> findByDokumentasi(Dokumentasi dokumentasi, Long start, Long count) {
        Filter filter = new ValueFilter("dokumentasi", QueryOperator.EQUALS, dokumentasi, "dokumentasi");
        if (start == null && count == null) {
            return dokumentasiPhotoDao.findByFilter(filter, "id", OrderDir.ASC);
        } else {
            return dokumentasiPhotoDao.findByFilter(filter, start, count, "id", OrderDir.ASC);
        }
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        getBlob(id, os, null, null);
    }

    @Override
    public void getBlob(Long id, OutputStream os, Integer w, Integer h) throws IOException {
        if (w == null && h == null) {
            blobRepository.copyContent(BlobDataType.DOKUMENTASI_PHOTO, id, os);
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
            DokumentasiPhoto photo = dokumentasiPhotoDao.findById(id);
            if (photo == null)
                return;
            String format = StringUtils.lowerCase(FilenameUtils.getExtension(photo.getFilename()));

            File temp = blobRepository.getTempFile(BlobDataType.DOKUMENTASI_PHOTO, id);
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
        return String.format("%s/%d", DokumentasiPhoto.class.getSimpleName(), id);
    }

    private void storeImageToFileCache(String path, String key, BufferedImage resized, String format) throws IOException {
        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            ImageIO.write(resized, format, os);
            is = new ByteArrayInputStream(os.toByteArray());
            fileCacheManager.store(path, key, is);
        } finally {
            StreamHelper.closeQuiet(is);
            StreamHelper.closeQuiet(os);
        }
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            dokumentasiPhotoDao.removeById(id);
            blobRepository.remove(BlobDataType.DOKUMENTASI_PHOTO, id);
        }
    }
}
