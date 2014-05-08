package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.CommentPublicationDao;
import com.integrasolusi.pusda.sisda.dao.PublicationAttachmentDao;
import com.integrasolusi.pusda.sisda.dao.PublicationDao;
import com.integrasolusi.pusda.sisda.persistence.CommentPublication;
import com.integrasolusi.pusda.sisda.persistence.Publication;
import com.integrasolusi.pusda.sisda.persistence.PublicationAttachment;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.utils.ImageUtils;
import com.integrasolusi.utils.StreamHelper;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 5:02 PM
 */
public class PublicationServiceImpl implements PublicationService {
    private PublicationDao publicationDao;
    private PublicationAttachmentDao publicationAttachmentDao;
    private CommentPublicationDao commentPublicationDao;
    private BlobRepository blobRepository;
    private ImageUtils imageUtils;


    public void setPublicationDao(PublicationDao publicationDao) {
        this.publicationDao = publicationDao;
    }

    public void setPublicationAttachmentDao(PublicationAttachmentDao publicationAttachmentDao) {
        this.publicationAttachmentDao = publicationAttachmentDao;
    }

    public void setCommentPublicationDao(CommentPublicationDao commentPublicationDao) {
        this.commentPublicationDao = commentPublicationDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    public void setImageUtils(ImageUtils imageUtils) {
        this.imageUtils = imageUtils;
    }

    @Override
    public Long countAlls() {
        return publicationDao.countAlls();
    }

    @Override
    public Long countPublished() {
        Filter filter = createPublishedFilter();
        return publicationDao.countByFilter(filter);
    }

    private Filter createPublishedFilter() {
        return new ValueFilter("published", QueryOperator.EQUALS, true, "published");
    }

    @Override
    public Long countPublishedByKeyword(String keyword) {
        CompositeFilter filter = createPublishedAndKeywordFilter(keyword);
        return publicationDao.countByFilter(filter);
    }

    @Override
    public Long countByKeyword(String keyword) {
        CompositeFilter filter = createKeywordFilter(keyword);
        return publicationDao.countByFilter(filter);
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
    public List<Publication> get(Long start, Long count) {
        return publicationDao.findAlls(start, count, "publishedDate", OrderDir.DESC);
    }

    @Override
    public List<Publication> getPublished(Long start, Long count) {
        return publicationDao.findByFilter(createPublishedFilter(), start, count, "publishedDate", OrderDir.DESC);
    }

    @Override
    public List<Publication> getPublishedByKeyword(String keyword, Long start, Long count) {
        CompositeFilter filter = createPublishedAndKeywordFilter(keyword);
        return publicationDao.findByFilter(filter, start, count, "publishedDate", OrderDir.DESC);
    }

    private CompositeFilter createPublishedAndKeywordFilter(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(createPublishedFilter());
        filter.add(createKeywordFilter(keyword));
        return filter;
    }

    @Override
    public List<Publication> getByKeyword(String keyword, Long start, Long count) {
        CompositeFilter filter = createKeywordFilter(keyword);
        return publicationDao.findByFilter(filter, start, count, "publishedDate", OrderDir.DESC);
    }

    @Override
    public void save(Publication publication) {
        publicationDao.save(publication);
    }

    @Override
    public Publication findById(Long id) {
        return publicationDao.findById(id);
    }

    @Override
    public void removeById(Long id) {
        ValueFilter filter = new ValueFilter("publication", QueryOperator.EQUALS, publicationDao.findById(id));
        List<Long> documentIds = new LinkedList<Long>();
        for (PublicationAttachment d : publicationAttachmentDao.findByFilter(filter)) {
            documentIds.add(d.getId());
        }
        publicationAttachmentDao.removeByFilter(filter);
        publicationDao.removeById(id);

        blobRepository.remove(BlobDataType.PUBLICATION_PICTURE, id);

        for (Long documentId : documentIds) {
            blobRepository.remove(BlobDataType.PUBLICATION_ATTACHMENT, documentId);
        }
    }

    @Override
    public Boolean hasTitlePicture(Long id) {
        return blobRepository.isExist(BlobDataType.PUBLICATION_PICTURE, id);
    }

    @Override
    public void savePicture(Long id, InputStream is) throws IOException {
        blobRepository.store(BlobDataType.PUBLICATION_PICTURE, id, is);
    }

    @Override
    public void saveComment(CommentPublication comment) {
        commentPublicationDao.save(comment);
    }

    @Override
    public List<CommentPublication> getComments(Long publicationId) {
        Publication publication = publicationDao.loadByID(publicationId);
        return commentPublicationDao.findByProperty("publication", publication, "id", OrderDir.ASC);
    }

    @Override
    public List<Publication> getLatest() {
        return getLatest(8L);
    }

    @Override
    public List<Publication> getLatest(Long count) {
        Filter filter = new ValueFilter("published", QueryOperator.EQUALS, true, "published");
        return publicationDao.findByFilter(filter, 0L, count, "publishedDate", OrderDir.DESC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            removeById(id);
        }
    }

    @Override
    public void save(Publication publication, InputStream inputStream) throws IOException {
        publicationDao.save(publication);
        blobRepository.store(BlobDataType.PUBLICATION_PICTURE, publication.getId(), inputStream);
    }

    @Override
    public void publishByIds(Long[] ids) {
        for (Long id : ids) {
            Publication publication = publicationDao.findById(id);
            if (publication != null) {
                publication.setPublished(true);
                publicationDao.save(publication);
            }
        }
    }

    @Override
    public void unpublishByIds(Long[] ids) {
        for (Long id : ids) {
            Publication publication = publicationDao.findById(id);
            if (publication != null) {
                publication.setPublished(false);
                publicationDao.save(publication);
            }
        }
    }

    @Override
    public void removePicture(Long id) {
        Publication publication = publicationDao.findById(id);
        publication.setPicture(null);
        publicationDao.save(publication);
        blobRepository.remove(BlobDataType.PUBLICATION_PICTURE, id);
    }

    @Override
    public void getPicture(Long id, OutputStream outputStream) throws Exception {
        blobRepository.copyContent(BlobDataType.PUBLICATION_PICTURE, id, outputStream);
    }

    @Override
    public void getPicture(Long id, OutputStream outputStream, Integer width, Integer height) throws Exception {
        Publication publication = publicationDao.findById(id);
        if (publication == null)
            return;
        String format = StringUtils.lowerCase(FilenameUtils.getExtension(publication.getPicture()));

        File temp = blobRepository.getTempFile(BlobDataType.PUBLICATION_PICTURE, id);
        InputStream is = new FileInputStream(temp);
        try {
            BufferedImage image = imageUtils.resizeImage(is, width, height);
            imageUtils.writeImage(image, format, outputStream);
        } finally {
            StreamHelper.closeQuiet(is);
            temp.delete();
        }
    }
}
