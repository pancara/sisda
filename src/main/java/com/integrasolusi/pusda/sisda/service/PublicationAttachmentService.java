package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Publication;
import com.integrasolusi.pusda.sisda.persistence.PublicationAttachment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/17/11
 * Time         : 11:24 PM
 */
public interface PublicationAttachmentService {
    void save(PublicationAttachment attachment, InputStream is) throws IOException;

    void removeById(Long id);

    List<PublicationAttachment> findByPublication(Publication publication);

    PublicationAttachment findById(Long id);

    void getBlob(Long id, OutputStream outputStream) throws IOException;
}
