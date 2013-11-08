package com.integrasolusi.pusda.sisda.service.presetantion;

import com.integrasolusi.pusda.sisda.dao.presentation.MeetingDao;
import com.integrasolusi.pusda.sisda.persistence.presentation.Meeting;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 7/23/13
 * Time       : 11:17 AM
 */
public class MeetingServiceImpl implements MeetingService {
    private MeetingDao meetingDao;

    public void setMeetingDao(MeetingDao meetingDao) {
        this.meetingDao = meetingDao;
    }

    @Override
    public Long countAlls() {
        return meetingDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return meetingDao.countByFilter(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"));
    }

    @Override
    public List<Meeting> findAlls(Long start, Long count) {
        return meetingDao.findAlls(start, count, "id", OrderDir.DESC);
    }

    @Override
    public List<Meeting> findByKeyword(String keyword, Long start, Long count) {
        return meetingDao.findByFilter(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"), start, count, "id", OrderDir.DESC);
    }

    @Override
    public void save(Meeting meeting) {
        meetingDao.save(meeting);
    }

    @Override
    public Meeting findById(Long id) {
        return meetingDao.findById(id);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            meetingDao.removeById(id);
        }
    }

    @Override
    public List<Meeting> findAlls() {
        return meetingDao.findAlls("name", OrderDir.ASC);
    }

    @Override
    public List<Meeting> getLatest(Long count) {
        return meetingDao.findAlls(0L, count, "date", OrderDir.DESC);
    }
}
