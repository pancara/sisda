package com.integrasolusi.pusda.sisda.service.tkpsda.activity;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.Activity;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 7/28/13
 * Time       : 6:29 PM
 */
public interface ActivityService {
    
    List<Activity> findByWilayahSungaiAndYear(WilayahSungai ws, Year year);

    Activity findById(Long id);

    void save(Activity activity);

    void removeByIds(Long[] ids);
}
