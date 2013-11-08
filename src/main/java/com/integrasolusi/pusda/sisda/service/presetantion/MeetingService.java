package com.integrasolusi.pusda.sisda.service.presetantion;

import com.integrasolusi.pusda.sisda.persistence.presentation.Meeting;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 7/23/13
 * Time       : 11:16 AM
 */
public interface MeetingService {
    Long countAlls();

    Long countByKeyword(String keyword);

    List<Meeting> findAlls(Long start, Long count);

    List<Meeting> findByKeyword(String keywordString, Long start, Long count);

    void save(Meeting meeting);

    Meeting findById(Long id);

    void removeByIds(Long[] ids);

    List<Meeting> findAlls();

    List<Meeting> getLatest(Long count);
}
