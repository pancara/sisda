package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.PhotoDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.Photo;
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
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 2:11 PM
 */
public class PhotoServiceImpl implements PhotoService {
    private PhotoDao photoDao;
    private BlobRepository blobRepository;
    private FileCacheManager fileCacheManager;
    private ImageUtils imageUtils;

    public void setPhotoDao(PhotoDao photoDao) {
        this.photoDao = photoDao;
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
    public void save(Photo photo) {
        photoDao.save(photo);
    }

    @Override
    public void save(Photo photo, InputStream is) throws IOException {
        photoDao.save(photo);
        blobRepository.store(BlobDataType.REPOSITORY_PHOTO, photo.getId(), is);
        fileCacheManager.reset(getCachePath(photo.getId()));
    }

    @Override
    public void removeById(Long id) {
        photoDao.removeById(id);
        blobRepository.remove(BlobDataType.REPOSITORY_PHOTO, id);
    }

    @Override
    public Long countAlls() {
        return photoDao.countAlls();
    }

    @Override
    public Photo findById(Long id) {
        return photoDao.findById(id);
    }

    @Override
    public Long countByKeyword(String keyword) {
        return photoDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public List<Photo> findAlls(Long start, Long count) {
        return photoDao.findAlls(start, count, "id", OrderDir.DESC);
    }

    private CompositeFilter createKeywordFilter(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.OR);
        filter.add(new ValueFilter("title", QueryOperator.LIKE, keyword));
        filter.add(new ValueFilter("filename", QueryOperator.LIKE, keyword));
        return filter;
    }

    @Override
    public List<Photo> findByKeyword(String keyword, Long start, Long count) {
        return photoDao.findByFilter(createKeywordFilter(keyword), start, count, "id", OrderDir.DESC);
    }


    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        getBlob(id, null, null, os);
    }

    @Override
    public void getBlob(Long id, Integer w, Integer h, OutputStream os) throws IOException {
        if (w == null && h == null) {
            blobRepository.copyContent(BlobDataType.REPOSITORY_PHOTO, id, os);
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
            Photo photo = photoDao.findById(id);
            if (photo == null)
                return;
            String format = StringUtils.lowerCase(FilenameUtils.getExtension(photo.getFilename()));
            File file = blobRepository.getTempFile(BlobDataType.REPOSITORY_PHOTO, id);
            InputStream is = new FileInputStream(file);
            try {
                BufferedImage image = imageUtils.resizeImage(is, w, h);
                ImageIO.write(image, format, os);
                storeImageToFileCache(path, key, image, format);
            } finally {
                StreamHelper.closeQuiet(is);
                file.delete();
            }
        }

    }

    @Override
    public void removeByIds(Long[] ids) throws IOException {
        for (Long id : ids) {
            photoDao.removeById(id);
            blobRepository.remove(BlobDataType.REPOSITORY_PHOTO, id);
            fileCacheManager.reset(getCachePath(id));
        }
    }

    @Override
    public void publishByIds(Long[] ids) {
        for (Long id : ids) {
            Photo photo = photoDao.findById(id);
            photo.setPublished(true);
            photoDao.save(photo);
        }
    }

    @Override
    public void unpublishByIds(Long[] ids) {
        for (Long id : ids) {
            Photo photo = photoDao.findById(id);
            photo.setPublished(false);
            photoDao.save(photo);
        }
    }

    private String getCacheKey(Integer w, Integer h) {
        return String.format("%d_%d", w, h);
    }

    private String getCachePath(Long id) {
        return String.format("%s/%d", Photo.class.getSimpleName(), id);
    }

    private void storeImageToFileCache(String path, String key, BufferedImage resized, String format) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(resized, format, os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        fileCacheManager.store(path, key, is);

        StreamHelper.closeQuiet(is);
        StreamHelper.closeQuiet(os);
    }
}
