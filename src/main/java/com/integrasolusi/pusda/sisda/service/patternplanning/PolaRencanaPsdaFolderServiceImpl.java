package com.integrasolusi.pusda.sisda.service.patternplanning;

import com.integrasolusi.pusda.sisda.dao.patternplanning.PolaRencanaPsdaFolderDao;
import com.integrasolusi.pusda.sisda.persistence.patternplanning.PolaRencanaPsdaFolder;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;

import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/19/11
 * Time         : 6:27 PM
 */
public class PolaRencanaPsdaFolderServiceImpl implements PolaRencanaPsdaFolderService {
    private PolaRencanaPsdaFolderDao polaRencanaPsdaFolderDao;

    public void setPolaRencanaPsdaFolderDao(PolaRencanaPsdaFolderDao polaRencanaPsdaFolderDao) {
        this.polaRencanaPsdaFolderDao = polaRencanaPsdaFolderDao;
    }

    private ValueFilter createPublishedFilter() {
        return new ValueFilter("published", QueryOperator.EQUALS, true);
    }

    private ValueFilter createKeywordFilter(String keyword) {
        return new ValueFilter("name", QueryOperator.LIKE, keyword);
    }

    private CompositeFilter createPublishedAndKeywordFilter(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(createKeywordFilter(keyword));
        filter.add(createPublishedFilter());
        return filter;
    }

    @Override
    public Long countPublished() {
        return polaRencanaPsdaFolderDao.countByFilter(createPublishedFilter());
    }

    @Override
    public Long countPublishedByKeyword(String keyword) {
        return polaRencanaPsdaFolderDao.countByFilter(createPublishedAndKeywordFilter(keyword));
    }

    @Override
    public List<PolaRencanaPsdaFolder> getPublished(Long start, Long count) {
        return polaRencanaPsdaFolderDao.findByFilter(createPublishedFilter(), start, count);
    }

    @Override
    public List<PolaRencanaPsdaFolder> getPublishedByKeyword(String keyword, Long start, Long count) {
        return polaRencanaPsdaFolderDao.findByFilter(createKeywordFilter(keyword), start, count);
    }

    @Override
    public PolaRencanaPsdaFolder findById(Long id) {
        return polaRencanaPsdaFolderDao.findById(id);
    }

    @Override
    public Long countAlls() {
        return polaRencanaPsdaFolderDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return polaRencanaPsdaFolderDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public List<PolaRencanaPsdaFolder> findAlls(Long start, Long count) {
        return polaRencanaPsdaFolderDao.findAlls(start, count, "id", OrderDir.ASC);
    }

    @Override
    public List<PolaRencanaPsdaFolder> findByKeyword(String keyword, Long start, Long count) {
        return polaRencanaPsdaFolderDao.findByFilter(createKeywordFilter(keyword), start, count, "id", OrderDir.ASC);
    }

    @Override
    public void removeById(Long id) {
        polaRencanaPsdaFolderDao.removeById(id);
    }

    @Override
    public void save(PolaRencanaPsdaFolder parent) {
        polaRencanaPsdaFolderDao.save(parent);
    }

    @Override
    public List<PolaRencanaPsdaFolder> findByParent(PolaRencanaPsdaFolder parent) {
        return polaRencanaPsdaFolderDao.findByFilter(new ValueFilter("parent", QueryOperator.EQUALS, parent, "parent"), "index", OrderDir.ASC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            polaRencanaPsdaFolderDao.removeById(id);
        }
    }


    @Override
    public void moveDown(PolaRencanaPsdaFolder folder) {
        PolaRencanaPsdaFolder below = getFolderBelow(folder);
        if (below != null) {
            Integer temp = folder.getIndex();
            folder.setIndex(below.getIndex());
            below.setIndex(temp);
            polaRencanaPsdaFolderDao.save(folder);
            polaRencanaPsdaFolderDao.save(below);
        }
    }

    @Override
    public void moveUp(PolaRencanaPsdaFolder folder) {
        PolaRencanaPsdaFolder above = getFolderAbove(folder);
        if (above != null) {
            Integer temp = folder.getIndex();
            folder.setIndex(above.getIndex());
            above.setIndex(temp);
            polaRencanaPsdaFolderDao.save(folder);
            polaRencanaPsdaFolderDao.save(above);
        }
    }

    @Override
    public void reindex() {
        List<PolaRencanaPsdaFolder> folders = polaRencanaPsdaFolderDao.findAlls("index", OrderDir.ASC);
        Integer index = 1;
        for (PolaRencanaPsdaFolder folder : folders) {
            folder.setIndex(index);
            index++;
            polaRencanaPsdaFolderDao.save(folder);
        }
    }

    private PolaRencanaPsdaFolder getFolderBelow(PolaRencanaPsdaFolder folder) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);

        QueryOperator op = folder.getParent() != null ? QueryOperator.EQUALS : QueryOperator.IS;
        filter.add(new ValueFilter("parent", op, folder.getParent(), "parent"));

        filter.add(new ValueFilter("index", QueryOperator.GREATER, folder.getIndex(), "index"));
        List<PolaRencanaPsdaFolder> belows = polaRencanaPsdaFolderDao.findByFilter(filter, 0L, 1L, "index", OrderDir.ASC);
        return belows.size() > 0 ?
                belows.get(0) : null;
    }

    private PolaRencanaPsdaFolder getFolderAbove(PolaRencanaPsdaFolder folder) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        if (folder.getParent() != null) {
            filter.add(new ValueFilter("parent", QueryOperator.EQUALS, folder.getParent(), "parent"));
        } else {
            filter.add(new ValueFilter("parent", QueryOperator.IS, null, "parent"));
        }


        filter.add(new ValueFilter("index", QueryOperator.LESS, folder.getIndex(), "index"));
        List<PolaRencanaPsdaFolder> aboves = polaRencanaPsdaFolderDao.findByFilter(filter, 0L, 1L, "index", OrderDir.DESC);
        return aboves.size() > 0 ?
                aboves.get(0) : null;
    }
}
