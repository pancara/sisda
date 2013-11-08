package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.MapCategoryDao;
import com.integrasolusi.pusda.sisda.dao.MapDao;
import com.integrasolusi.pusda.sisda.persistence.Map;
import com.integrasolusi.pusda.sisda.persistence.MapCategory;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/29/11
 * Time         : 4:30 PM
 */
public class MapServiceImpl implements MapService {
    private MapDao mapDao;
    private MapCategoryDao mapCategoryDao;
    private BlobRepository blobRepository;

    public void setMapDao(MapDao mapDao) {
        this.mapDao = mapDao;
    }

    public void setMapCategoryDao(MapCategoryDao mapCategoryDao) {
        this.mapCategoryDao = mapCategoryDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public Long countPublished() {
        return mapDao.countByFilter(createPublishedFilter());
    }

    @Override
    public Long countPublishedByKeyword(String keyword) {
        return mapDao.countByFilter(createPublishedAndKeywordFilter(keyword));
    }

    @Override
    public List<Map> getPublished(Long start, Long count) {
        return mapDao.findByFilter(createPublishedFilter(), start, count);
    }

    @Override
    public List<Map> getPublishedByKeyword(String keyword, Long start, Long count) {
        return mapDao.findByFilter(createPublishedAndKeywordFilter(keyword));
    }

    @Override
    public Map findById(Long id) {
        return mapDao.findById(id);
    }

    @Override
    public void getDocument(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.MAP, id, os);
    }

    @Override
    public InputStream getPictureStream(Long id) throws IOException {
        return blobRepository.getStream(BlobDataType.MAP, id);
    }

    @Override
    public Boolean hasPicture(Long id) {
        return blobRepository.isExist(BlobDataType.MAP, id);
    }

    private ValueFilter createPublishedFilter() {
        return new ValueFilter("published", QueryOperator.EQUALS, true);
    }

    private CompositeFilter createPublishedAndKeywordFilter(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"));
        filter.add(createPublishedFilter());
        return filter;
    }

    @Override
    public Long countAlls() {
        return mapDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return mapDao.countByFilter(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"));
    }

    @Override
    public Long countByCategory(Long category) {
        return mapDao.countByFilter(new ValueFilter("category.id", QueryOperator.EQUALS, category, "category_id"));
    }

    @Override
    public Long countByCategoryAndKeyword(Long category, String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"));
        filter.add(new ValueFilter("category.id", QueryOperator.EQUALS, category, "category_id"));
        return mapDao.countByFilter(filter);
    }

    @Override
    public Long countByPublished(boolean published) {
        return mapDao.countByFilter(new ValueFilter("published", QueryOperator.EQUALS, true, "published"));
    }

    @Override
    public Long countByKeywordAndPublished(String keyword, boolean published) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"));
        filter.add(new ValueFilter("published", QueryOperator.EQUALS, published, "published"));
        return mapDao.countByFilter(filter);

    }

