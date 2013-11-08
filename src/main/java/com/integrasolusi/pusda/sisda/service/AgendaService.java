package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Agenda;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/24/11
 * Time         : 5:02 PM
 */
public interface AgendaService {

    List<Agenda> getNearest(Date date);

    List<Agenda> getByHoldDate(Date date, Long start, Long count);

    Long countByHoldDate(Date date);

    Long countPublished();

    Long countPublishedByKeyword(String keyword);

    List<Agenda> getPublished(Long start, Long count);

    List<Agenda> getPublishedByKeyword(String keyword, Long start, Long count);

    Agenda findById(Long id);

    List<Agenda> get(Long start, Long count);

    List<Agenda> getByKeyword(String keyword, Long start, Long count);

    Long countAlls();

    Long countByKeyword(String keyword);

    void save(Agenda agenda);
    
    void save(Agenda agenda, InputStream inputStream) throws IOException;

    void removeById(Long id);

    void removeByIds(Long[] ids);

    void publishByIds(Long[] ids);

    void unpublishByIds(Long[] ids);

    void removeThumb(Long id);

    void getThumbBlob(Long id, OutputStream outputStream) throws IOException;
}
