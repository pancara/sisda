package com.integrasolusi.pusda.sisda.service.ffws;

import com.integrasolusi.pusda.sisda.dao.ffws.StationDao;
import com.integrasolusi.pusda.sisda.dao.ffws.WaterLevelDao;
import com.integrasolusi.pusda.sisda.persistence.ffws.Station;
import com.integrasolusi.pusda.sisda.persistence.ffws.WaterLevel;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;

import java.util.*;

/**
 * Programmer : pancara
 * Date       : 6/12/13
 * Time       : 3:11 PM
 */
public class WaterLevelServiceImpl implements WaterLevelService {
    private WaterLevelDao waterLevelDao;
    private StationDao stationDao;

    public void setWaterLevelDao(WaterLevelDao waterLevelDao) {
        this.waterLevelDao = waterLevelDao;
    }

    public void setStationDao(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    @Override
    public void save(WaterLevel waterLevel) {
        waterLevelDao.save(waterLevel);
    }

    @Override
    public Date getLastSamplingAt(Station station) {
        return waterLevelDao.getLastSamplingAt(station);
    }

    @Override
    public WaterLevel getLatestByStation(Station station) {
        return waterLevelDao.getLatest(station);
    }

    @Override
    public List<WaterLevel> getLatest(int count) {
        List<Station> stationList = stationDao.findAlls();
        count = stationList.size() < count ? stationList.size() : count;

        List<WaterLevel> levels = new ArrayList<>();
        for (Station station : stationList) {
            WaterLevel w = waterLevelDao.getLatest(station);
            levels.add(w);
        }

        Collections.sort(levels, new Comparator<WaterLevel>() {
            @Override
            public int compare(WaterLevel w1, WaterLevel w2) {
                if (w1 == null) return -1;
                if (w2 == null) return 1;

                if (w1.getSamplingAt().after(w2.getSamplingAt()))
                    return -1;

                if (w1.getSamplingAt().before(w2.getSamplingAt()))
                    return 1;

                return 0;
            }
        });

        return levels.subList(0, count);
    }


    @Override
    public List<WaterLevel> findByStation(Station station, Long start, Long count) {
        return waterLevelDao.findByFilter(new ValueFilter("station", QueryOperator.EQUALS, station), start, count, "samplingAt", OrderDir.DESC);
    }

}