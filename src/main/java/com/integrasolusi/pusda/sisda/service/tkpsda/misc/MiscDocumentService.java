package com.integrasolusi.pusda.sisda.service.tkpsda.misc;

import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscDocument;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscFolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 7/28/13
 * Time       : 6:31 PM
 */
public interface MiscDocumentService {

    List<MiscDocument> findByFolder(MiscFolder folder);

    MiscDocument findById(Long id);

    void save(MiscDocument document);

    void save(MiscDocument document, InputStream is) throws IOException;

    void removeByIds(Long[] ids);

    void getBlob(Long documentId, OutputStream os) throws IOException;
}
