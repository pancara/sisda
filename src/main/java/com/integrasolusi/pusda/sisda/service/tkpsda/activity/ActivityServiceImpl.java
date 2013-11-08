package com.integrasolusi.pusda.sisda.service.tkpsda.activity;

import com.integrasolusi.pusda.sisda.dao.tkpsda.meeting.ActivityDao;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.Activity;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 7/28/13
 * Time       : 6:29 PM
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao;

    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    @Override
    public List<Activity> findByWilayahSungaiAndYear(WilayahSungai ws, Year year) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("ws", QueryOperator.EQUALS, ws, "ws"));
        filter.add(new ValueFilter("year", QueryOperator.EQUALS, year, "year"));

        return activityDao.findByFilter(filter, "date", OrderDir.ASC);
    }

    @Override
    public Activity findById(Long id) {
        return activityDao.findById(id);
    }

    @Override
    public void save(Activity activity) {
        activityDao.save(activity);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            activityDao.removeById(id);
        }
    }
}
