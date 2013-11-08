package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.RegulationFolderDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.regulation.Folder;

import java.util.List;

/**
 * User: pancara
 * Date: 8/30/12
 * Time: 1:18 PM
 */
public class RegulationFolderServiceImpl implements RegulationFolderService {
    private RegulationFolderDao regulationFolderDao;

    public void setRegulationFolderDao(RegulationFolderDao regulationFolderDao) {
        this.regulationFolderDao = regulationFolderDao;
    }

    @Override
    public void save(Folder folder) {
        regulationFolderDao.save(folder);
    }

    @Override
    public List<Folder> findAlls() {
        return regulationFolderDao.findAlls("index", OrderDir.ASC);
    }

    @Override
    public List<Folder> findByParent(Folder parent) {
        return regulationFolderDao.findByFilter(new ValueFilter("parent", QueryOperator.EQUALS, parent, "parent"), "index", OrderDir.ASC);
    }

    @Override
    public Folder findById(Long id) {
        return regulationFolderDao.findById(id);
    }

    @Override
    public Long countAlls() {
        return regulationFolderDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return regulationFolderDao.countByFilter(createKeywordFilter(keyword));
    }

    private ValueFilter createKeywordFilter(String keyword) {
        return new ValueFilter("name", QueryOperator.LIKE, keyword, "name");
    }

    @Override
    public List<Folder> findAlls(Long start, Long count) {
        return regulationFolderDao.findAlls(start, count, "index", OrderDir.ASC);
    }

    @Override
    public List<Folder> findByKeyword(String keyword, Long start, Long count) {
        return regulationFolderDao.findByFilter(createKeywordFilter(keyword), start, count, "index", OrderDir.ASC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            regulationFolderDao.removeById(id);
        }
    }

    @Override
    public void moveDown(Folder folder) {
        Folder below = getFolderBelow(folder);
        if (below != null) {
            Integer temp = folder.getIndex();
            folder.setIndex(below.getIndex());
            below.setIndex(temp);
            regulationFolderDao.save(folder);
            regulationFolderDao.save(below);
        }
    }

    @Override
    public void moveUp(Folder folder) {
        Folder above = getFolderAbove(folder);
        if (above != null) {
            Integer temp = folder.getIndex();
            folder.setIndex(above.getIndex());
            above.setIndex(temp);
            regulationFolderDao.save(folder);
            regulationFolderDao.save(above);
        }
    }

    @Override
    public void reindex() {
        List<Folder> folders = regulationFolderDao.findAlls("index", OrderDir.ASC);
        Integer index = 1;
        for (Folder folder : folders) {
            folder.setIndex(index);
            index++;
            regulationFolderDao.save(folder);
        }
    }

    private Folder getFolderBelow(Folder folder) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);

        QueryOperator op = folder.getParent() != null ? QueryOperator.EQUALS : QueryOperator.IS;
        filter.add(new ValueFilter("parent", op, folder.getParent(), "parent"));

        filter.add(new ValueFilter("index", QueryOperator.GREATER, folder.getIndex(), "index"));
        List<Folder> belows = regulationFolderDao.findByFilter(filter, 0L, 1L, "index", OrderDir.ASC);
        return belows.size() > 0 ?
                belows.get(0) : null;
    }

    private Folder getFolderAbove(Folder folder) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        if (folder.getParent() != null) {
            filter.add(new ValueFilter("parent", QueryOperator.EQUALS, folder.getParent(), "parent"));
        } else {
            filter.add(new ValueFilter("parent", QueryOperator.IS, null, "parent"));
        }


        filter.add(new ValueFilter("index", QueryOperator.LESS, folder.getIndex(), "index"));
        List<Folder> aboves = regulationFolderDao.findByFilter(filter, 0L, 1L, "index", OrderDir.DESC);
        return aboves.size() > 0 ?
                aboves.get(0) : null;
    }
}
