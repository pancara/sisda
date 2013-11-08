package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.AgendaDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.Agenda;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/27/11
 * Time         : 9:30 AM
 */
public class AgendaServiceImpl implements AgendaService {
    private AgendaDao agendaDao;
    private BlobRepository blobRepository;

    public void setAgendaDao(AgendaDao agendaDao) {
        this.agendaDao = agendaDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public List<Agenda> getNearest(Date holdDate) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("holdDate", QueryOperator.GREATER_OR_EQUALS, holdDate, "holdDate"));
        filter.add(new ValueFilter("published", QueryOperator.EQUALS, true, "published"));
        return agendaDao.findByFilter(filter, 0L, 8L, "holdDate", OrderDir.ASC);
    }

    @Override
    public Long countByHoldDate(Date holdDate) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("holdDate", QueryOperator.EQUALS, holdDate, "holdDate"));
        filter.add(new ValueFilter("published", QueryOperator.EQUALS, true, "published"));
        return agendaDao.countByFilter(filter);
    }

    @Override
    public List<Agenda> getByHoldDate(Date holdDate, Long start, Long count) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("holdDate", QueryOperator.EQUALS, holdDate, "holdDate"));
        filter.add(new ValueFilter("published", QueryOperator.EQUALS, true, "published"));
        return agendaDao.findByFilter(filter, start, count, "holdDate", OrderDir.ASC);
    }

    @Override
    public Long countPublished() {
        Filter filter = new ValueFilter("published", QueryOperator.EQUALS, true, "published");
        return agendaDao.countByFilter(filter);
    }

    @Override
    public Long countPublishedByKeyword(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("keyword", QueryOperator.LIKE, "%" + keyword + "%", "keyword"));
        filter.add(new ValueFilter("published", QueryOperator.EQUALS, true, "published"));
        return agendaDao.countByFilter(filter);
    }

    @Override
    public List<Agenda> getPublished(Long start, Long count) {
        Filter filter = new ValueFilter("published", QueryOperator.EQUALS, true, "published");
        return agendaDao.findByFilter(filter, start, count, "holdDate", OrderDir.DESC);
    }

    @Override
    public List<Agenda> getPublishedByKeyword(String keyword, Long start, Long count) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("keyword", QueryOperator.LIKE, "%" + keyword + "%", "keyword"));
        filter.add(new ValueFilter("published", QueryOperator.EQUALS, true, "published"));
        return agendaDao.findByFilter(filter, start, count, "holdDate", OrderDir.DESC);
    }

    @Override
    public Agenda findById(Long id) {
        return agendaDao.findById(id);
    }

    @Override
    public List<Agenda> get(Long start, Long count) {
        return agendaDao.findAlls(start, count);
    }

    @Override
    public List<Agenda> getByKeyword(String keyword, Long start, Long count) {
        CompositeFilter filter = createKeywordFilter(keyword);
        return agendaDao.findByFilter(filter, start, count);
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
    public Long countAlls() {
        return agendaDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return agendaDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public void save(Agenda agenda) {
        agendaDao.save(agenda);
    }

    @Override
    public void removeById(Long id) {
        agendaDao.removeById(id);
        blobRepository.remove(BlobDataType.AGENDA_THUMB, id);
    }

    @Override
    public void save(Agenda agenda, InputStream inputStream) throws IOException {
        agendaDao.save(agenda);
        blobRepository.store(BlobDataType.AGENDA_THUMB, agenda.getId(), inputStream);
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
            Agenda agenda = agendaDao.findById(id);
            if (agenda != null) {
                agenda.setPublished(true);
                agendaDao.save(agenda);
            }
        }
    }

    @Override
    public void unpublishByIds(Long[] ids) {
        for (Long id : ids) {
            Agenda agenda = agendaDao.findById(id);
            if (agenda != null) {
                agenda.setPublished(false);
                agendaDao.save(agenda);
            }
        }
    }

    @Override
    public void removeThumb(Long id) {
        Agenda agenda = agendaDao.findById(id);
        agenda.setThumbFilename(null);
        agendaDao.save(agenda);
        blobRepository.remove(BlobDataType.AGENDA_THUMB, id);
    }

    @Override
    public void getThumbBlob(Long id, OutputStream outputStream) throws IOException {
        blobRepository.copyContent(BlobDataType.AGENDA_THUMB, id, outputStream);
    }
}

