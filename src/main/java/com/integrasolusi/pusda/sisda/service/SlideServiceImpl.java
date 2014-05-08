package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.SlideDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.Slide;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.utils.FileCacheManager;
import com.integrasolusi.utils.ImageUtils;
import com.integrasolusi.utils.StreamHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

//import org.apache.commons.lang.math.RandomUtils;

/**
 * Programmer   : pancara
 * Date         : 7/10/11
 * Time         : 2:47 PM
 */
public class SlideServiceImpl implements SlideService {
    private SlideDao slideDao;
    private BlobRepository blobRepository;
    private FileCacheManager fileCacheManager;
    private ImageUtils imageUtils;

    public void setSlideDao(SlideDao slideDao) {
        this.slideDao = slideDao;
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
    public List<Slide> getPublished() {
        return slideDao.findByFilter(new ValueFilter("published", QueryOperator.EQUALS, true));
    }

    @Override
    public Slide getNext(Long currentId) {
        Long currentIndex = 0L;
        Slide current = null;
        if (currentId != null) {
            current = slideDao.findById(currentId);
            currentIndex = current.getIndex();
        }
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);

        ValueFilter publishedFilter = new ValueFilter("published", QueryOperator.EQUALS, true);
        filter.add(publishedFilter);

        ValueFilter indexFilter = new ValueFilter("index", QueryOperator.GREATER, currentIndex);
        filter.add(indexFilter);

        Slide slide = slideDao.findUniqueByFilter(filter);
        if (slide == null) {
            indexFilter.setValue(0L);
            slide = slideDao.findUniqueByFilter(filter);
        }
        return slide;
    }

    @Override
    public void reindexAlls() {
        List<Slide> slides = slideDao.findAlls("index", OrderDir.ASC);
        Long index = 1L;
        for (Slide slide : slides) {
            slide.setIndex(index);
            index++;
            slideDao.save(slide);
        }
    }

    @Override
    public Slide findById(Long id) {
        return slideDao.findById(id);
    }

    @Override
    public Slide getRandom() {
        ValueFilter filter = new ValueFilter("published", QueryOperator.EQUALS, true);
        Long count = slideDao.countByFilter(filter);
        if (count > 0) {
            Integer rowIndex = RandomUtils.nextInt(count.intValue());
            return slideDao.findByIndex(filter, rowIndex.longValue(), "index", OrderDir.ASC);
        }
        return null;
    }

    @Override
    public BufferedImage getPicture(Long id) throws IOException {
        return blobRepository.createImage(BlobDataType.SLIDE, id);
    }

    @Override
    public Long countAlls() {
        return slideDao.countAlls();
    }

    @Override
    public List<Slide> get(Long start, Long count) {
        return slideDao.findAlls(start, count);
    }

    @Override
    public void removeById(Long id) {
        slideDao.removeById(id);
        blobRepository.remove(BlobDataType.SLIDE, id);
    }

    @Override
    public void save(Slide slide, InputStream is) throws IOException {
        if (slide.getIndex() == null) {
            Long newIndex = slideDao.getMaxIndex() + 1L;
            slide.setIndex(newIndex);
        }
        slideDao.save(slide);
        if (is != null) {
            blobRepository.store(BlobDataType.SLIDE, slide.getId(), is);
            fileCacheManager.reset(getCachePath(slide.getId()));
        }
    }

    @Override
    public void save(Slide slide) throws IOException {
        save(slide, null);
    }

    private Long getMaxIndex() {
        return slideDao.getMaxIndex();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return slideDao.countByFilter(createKeywordFilter(keyword));
    }

    private Filter createKeywordFilter(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.OR);
        filter.add(new ValueFilter("title", QueryOperator.LIKE, keyword, "title"));
        filter.add(new ValueFilter("description", QueryOperator.LIKE, keyword, "description"));
        return filter;
    }

    @Override
    public List<Slide> findAlls(Long start, Long count) {
        return slideDao.findAlls(start, count, "id", OrderDir.DESC);
    }

    @Override
    public List<Slide> findByKeyword(String keyword, Long start, Long count) {
        return slideDao.findByFilter(createKeywordFilter(keyword), start, count, "id", OrderDir.DESC);
    }

    @Override
    public void getBlob(Long id, OutputStream os) throws IOException {
        getBlob(id, null, null, os);
    }

    @Override
    public void getBlob(Long id, Integer w, Integer h, OutputStream os) throws IOException {
        if (w == null && h == null) {
            blobRepository.copyContent(BlobDataType.SLIDE, id, os);
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
            File tempFile = blobRepository.getTempFile(BlobDataType.SLIDE, id);
            if (tempFile != null) {
                InputStream is = new FileInputStream(tempFile);
                Slide slide = slideDao.findById(id);
                try {
                    BufferedImage resized = imageUtils.resizeImage(is, w, h);
                    String format = StringUtils.lowerCase(FilenameUtils.getExtension(slide.getFilename()));
                    ImageIO.write(resized, format, os);
                    storeImageToFileCache(path, key, resized, format);
                } finally {
                    StreamHelper.closeQuiet(is);
                    FileUtils.deleteQuietly(tempFile);
                }
            }
        }
    }

    @Override
    public void removeByIds(Long[] ids) throws IOException {
        for (Long id : ids) {
            slideDao.removeById(id);
            blobRepository.remove(BlobDataType.SLIDE, id);
            fileCacheManager.reset(getCachePath(id));
        }
    }

    @Override
    public void publishByIds(Long[] ids) {
        for (Long id : ids) {
            Slide slide = slideDao.findById(id);
            slide.setPublished(true);
            slideDao.save(slide);
        }
    }

    @Override
    public void unpublishByIds(Long[] ids) {
        for (Long id : ids) {
            Slide slide = slideDao.findById(id);
            slide.setPublished(false);
            slideDao.save(slide);
        }
    }

    private String getCacheKey(Integer w, Integer h) {
        return String.format("%d_%d", w, h);
    }

    private String getCachePath(Long id) {
        return String.format("%s/%d", Slide.class.getSimpleName(), id);
    }

    private void storeImageToFileCache(String path, String key, BufferedImage resized, String format) throws IOException {
        ByteArrayOutputStream os = null;
        InputStream is = null;
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
}
