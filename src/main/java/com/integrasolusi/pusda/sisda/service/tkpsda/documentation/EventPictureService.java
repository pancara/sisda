package com.integrasolusi.pusda.sisda.service.tkpsda.documentation;

import com.integrasolusi.pusda.sisda.persistence.tkpsda.documentation.Event;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.documentation.EventPicture;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 11/12/12
 * Time       : 3:50 PM
 */
public interface EventPictureService {
    EventPicture findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;
    
    void getBlob(Long id, Integer w, Integer h, OutputStream os) throws IOException;

    void save(EventPicture pic);

    void save(EventPicture pic, InputStream inputStream) throws IOException;

    void removeByIds(Long[] ids) throws IOException;

    List<EventPicture> findByEvent(Event event);
}
