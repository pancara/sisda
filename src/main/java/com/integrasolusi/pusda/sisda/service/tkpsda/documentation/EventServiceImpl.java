package com.integrasolusi.pusda.sisda.service.tkpsda.documentation;

import com.integrasolusi.pusda.sisda.dao.tkpsda.documentation.EventDao;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.documentation.Event;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 11/12/12
 * Time       : 3:35 PM
 */
public class EventServiceImpl implements EventService {
    private EventDao eventDao;

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public List<Event> findByWilayahSungaiAndActive(WilayahSungai ws) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("wilayahSungai", QueryOperator.EQUALS, ws, "wilayahSungai"));
        filter.add(new ValueFilter("active", QueryOperator.EQUALS, true, "active"));
        return eventDao.findByFilter(filter, "index", OrderDir.ASC);
    }

    @Override
    public Long countAlls() {
        return eventDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return eventDao.countByFilter(createKeywordFilter(keyword));
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("title", QueryOperator.LIKE, keyword, "title");
    }

    @Override
    public Long countByWilayahSungai(Long wilayahSungai) {
        return eventDao.countByFilter(createWilayahSungaiFilter(wilayahSungai));
    }

    private Filter createWilayahSungaiFilter(Long wilayahSungai) {
        return new ValueFilter("wilayahSungai.id", QueryOperator.EQUALS, wilayahSungai, "wilayahSungai_id");
    }

    @Override
    public Long countByWilayahSungaiAndKeyword(Long wilayahSungai, String keyword) {
        return eventDao.countByFilter(createWilayahSungaiAndKeywordFilter(wilayahSungai, keyword));
    }

    private CompositeFilter createWilayahSungaiAndKeywordFilter(Long wilayahSungai, String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(createWilayahSungaiFilter(wilayahSungai));
        filter.add(createKeywordFilter(keyword));
        return filter;
    }

    @Override
    public List<Event> findAlls(Long start, Long count) {
        return eventDao.findAlls(start, count, "title", OrderDir.DESC);
    }

    @Override
    public List<Event> findByKeyword(String keyword, Long start, Long count) {
        return eventDao.findByFilter(createKeywordFilter(keyword), start, count, "title", OrderDir.DESC);
    }

    @Override
    public List<Event> findByWilayahSungai(Long wilayahSungai, Long start, Long count) {
        return eventDao.findByFilter(createWilayahSungaiFilter(wilayahSungai), start, count, "title", OrderDir.DESC);
    }

    @Override
    public List<Event> findByWilayahSungaiAndKeyword(Long wilayahSungai, String keyword, Long start, Long count) {
        return eventDao.findByFilter(createWilayahSungaiAndKeywordFilter(wilayahSungai, keyword), start, count, "title", OrderDir.DESC);
    }

    @Override
    public Event findById(Long id) {
        return eventDao.findById(id);
    }

    @Override
    public void save(Event Dokumentasi) {
        eventDao.save(Dokumentasi);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            eventDao.removeById(id);
        }
    }

    @Override
    public void publishByIds(Long[] ids) {
        for (Long id : ids) {
            Event Dokumentasi = eventDao.findById(id);
            Dokumentasi.setActive(true);
            eventDao.save(Dokumentasi);
        }
    }

    @Override
    public void unpublishByIds(Long[] ids) {
        for (Long id : ids) {
            Event Dokumentasi = eventDao.findById(id);
            Dokumentasi.setActive(false);
            eventDao.save(Dokumentasi);
        }
    }

    @Override
    public List<Event> findByWilayahSungaiAndYear(WilayahSungai ws, Year year) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);

        filter.add(new ValueFilter("wilayahSungai", QueryOperator.EQUALS, ws, "wilayahSungai"));
        filter.add(new ValueFilter("year", QueryOperator.EQUALS, year, "year"));

        return eventDao.findByFilter(filter, "date", OrderDir.DESC);
    }

    @Override
    public List<Event> findByWilayahSungaiAndYearAndActive(WilayahSungai ws, Year year, boolean active) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);

        filter.add(new ValueFilter("wilayahSungai", QueryOperator.EQUALS, ws, "wilayahSungai"));
        filter.add(new ValueFilter("year", QueryOperator.EQUALS, year, "event_year"));
        filter.add(new ValueFilter("active", QueryOperator.EQUALS, active, "active"));

        return eventDao.findByFilter(filter, "date", OrderDir.DESC);
    }
}
