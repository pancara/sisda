package com.integrasolusi.pusda.sisda.service.tkpsda.misc;

import com.integrasolusi.pusda.sisda.dao.tkpsda.misc.MiscFolderDao;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscFolder;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 7/28/13
 * Time       : 6:29 PM
 */
public class MiscFolderServiceImpl implements MiscFolderService {
    private MiscFolderDao miscFolderDao;

    public void setMiscFolderDao(MiscFolderDao miscFolderDao) {
        this.miscFolderDao = miscFolderDao;
    }

    @Override
    public List<MiscFolder> findByWilayahSungaiAndYear(WilayahSungai ws, Year year) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("ws", QueryOperator.EQUALS, ws, "ws"));
        filter.add(new ValueFilter("year", QueryOperator.EQUALS, year, "year"));

        return miscFolderDao.findByFilter(filter, "index", OrderDir.ASC);
    }

    @Override
    public MiscFolder findById(Long id) {
        return miscFolderDao.findById(id);
    }

    @Override
    public void save(MiscFolder folder) {
        miscFolderDao.save(folder);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            miscFolderDao.removeById(id);
        }
    }
}
