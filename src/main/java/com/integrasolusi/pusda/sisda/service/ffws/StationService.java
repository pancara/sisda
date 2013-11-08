package com.integrasolusi.pusda.sisda.service.ffws;

import com.integrasolusi.pusda.sisda.persistence.ffws.Station;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 6/12/13
 * Time       : 3:02 PM
 */
public interface StationService {
    List<Station> findAlls();

    List<Station> findAlls(Long start, Long count);

    Station findById(Long stationId);

    void save(Station station);

    void removeByIds(Long[] ids);
}
