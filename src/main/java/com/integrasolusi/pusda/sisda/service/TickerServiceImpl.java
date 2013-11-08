package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.TickerDao;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.Ticker;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 11/29/12
 * Time       : 2:55 PM
 */
public class TickerServiceImpl implements TickerService {
    private TickerDao tickerDao;

    public void setTickerDao(TickerDao tickerDao) {
        this.tickerDao = tickerDao;
    }

    @Override
    public List<Ticker> findAlls(Long start, Long count) {
        return tickerDao.findAlls(start, count, "id", OrderDir.DESC);
    }

    @Override
    public List<Ticker> findByKeyword(String keyword, Long start, Long count) {
        return tickerDao.findByFilter(createKeywordFilter(keyword), start, count, "id", OrderDir.DESC);
    }

    @Override
    public Long countAlls() {
        return tickerDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return tickerDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public Ticker findById(Long id) {
        return tickerDao.findById(id);
    }

    @Override
    public void save(Ticker ticker) {
        tickerDao.save(ticker);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            tickerDao.removeById(id);
        }
    }

    @Override
    public void publishByIds(Long[] ids) {
        for (Long id : ids) {
            Ticker ticker = tickerDao.findById(id);
            ticker.setPublish(true);
            tickerDao.save(ticker);
        }
    }


    @Override
    public void unpublishByIds(Long[] ids) {
        for (Long id : ids) {
            Ticker ticker = tickerDao.findById(id);
            ticker.setPublish(false);
            tickerDao.save(ticker);
        }
    }

    @Override
    public List<Ticker> findPublished(Long start, Long count) {
        return tickerDao.findByFilter(new ValueFilter("publish", QueryOperator.EQUALS, true, "publish"), start, count, "id", OrderDir.DESC);
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("title", QueryOperator.LIKE, keyword, "title");
    }

}
