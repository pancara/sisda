package com.integrasolusi.pusda.sisda.service.tkpsda;

import com.integrasolusi.pusda.sisda.dao.tkpsda.ScheduleDao;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.Schedule;

import java.util.List;

/**
 * User: pancara
 * Date: 9/3/12
 * Time: 9:54 AM
 */
public class ScheduleServiceImpl implements ScheduleService {
    private ScheduleDao scheduleDao;

    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    @Override
    public List<Schedule> findByWilayahSungaiAndStatus(WilayahSungai ws, Boolean status) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("wilayahSungai", QueryOperator.EQUALS, ws, "wilayahSungai"));
        filter.add(new ValueFilter("active", QueryOperator.EQUALS, status, "active"));
        return scheduleDao.findByFilter(filter, "holdDate", OrderDir.ASC);
    }


    @Override
    public Long countAlls() {
        return scheduleDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return scheduleDao.countByFilter(createKeywordFilter(keyword));
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("title", QueryOperator.LIKE, keyword, "title");
    }

    @Override
    public Long countByWilayahSungai(Long wilayahSungai) {
        return scheduleDao.countByFilter(createWilayahSungaiFilter(wilayahSungai));
    }

    private Filter createWilayahSungaiFilter(Long wilayahSungai) {
        return new ValueFilter("wilayahSungai.id", QueryOperator.EQUALS, wilayahSungai, "wilayahSungai_id");
    }

    @Override
    public Long countByWilayahSungaiAndKeyword(Long wilayahSungai, String keyword) {
        return scheduleDao.countByFilter(createWilayahSungaiAndKeywordFilter(wilayahSungai, keyword));
    }

    private CompositeFilter createWilayahSungaiAndKeywordFilter(Long wilayahSungai, String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(createWilayahSungaiFilter(wilayahSungai));
        filter.add(createKeywordFilter(keyword));
        return filter;
    }

    @Override
    public List<Schedule> findAlls(Long start, Long count) {
        return scheduleDao.findAlls(start, count, "holdDate", OrderDir.DESC);
    }

    @Override
    public List<Schedule> findByKeyword(String keyword, Long start, Long count) {
        return scheduleDao.findByFilter(createKeywordFilter(keyword), start, count, "holdDate", OrderDir.DESC);
    }

    @Override
    public List<Schedule> findByWilayahSungai(Long wilayahSungai, Long start, Long count) {
        return scheduleDao.findByFilter(createWilayahSungaiFilter(wilayahSungai), start, count, "holdDate", OrderDir.DESC);
    }

    @Override
    public List<Schedule> findByWilayahSungaiAndKeyword(Long wilayahSungai, String keyword, Long start, Long count) {
        return scheduleDao.findByFilter(createWilayahSungaiAndKeywordFilter(wilayahSungai, keyword), start, count, "holdDate", OrderDir.DESC);
    }

    @Override
    public Schedule findById(Long id) {
        return scheduleDao.findById(id);
    }

    @Override
    public void save(Schedule schedule) {
        scheduleDao.save(schedule);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            scheduleDao.removeById(id);
        }
    }

    @Override
    public void publishByIds(Long[] ids) {
        for (Long id : ids) {
            Schedule schedule = scheduleDao.findById(id);
            schedule.setActive(true);
            scheduleDao.save(schedule);
        }
    }

    @Override
    public void unpublishByIds(Long[] ids) {
        for (Long id : ids) {
            Schedule schedule = scheduleDao.findById(id);
            schedule.setActive(false);
            scheduleDao.save(schedule);
        }
    }

    @Override
    public List<Schedule> findByWilayahSungaiAndYear(WilayahSungai ws, Year year) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("wilayahSungai", QueryOperator.EQUALS, ws, "wilayahSungai"));
        filter.add(new ValueFilter("year", QueryOperator.EQUALS, year, "year"));
        return scheduleDao.findByFilter(filter, "holdDate", OrderDir.ASC);
    }
}
