package com.integrasolusi.pusda.sisda.service.tkpsda;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.Schedule;

import java.util.List;

/**
 * User: pancara
 * Date: 9/3/12
 * Time: 9:54 AM
 */
public interface ScheduleService {
    
    List<Schedule> findByWilayahSungaiAndStatus(WilayahSungai ws, Boolean status);

    Long countAlls();

    Long countByKeyword(String keyword);

    Long countByWilayahSungai(Long wilayahSungai);

    Long countByWilayahSungaiAndKeyword(Long wilayahSungai, String keyword);

    List<Schedule> findAlls(Long start, Long count);

    List<Schedule> findByKeyword(String keyword, Long start, Long count);

    List<Schedule> findByWilayahSungai(Long wilayahSungai, Long start, Long count);

    List<Schedule> findByWilayahSungaiAndKeyword(Long wilayahSungai, String keyword, Long start, Long count);

    Schedule findById(Long id);

    void save(Schedule schedule);

    void removeByIds(Long[] ids);

    void publishByIds(Long[] ids);

    void unpublishByIds(Long[] ids);

    List<Schedule> findByWilayahSungaiAndYear(WilayahSungai ws, Year year);
}