    @Override
    public Long countByCategoryAndPublished(Long categoryId, boolean published) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);

        List<MapCategory> categories = getMapCategoriesTree(categoryId);
        filter.add(new ValueFilter("category", QueryOperator.IN, categories, "categories"));

        filter.add(new ValueFilter("published", QueryOperator.EQUALS, published, "published"));
        return mapDao.countByFilter(filter);
    }

    @Override
    public Long countByCategoryAndKeywordAndPublished(Long categoryId, String keyword, boolean published) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);

        List<MapCategory> categories = getMapCategoriesTree(categoryId);
        filter.add(new ValueFilter("category", QueryOperator.IN, categories, "categories"));

        filter.add(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"));
        filter.add(new ValueFilter("published", QueryOperator.EQUALS, published, "published"));
        return mapDao.countByFilter(filter);
    }

    @Override
    public List<Map> get(Long start, Long count) {
        return mapDao.findAlls(start, count);
    }

    @Override
    public List<Map> getByKeyword(String keyword, Long start, Long count) {
        return mapDao.findByFilter(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"), start, count);
    }

    @Override
    public void removeById(Long id) {
        mapDao.removeById(id);
        blobRepository.remove(BlobDataType.MAP, id);
    }

    @Override
    public void save(Map map, InputStream is) throws IOException {
        mapDao.save(map);
        blobRepository.store(BlobDataType.MAP, map.getId(), is);
    }

    @Override
    public void save(Map map) {
        mapDao.save(map);
    }

    @Override
    public List<Map> findAlls(Long start, Long count) {
        return mapDao.findAlls(start, count, "name", OrderDir.ASC);
    }

    @Override
    public List<Map> findByFilter(ValueFilter filter) {
        return mapDao.findByFilter(filter, "name", OrderDir.ASC);
    }

    @Override
    public List<Map> findByKeyword(String keyword, Long start, Long count) {
        return mapDao.findByFilter(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"), start, count, "name", OrderDir.ASC);
    }

    @Override
    public List<Map> findByCategory(MapCategory category) {
        return mapDao.findByFilter(new ValueFilter("category", QueryOperator.EQUALS, category, "category"), "name", OrderDir.ASC);
    }

    @Override
    public List<Map> findByCategory(Long category, Long start, Long count) {
        return mapDao.findByFilter(new ValueFilter("category.id", QueryOperator.EQUALS, category, "category_id"), start, count, "name", OrderDir.ASC);
    }

    @Override
    public List<Map> findByCategoryAndKeyword(Long category, String keyword, Long start, Long count) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("category.id", QueryOperator.EQUALS, category, "category_id"));
        filter.add(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"));
        return mapDao.findByFilter(filter, start, count, "name", OrderDir.ASC);
    }


    @Override
    public List<Map> findByPublished(Long start, Long count, boolean published) {
        ValueFilter filter = new ValueFilter("published", QueryOperator.EQUALS, published, "published");
        return mapDao.findByFilter(filter, start, count, "name", OrderDir.ASC);
    }

    @Override
    public List<Map> findByKeywordAndPublished(String keyword, boolean published, Long start, Long count) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"));
        filter.add(new ValueFilter("published", QueryOperator.EQUALS, published, "published"));
        return mapDao.findByFilter(filter, start, count, "name", OrderDir.ASC);
    }

    @Override
    public List<Map> findByCategoryAndPublished(Long categoryId, boolean published, Long start, Long count) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);

        List<MapCategory> categories = getMapCategoriesTree(categoryId);

        filter.add(new ValueFilter("category", QueryOperator.IN, categories, "categories"));
        filter.add(new ValueFilter("published", QueryOperator.EQUALS, published, "published"));
        return mapDao.findByFilter(filter, start, count, "name", OrderDir.ASC);
    }

    private List<MapCategory> getMapCategoriesTree(Long categoryId) {
        List<MapCategory> categories = new LinkedList<>();
        MapCategory category = mapCategoryDao.findById(categoryId);
        populateCategoryTree(category, categories);
        return categories;
    }

    @Override
    public List<Map> findByCategoryAndKeywordAndPublished(Long categoryId, String keyword, boolean published, Long start, Long count) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);

        List<MapCategory> categories = getMapCategoriesTree(categoryId);
        filter.add(new ValueFilter("category", QueryOperator.IN, categories, "categories"));

        filter.add(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"));
        filter.add(new ValueFilter("published", QueryOperator.EQUALS, published, "published"));
        return mapDao.findByFilter(filter, start, count, "name", OrderDir.ASC);
    }


    private void populateCategoryTree(MapCategory category, List<MapCategory> categories) {
        categories.add(category);
        ValueFilter filter = new ValueFilter("parent", QueryOperator.EQUALS, category, "parent");
        List<MapCategory> children = mapCategoryDao.findByFilter(filter);
        for (MapCategory child : children) {
            populateCategoryTree(child, categories);
        }
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            removeById(id);
        }
    }

    @Override
    public void publishByIds(Long[] ids) {
        for (Long id : ids) {
            Map map = mapDao.findById(id);
            map.setPublished(true);
            mapDao.save(map);
        }
    }

    @Override
    public void unpublishByIds(Long[] ids) {
        for (Long id : ids) {
            Map map = mapDao.findById(id);
            map.setPublished(false);
            mapDao.save(map);
        }
    }


}
