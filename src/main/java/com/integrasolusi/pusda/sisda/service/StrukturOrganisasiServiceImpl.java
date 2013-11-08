package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.StrukturOrganisasiDao;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.StrukturOrganisasi;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.utils.FileCacheManager;
import com.integrasolusi.utils.ImageUtils;
import com.integrasolusi.utils.StreamHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 11/27/12
 * Time       : 2:55 PM
 */
public class StrukturOrganisasiServiceImpl implements StrukturOrganisasiService {
    private StrukturOrganisasiDao strukturOrganisasiDao;
    private BlobRepository blobRepository;
    private FileCacheManager fileCacheManager;
    private ImageUtils imageUtils;
    private StreamHelper streamHelper;

    public void setStrukturOrganisasiDao(StrukturOrganisasiDao strukturOrganisasiDao) {
        this.strukturOrganisasiDao = strukturOrganisasiDao;
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

    public void setStreamHelper(StreamHelper streamHelper) {
        this.streamHelper = streamHelper;
    }

    @Override
    public StrukturOrganisasi findById(Long id) {
        return strukturOrganisasiDao.findById(id);
    }

    @Override
    public List<StrukturOrganisasi> findAlls() {
        return strukturOrganisasiDao.findAlls("id", OrderDir.ASC);
    }

    @Override
    public void getBlob(Long id, OutputStream outputStream) throws IOException {
        getBlob(id, null, null, outputStream);
    }

    @Override
    public void getBlob(Long id, Integer w, Integer h, OutputStream os) throws IOException {
        if (w == null && h == null) {
            blobRepository.copyContent(BlobDataType.STRUKTUR_ORGANISASI, id, os);
            return;
        }

        // check if file is in cache
        String path = getCachePath(id);
        String key = getCacheKey(w, h);

        InputStream is = fileCacheManager.getStream(path, key);
        if (is == null) {
            is = blobRepository.getStream(BlobDataType.STRUKTUR_ORGANISASI, id);
            if (is != null) {
                BufferedImage resized = imageUtils.resizeImage(is, w, h);
                storeImageToFileCache(path, key, resized);
                StreamHelper.closeQuiet(is);
            }
        }

        is = fileCacheManager.getStream(path, key);
        if (is != null) {
            streamHelper.copy(is, os);
            StreamHelper.closeQuiet(is);
        }
    }

    private String getCacheKey(Integer w, Integer h) {
        return String.format("%d_%d", w, h);
    }

    private String getCachePath(Long id) {
        return String.format("%s/%d", StrukturOrganisasi.class.getSimpleName(), id);
    }

    private void storeImageToFileCache(String path, String key, BufferedImage resized) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(resized, "PNG", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        fileCacheManager.store(path, key, is);

        StreamHelper.closeQuiet(is);
        StreamHelper.closeQuiet(os);
    }

    @Override
    public void save(StrukturOrganisasi so) {
        strukturOrganisasiDao.save(so);
    }

    @Override
    public void save(StrukturOrganisasi so, InputStream inputStream) throws IOException {
        strukturOrganisasiDao.save(so);
        blobRepository.store(BlobDataType.STRUKTUR_ORGANISASI, so.getId(), inputStream);
        fileCacheManager.reset(getCachePath(so.getId()));
    }
}
