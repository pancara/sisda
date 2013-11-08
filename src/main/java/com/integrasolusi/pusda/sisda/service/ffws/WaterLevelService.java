package com.integrasolusi.pusda.sisda.service.ffws;

import com.integrasolusi.pusda.sisda.persistence.ffws.Station;
import com.integrasolusi.pusda.sisda.persistence.ffws.WaterLevel;

import java.util.Date;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 6/12/13
 * Time       : 3:11 PM
 */
public interface WaterLevelService {
    void save(WaterLevel waterLevel);

    Date getLastSamplingAt(Station station);

    WaterLevel getLatestByStation(Station station);

    List<WaterLevel> getLatest(int count);

    List<WaterLevel> findByStation(Station station, Long start, Long count);
}
