package com.integrasolusi.query.generic;

import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.util.OrderDir;
import org.hibernate.ScrollableResults;

import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/12/11
 * Time         : 2:55 PM
 */
public interface GenericDao<T, TId> {

    T findById(TId id);

    List<T> findByProperty(String propertyName, Object propertyValue);

    List<T> findByProperty(String propertyName, Object propertyValue, int start, int count);

    List<T> findByProperty(String propertyName, Object propertyValue, String orderProperty, OrderDir orderDir);

    List<T> findByProperty(String propertyName, Object propertyValue, String orderProperty, OrderDir orderDir, int start, int count);

    List<T> findByFilter(Filter filter);

    ScrollableResults findByFilterAsCursor(Filter filter);

    List<T> findByFilter(Filter filter, Long start, Long count);

    List<T> findByFilter(Filter filter, String orderProperty, OrderDir desc);

    List<T> findByFilter(Filter filter, Long start, Long count, String orderProperty, OrderDir desc);

    Long countByFilter(Filter filter);

    T findUniqueByFilter(Filter filter);

    T findUniqueByFilter(final Filter filter, String orderProperty, OrderDir orderDir);

    T findUniqueByProperty(String propertyName, Object propertyValue);

    Long countByProperty(String propertyName, Object propertyValue);

    T loadByID(TId id);

    void refresh(T entity);

    Long countAlls();

    T save(T obj);

    void remove(T entity);

    void removeById(TId id);

    void removeByFilter(Filter filter);

    List<T> findAlls();

    List<T> findAlls(String orderProperty);
    
    List<T> findAlls(String orderProperty, OrderDir orderDir);

    List<T> findAlls(Long start, Long count);

    List<T> findAlls(Long start, Long count, String orderProperty, OrderDir orderDir);

    T findByIndex(Long index, String orderProperty, OrderDir orderDir);

    T findByIndex(Filter filter, Long index, String orderProperty, OrderDir orderDir);
}
