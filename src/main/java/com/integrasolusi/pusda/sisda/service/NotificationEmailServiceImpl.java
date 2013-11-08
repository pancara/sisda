package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.NotificationEmailDao;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.NotificationEmail;

import java.util.List;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 2:54 PM
 */
public class NotificationEmailServiceImpl implements NotificationEmailService {
    private NotificationEmailDao notificationEmailDao;

    public void setNotificationEmailDao(NotificationEmailDao notificationEmailDao) {
        this.notificationEmailDao = notificationEmailDao;
    }

    @Override
    public NotificationEmail findById(Long id) {
        return notificationEmailDao.findById(id);
    }

    @Override
    public List<NotificationEmail> findAlls() {
        return notificationEmailDao.findAlls("address", OrderDir.ASC);
    }

    @Override
    public Long countAlls() {
        return notificationEmailDao.countAlls();
    }

    @Override
    public void save(NotificationEmail email) {
        notificationEmailDao.save(email);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            notificationEmailDao.removeById(id);
        }
    }

}
