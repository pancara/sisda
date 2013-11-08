package com.integrasolusi.pusda.sisda.tool.ffws;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 6/12/13
 * Time       : 3:27 PM
 */
public class FfwsDataReader {
    private JdbcTemplate jdbcTemplate;
    private int maxRow = 1000;


    public FfwsDataReader(DataSource dataSource, int maxRow) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.maxRow = maxRow;
    }

    public void read(String tableName, Date lowBound, DataWriter dataWriter) {
        String queryString = String.format("SELECT CONCAT(SamplingDate, ' ', SamplingTime) AS SamplingAt, WLevel FROM %s WHERE CONCAT(SamplingDate, ' ', SamplingTime) > ? " +
                "ORDER BY SamplingAt ASC LIMIT %d", tableName, maxRow);
        jdbcTemplate.query(queryString, new Object[]{lowBound}, dataWriter);
    }

    public int getMaxRow() {
        return maxRow;
    }
}
