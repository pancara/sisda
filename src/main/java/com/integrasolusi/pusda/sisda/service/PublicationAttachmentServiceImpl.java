package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.PublicationAttachmentDao;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.pusda.sisda.persistence.Publication;
import com.integrasolusi.pusda.sisda.persistence.PublicationAttachment;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/17/11
 * Time         : 11:24 PM
 */
public class PublicationAttachmentServiceImpl implements PublicationAttachmentService {
    private PublicationAttachmentDao publicationAttachmentDao;
    private BlobRepository blobRepository;

    public void setPublicationAttachmentDao(PublicationAttachmentDao publicationAttachmentDao) {
        this.publicationAttachmentDao = publicationAttachmentDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public void save(PublicationAttachment attachment, InputStream is) throws IOException {
        if (attachment.getId() == null) {
            Integer maxIndex = publicationAttachmentDao.getMaxIndexByPublication(attachment.getPublication());
            Integer newIndex = maxIndex == null ?
                    1 :
                    maxIndex + 1;
            attachment.setIndex(newIndex);
        }
        publicationAttachmentDao.save(attachment);
        if (is != null) {
            blobRepository.store(BlobDataType.PUBLICATION_ATTACHMENT, attachment.getId(), is);
        }
    }

    @Override
    public void removeById(Long id) {
        publicationAttachmentDao.removeById(id);
        blobRepository.remove(BlobDataType.PUBLICATION_ATTACHMENT, id);
    }

    @Override
    public List<PublicationAttachment> findByPublication(Publication publication) {
        return publicationAttachmentDao.findByFilter(new ValueFilter("publication", QueryOperator.EQUALS, publication));
    }

    @Override
    public PublicationAttachment findById(Long id) {
        return publicationAttachmentDao.findById(id);
    }

    @Override
    public void getBlob(Long id, OutputStream outputStream) throws IOException {
        blobRepository.copyContent(BlobDataType.PUBLICATION_ATTACHMENT, id, outputStream);
    }
}
