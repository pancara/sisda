package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Ticker;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 11/29/12
 * Time       : 2:55 PM
 */
public interface TickerService {
    List<Ticker> findAlls(Long start, Long count);

    List<Ticker> findByKeyword(String keyword, Long start, Long count);

    Long countAlls();

    Long countByKeyword(String keyword);

    Ticker findById(Long id);

    void save(Ticker ticker);

    void removeByIds(Long[] ids);

    void publishByIds(Long[] ids);

    void unpublishByIds(Long[] ids);

    List<Ticker> findPublished(Long start, Long count);
}
