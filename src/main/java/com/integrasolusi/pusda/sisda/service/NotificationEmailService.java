package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.NotificationEmail;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 4/15/13
 * Time       : 9:32 PM
 */
public interface NotificationEmailService {

    NotificationEmail findById(Long id);

    List<NotificationEmail> findAlls();

    Long countAlls();

    void save(NotificationEmail email);

    void removeByIds(Long[] ids);
}
