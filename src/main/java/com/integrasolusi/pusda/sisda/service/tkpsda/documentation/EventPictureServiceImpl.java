package com.integrasolusi.pusda.sisda.service.tkpsda.documentation;

import com.integrasolusi.pusda.sisda.dao.tkpsda.documentation.EventPictureDao;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.documentation.Event;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.documentation.EventPicture;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
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
 * Programmer : pancara
 * Date       : 11/12/12
 * Time       : 3:51 PM
 */
public class EventPictureServiceImpl implements EventPictureService {
    private BlobRepository blobRepository;
    private EventPictureDao eventPictureDao;
    private FileCacheManager fileCacheManager;
    private ImageUtils imageUtils;

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    public void setEventPictureDao(EventPictureDao eventPictureDao) {
        this.eventPictureDao = eventPictureDao;
    }

    public void setFileCacheManager(FileCacheManager fileCacheManager) {
        this.fileCacheManager = fileCacheManager;
    }

    public void setImageUtils(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    @Override
    public EventPicture findById(Long id) {
        return eventPictureDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        getBlob(id, null, null, os);
    }

    @Override
    public void getBlob(Long id, Integer w, Integer h, OutputStream os) throws IOException {
        if (w == null && h == null) {
            blobRepository.copyContent(BlobDataType.TKPSDA_EVENT_PICTURE, id, os);
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
            EventPicture picture = eventPictureDao.findById(id);
            if (picture == null)
                return;

            String format = StringUtils.lowerCase(FilenameUtils.getExtension(picture.getFilename()));

            File temp = blobRepository.getTempFile(BlobDataType.TKPSDA_EVENT_PICTURE, id);
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
        return String.format("%s/%d", EventPicture.class.getSimpleName(), id);
    }

    private void storeImageToFileCache(String path, String key, BufferedImage resized, String format) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(resized, format, os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        fileCacheManager.store(path, key, is);

        StreamHelper.closeQuiet(is);
        StreamHelper.closeQuiet(os);
    }

    @Override
    public void save(EventPicture pic) {
        eventPictureDao.save(pic);
    }

    @Override
    public void save(EventPicture pic, InputStream inputStream) throws IOException {
        eventPictureDao.save(pic);
        blobRepository.store(BlobDataType.TKPSDA_EVENT_PICTURE, pic.getId(), inputStream);

        fileCacheManager.reset(getCachePath(pic.getId()));
    }

    @Override
    public void removeByIds(Long[] ids) throws IOException {
        for (Long id : ids) {
            eventPictureDao.removeById(id);
        }

        for (Long id : ids) {
            blobRepository.remove(BlobDataType.TKPSDA_EVENT_PICTURE, id);
            fileCacheManager.reset(getCachePath(id));
        }
    }

    @Override
    public List<EventPicture> findByEvent(Event event) {
        return eventPictureDao.findByFilter(new ValueFilter("event", QueryOperator.EQUALS, event, "event"), "index", OrderDir.ASC);
    }


}
