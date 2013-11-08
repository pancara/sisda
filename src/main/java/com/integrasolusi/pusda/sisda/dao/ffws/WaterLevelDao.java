package com.integrasolusi.pusda.sisda.dao.ffws;

import com.integrasolusi.pusda.sisda.persistence.ffws.Station;
import com.integrasolusi.pusda.sisda.persistence.ffws.WaterLevel;
import com.integrasolusi.query.generic.GenericDao;

import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 6/12/13
 * Time       : 12:47 PM
 */
public interface WaterLevelDao extends GenericDao<WaterLevel, Long> {
    Date getLastSamplingAt(Station station);

    WaterLevel getLatest(Station station);

}
