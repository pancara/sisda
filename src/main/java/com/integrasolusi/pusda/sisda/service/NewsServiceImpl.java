package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.CommentNewsDao;
import com.integrasolusi.pusda.sisda.dao.NewsDao;
import com.integrasolusi.pusda.sisda.persistence.CommentNews;
import com.integrasolusi.pusda.sisda.persistence.News;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.utils.FileCacheManager;
import com.integrasolusi.utils.ImageUtils;
import com.integrasolusi.utils.StreamHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 5:02 PM
 */
public class NewsServiceImpl implements NewsService {
    private NewsDao newsDao;
    private CommentNewsDao commentNewsDao;
    private BlobRepository blobRepository;
    private ImageUtils imageUtils;

    private FileCacheManager fileCacheManager;

    public void setNewsDao(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    public void setCommentNewsDao(CommentNewsDao commentNewsDao) {
        this.commentNewsDao = commentNewsDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    public void setImageUtils(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    public void setFileCacheManager(FileCacheManager fileCacheManager) {
        this.fileCacheManager = fileCacheManager;
    }

    @Override
    public Long countAlls() {
        return newsDao.countAlls();
    }

    @Override
    public Long countPublished() {
        Filter filter = createPublishedFilter();
        return newsDao.countByFilter(filter);
    }

    private Filter createPublishedFilter() {
        return new ValueFilter("published", QueryOperator.EQUALS, true, "published");
    }

    @Override
    public Long countPublishedByKeyword(String keyword) {
        CompositeFilter filter = createPublishedAndKeywordFilter(keyword);
        return newsDao.countByFilter(filter);
    }

    @Override
    public Long countByKeyword(String keyword) {
        CompositeFilter filter = createKeywordFilter(keyword);
        return newsDao.countByFilter(filter);
    }

    private CompositeFilter createKeywordFilter(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.OR);
        String param_value = "%" + keyword + "%";
        filter.add(new ValueFilter("title", QueryOperator.LIKE, param_value, "title"));
        filter.add(new ValueFilter("shortDescription", QueryOperator.LIKE, param_value, "shortDescription"));
        filter.add(new ValueFilter("content", QueryOperator.LIKE, param_value, "content"));
        return filter;
    }

    @Override
    public List<News> get(Long start, Long count) {
        return newsDao.findAlls(start, count, "publishedDate", OrderDir.DESC);
    }

    @Override
    public List<News> getPublished(Long start, Long count) {
        return newsDao.findByFilter(createPublishedFilter(), start, count, "publishedDate", OrderDir.DESC);
    }

    @Override
    public List<News> getPublishedByKeyword(String keyword, Long start, Long count) {
        CompositeFilter filter = createPublishedAndKeywordFilter(keyword);
        return newsDao.findByFilter(filter, start, count, "publishedDate", OrderDir.DESC);
    }

    private CompositeFilter createPublishedAndKeywordFilter(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(createPublishedFilter());
        filter.add(createKeywordFilter(keyword));
        return filter;
    }

    @Override
    public List<News> getByKeyword(String keyword, Long start, Long count) {
        CompositeFilter filter = createKeywordFilter(keyword);
        return newsDao.findByFilter(filter, start, count, "publishedDate", OrderDir.DESC);
    }

    @Override
    public void save(News news) {
        newsDao.save(news);
    }

    @Override
    public void save(News news, InputStream is) throws IOException {
        newsDao.save(news);
        blobRepository.store(BlobDataType.NEWS_PHOTO, news.getId(), is);
    }

    @Override
    public News findById(Long id) {
        return newsDao.findById(id);
    }

    @Override
    public void removeById(Long id) {
        newsDao.removeById(id);
        blobRepository.remove(BlobDataType.NEWS_PHOTO, id);
    }

    @Override
    public Boolean hasPicture(Long id) {
        return blobRepository.isExist(BlobDataType.NEWS_PHOTO, id);
    }

    @Override
    public BufferedImage getTitlePicture(Long id) throws IOException {
        return blobRepository.createImage(BlobDataType.NEWS_PHOTO, id);
    }


    @Override
    public void saveComment(CommentNews comment) {
        commentNewsDao.save(comment);
    }

    @Override
    public List<CommentNews> getComments(Long newsId) {
        News news = newsDao.loadByID(newsId);
        return commentNewsDao.findByProperty("news", news, "id", OrderDir.ASC);
    }

    @Override
    public List<News> getLatest(Long count) {
        Filter filter = new ValueFilter("published", QueryOperator.EQUALS, true, "published");
        return newsDao.findByFilter(filter, 0L, count, "publishedDate", OrderDir.DESC);
    }

    @Override
    public List<News> getLatest() {
        return getLatest(6L);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            removeById(id);
        }
    }

    @Override
    public void publishByIds(Long[] ids) {
        for (Long id : ids) {
            News news = newsDao.findById(id);
            if (news != null) {
                news.setPublished(true);
                newsDao.save(news);
            }
        }
    }

    @Override
    public void unpublishByIds(Long[] ids) {
        for (Long id : ids) {
            if (id != null) {
                News news = newsDao.findById(id);
                news.setPublished(false);
                newsDao.save(news);
            }
        }
    }

    @Override
    public void getHeadlinePictureBlob(Long id, OutputStream outputStream) throws IOException {
        blobRepository.copyContent(BlobDataType.NEWS_PHOTO, id, outputStream);
    }

    @Override
    public void getHeadlinePictureBlob(Long id, OutputStream outputStream, Integer width, Integer height) throws Exception {
        String path = getCachePath(id);
        String key = getCacheKey(width, height);

        News news = newsDao.findById(id);
        String format = StringUtils.lowerCase(FilenameUtils.getExtension(news.getPhotoFilename()));


        if (fileCacheManager.exist(path, key)) {
            InputStream is = fileCacheManager.getStream(path, key);
            try {
                StreamHelper.copy(is, outputStream);
            } finally {
                StreamHelper.closeQuiet(is);
            }
        } else {

            File temp = blobRepository.getTempFile(BlobDataType.NEWS_PHOTO, id);
            InputStream is = new FileInputStream(temp);
            try {
                BufferedImage image = imageUtils.resizeImage(is, width, height);
                imageUtils.writeImage(image, format, outputStream);
                storeImageToFileCache(path, key, image, format);
            } finally {
                StreamHelper.closeQuiet(is);
                FileUtils.deleteQuietly(temp);
            }
        }

    }

    private String getCacheKey(Integer w, Integer h) {
        return String.format("%d_%d", w, h);
    }

    private String getCachePath(Long id) {
        return String.format("%s/%d", News.class.getSimpleName(), id);
    }

    private void storeImageToFileCache(String path, String key, BufferedImage resized, String format) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(resized, format, os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        try {
            fileCacheManager.store(path, key, is);
        } finally {
            StreamHelper.closeQuiet(is);
            StreamHelper.closeQuiet(os);
        }
    }

    @Override
    public void removePhoto(Long id) throws IOException {
        News news = newsDao.findById(id);
        news.setPhotoFilename(null);
        newsDao.save(news);
        blobRepository.remove(BlobDataType.NEWS_PHOTO, id);
        fileCacheManager.reset(getCachePath(id));
    }
}
