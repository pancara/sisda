package com.integrasolusi.pusda.sisda.service.tkpsda.documentation;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.documentation.Event;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 11/12/12
 * Time       : 3:34 PM
 */
public interface EventService {
    List<Event> findByWilayahSungaiAndActive(WilayahSungai ws);

    Event findById(Long id);

    Long countAlls();

    Long countByKeyword(String keyword);

    Long countByWilayahSungai(Long wilayahSungai);

    Long countByWilayahSungaiAndKeyword(Long wilayahSungai, String keyword);

    List<Event> findAlls(Long start, Long count);

    List<Event> findByKeyword(String keyword, Long start, Long count);

    List<Event> findByWilayahSungai(Long wilayahSungai, Long start, Long count);

    List<Event> findByWilayahSungaiAndKeyword(Long wilayahSungai, String keyword, Long start, Long count);

    void save(Event dokumentasi);

    void removeByIds(Long[] ids);

    void publishByIds(Long[] ids);

    void unpublishByIds(Long[] ids);

    List<Event> findByWilayahSungaiAndYear(WilayahSungai ws, Year year);

    List<Event> findByWilayahSungaiAndYearAndActive(WilayahSungai ws, Year year, boolean active);
}
