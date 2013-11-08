package com.integrasolusi.pusda.sisda.tool.ffws;

import com.integrasolusi.pusda.sisda.persistence.ffws.Station;
import com.integrasolusi.pusda.sisda.persistence.ffws.WaterLevel;
import com.integrasolusi.pusda.sisda.service.ffws.WaterLevelService;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Programmer : pancara
 * Date       : 6/12/13
 * Time       : 3:36 PM
 */
public class DataWriter implements RowCallbackHandler {
    private WaterLevelService waterLevelService;
    private Station station;
    private int writeCount = 0;


    public DataWriter(Station station, WaterLevelService waterLevelService) {
        this.station = station;
        this.waterLevelService = waterLevelService;
    }

    @Override
    public void processRow(ResultSet rs) throws SQLException {
        WaterLevel waterLevel = new WaterLevel();
        waterLevel.setStation(station);
        waterLevel.setSamplingAt(rs.getTimestamp("SamplingAt"));
        waterLevel.setValue(rs.getDouble("WLevel"));
        waterLevelService.save(waterLevel);
        writeCount++;
    }

    public int getWriteCount() {
        return writeCount;
    }
}
