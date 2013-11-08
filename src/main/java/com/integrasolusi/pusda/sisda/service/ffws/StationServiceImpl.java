package com.integrasolusi.pusda.sisda.service.ffws;

import com.integrasolusi.pusda.sisda.dao.ffws.StationDao;
import com.integrasolusi.pusda.sisda.persistence.ffws.Station;
import com.integrasolusi.query.util.OrderDir;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 6/12/13
 * Time       : 3:02 PM
 */
public class StationServiceImpl implements StationService {
    private StationDao stationDao;

    public void setStationDao(StationDao stationDao) {
        this.stationDao = stationDao;
    }


    @Override
    public List<Station> findAlls() {
        return stationDao.findAlls("name");
    }

    @Override
    public List<Station> findAlls(Long start, Long count) {
        return stationDao.findAlls(start, count, "name", OrderDir.ASC);
    }

    @Override
    public Station findById(Long stationId) {
        return stationDao.findById(stationId);
    }

    @Override
    public void save(Station station) {
        stationDao.save(station);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            stationDao.removeById(id);
        }
    }
}
