package com.integrasolusi.pusda.sisda.tool.ffws;

import com.integrasolusi.pusda.sisda.persistence.ffws.Station;
import com.integrasolusi.pusda.sisda.service.ffws.StationService;
import com.integrasolusi.pusda.sisda.service.ffws.WaterLevelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Programmer : pancara
 * Date       : 6/12/13
 * Time       : 3:00 PM
 */
public class DataPopulator implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(DataPopulator.class);

    private StationService stationService;
    private WaterLevelService waterLevelService;
    private DataSource ffwsDataSource;
    private int batchSize = 5;

    private boolean stopping = false;

    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    public void setWaterLevelService(WaterLevelService waterLevelService) {
        this.waterLevelService = waterLevelService;
    }

    public void setFfwsDataSource(DataSource ffwsDataSource) {
        this.ffwsDataSource = ffwsDataSource;
    }

    public void run() {
        List<Station> stationList = stationService.findAlls();
        for (Station station : stationList) {
            if (stopping) return;
            try {
                populateStationData(station );
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        stationList.clear();
    }

    private void populateStationData(Station station ) {
        DataWriter writer = new DataWriter(station, waterLevelService);
        Date lowBound = waterLevelService.getLastSamplingAt(station);
        FfwsDataReader reader = new FfwsDataReader(ffwsDataSource, batchSize);

        while (true) {
            reader.read(station.getSourceTable(), lowBound, writer);
            if (writer.getWriteCount() < reader.getMaxRow())
                break;

            if (stopping) return;
        }
    }

    public void reset() {
        this.stopping = false;
    }

    public void stop() {
        this.stopping = true;
    }
}
