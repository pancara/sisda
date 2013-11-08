package com.integrasolusi.query.generic;

import com.integrasolusi.query.filter.*;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.util.OrderDir;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/12/11
 * Time         : 2:56 PM
 */
public class HibernateGenericDao<T, TId> extends HibernateDaoSupport implements GenericDao<T, TId> {
    private Class<T> persistentClass;
    private Class<TId> idClass;

    @Override
    public Long countByFilter(final Filter filter) {
        return (Long) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = String.format("SELECT COUNT(e) FROM %1$s e WHERE %2$s", getPersistentClass().getCanonicalName(), filter.getExpression());
                Query query = session.createQuery(queryString);
                setQueryParameterValues(query, filter);

                return query.uniqueResult();
            }
        });
    }

    @Override
    public List<T> findByFilter(final com.integrasolusi.query.filter.Filter filter) {
        return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = String.format("FROM %1$s WHERE %2$s", getPersistentClass().getCanonicalName(), filter.getExpression());
                Query query = session.createQuery(queryString);
                setQueryParameterValues(query, filter);

                return query.list();
            }
        });
    }

    @Override
    public ScrollableResults findByFilterAsCursor(final Filter filter) {
        return (ScrollableResults) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = String.format("FROM %1$s WHERE %2$s", getPersistentClass().getCanonicalName(), filter.getExpression());
                Query query = session.createQuery(queryString);
                setQueryParameterValues(query, filter);
                return query.scroll(ScrollMode.FORWARD_ONLY);
            }
        });
    }

    @Override
    public List<T> findByFilter(final Filter filter, final Long start, final Long count) {
        return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = String.format("FROM %1$s WHERE %2$s", getPersistentClass().getCanonicalName(), filter.getExpression());
                Query query = session.createQuery(queryString);
                setQueryParameterValues(query, filter);

                query.setFirstResult(start.intValue());
                query.setMaxResults(count.intValue());
                return query.list();
            }
        });
    }

    @Override
    public List<T> findByFilter(final Filter filter, final String orderProperty, final OrderDir orderDir) {
        return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = String.format("FROM %1$s WHERE %2$s ORDER BY %3$s %4$s ",
                        getPersistentClass().getCanonicalName(), filter.getExpression(), orderProperty, orderDir);
                Query query = session.createQuery(queryString);
                setQueryParameterValues(query, filter);
                return query.list();
            }
        });
    }

    @Override
    public List<T> findByFilter(final Filter filter, final Long start, final Long count, final String orderProperty, final OrderDir orderDir) {
        return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = String.format("FROM %1$s WHERE %2$s ORDER BY %3$s %4$s ",
                        getPersistentClass().getCanonicalName(), filter.getExpression(), orderProperty, orderDir);
                Query query = session.createQuery(queryString);
                setQueryParameterValues(query, filter);
                query.setFirstResult(start.intValue());
                query.setMaxResults(count.intValue());
                return query.list();
            }
        });
    }

    @Override
    public T findUniqueByFilter(final Filter filter) {
        return (T) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = String.format("FROM %1$s WHERE %2$s", getPersistentClass().getCanonicalName(), filter.getExpression());
                Query query = session.createQuery(queryString);
                setQueryParameterValues(query, filter);

                query.setFirstResult(0);
                query.setMaxResults(1);
                return query.uniqueResult();
            }
        });
    }

    @Override
    public T findUniqueByFilter(final Filter filter, final String orderProperty, final OrderDir orderDir) {
        return (T) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = String.format("FROM %1$s WHERE %2$s ORDER BY %3$s %4$s",
                        getPersistentClass().getCanonicalName(),
                        filter.getExpression(),
                        orderProperty,
                        orderDir);
                Query query = session.createQuery(queryString);
                setQueryParameterValues(query, filter);

                query.setFirstResult(0);
                query.setMaxResults(1);
                return query.uniqueResult();
            }
        });
    }

    protected void setQueryParameterValues(Query query, Filter filter) {
        List valueFilters = new LinkedList();
        populateValueFilter(filter, valueFilters);
        for (Object o : valueFilters) {
            ValueFilter f = (ValueFilter) o;
            if ((f.getValue() == null) && (QueryOperator.IS.equals(f.getOperator()) || QueryOperator.EQUALS.equals(f.getOperator())))
                continue;

            if (f.getValue() instanceof Collection) {
                Collection value = (Collection) f.getValue();
                query.setParameterList(f.getParameterName(), value);
            } else {
                Object value = f.getValue();
                query.setParameter(f.getParameterName(), value);
            }
        }
    }

    private void populateValueFilter(Filter filter, List result) {
        if (filter instanceof ValueFilter) result.add(filter);

        if (filter instanceof NegateFilter) {
            Filter innerFilter = ((NegateFilter) filter).getFilter();
            populateValueFilter(innerFilter, result);
        }
        if (filter instanceof CompositeFilter) {
            CompositeFilter compositeFilter = (CompositeFilter) filter;
            for (Filter f : compositeFilter.getFilterList())
                populateValueFilter(f, result);
        }
    }

    @Override
    public Long countByProperty(final String propertyName,
                                final Object propertyValue) {
        return (Long) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                String queryString = createFindByPropertyQuery(propertyName);
                queryString = "select count(*) " + queryString;

                Query query = session.createQuery(queryString);
                if (propertyValue instanceof String) {
                    query.setParameter(propertyName, "%" + propertyValue + "%");
                } else {
                    query.setParameter(propertyName, propertyValue);
                }
                return query.uniqueResult();
            }
        });
    }

    @Override
    public Long countAlls() {
        return (Long) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                String queryString = String.format("SELECT COUNT(e) FROM %s e", getPersistentClass().getCanonicalName());
                Query query = session.createQuery(queryString);
                return query.uniqueResult();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public HibernateGenericDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.idClass = (Class<TId>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public Class<T> getPersistentClass() {
        return this.persistentClass;
    }

    public Class<TId> getIdClass() {
        return this.idClass;
    }

    @Override
    public List<T> findAlls() {
        return (List<T>) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Criteria criteria = session
                                .createCriteria(getPersistentClass());
                        criteria.addOrder(Order.asc("id"));
                        return criteria.list();
                    }
                });
    }

    @Override
    public List<T> findAlls(final Long first, final Long count) {
        return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = String.format("FROM %s ORDER BY id", getPersistentClass().getCanonicalName());
                Query query = session.createQuery(queryString);
                query.setFirstResult(first.intValue());
                query.setMaxResults(count.intValue());
                return query.list();
            }
        });
    }

    @Override
    public List<T> findAlls(final Long start, final Long count, final String orderProperty, final OrderDir orderDir) {
        return (List<T>) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Criteria criteria = session
                                .createCriteria(getPersistentClass());
                        if (OrderDir.ASC.equals(orderDir)) {
                            criteria.addOrder(Order.asc(orderProperty));
                        } else {
                            criteria.addOrder(Order.desc(orderProperty));
                        }

                        criteria.setFirstResult(start.intValue());
                        criteria.setMaxResults(count.intValue());
                        return criteria.list();
                    }
                });
    }


    @Override
    public List<T> findAlls(String orderProperty) {
        return findAlls(orderProperty, OrderDir.ASC);
    }

    @Override
    public List<T> findAlls(final String orderProperty, final OrderDir orderDir) {
        return (List<T>) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        Criteria criteria = session
                                .createCriteria(getPersistentClass());
                        if (OrderDir.ASC.equals(orderDir)) {
                            criteria.addOrder(Order.asc(orderProperty));
                        } else {
                            criteria.addOrder(Order.desc(orderProperty));
                        }
                        return criteria.list();
                    }
                });
    }

    @Override
    public void refresh(T entity) {
        getHibernateTemplate().refresh(entity);
    }

    @Override
    public T findById(final TId ID) {
        return (T) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        return session.get(getPersistentClass(),
                                (Serializable) ID);
                    }
                });
    }

    private String createFindByPropertyQuery(String propertyName) {
        return "from " + getPersistentClass().getCanonicalName() + " entity "
                + "where entity." + propertyName + " like :" + propertyName;
    }

    private String createFindByPropertyQueryOrderBy(String propertyName,
                                                    String orderProperty, OrderDir orderDir) {
        return "from " + getPersistentClass().getCanonicalName() + " entity "
                + "where entity." + propertyName + " like :" + propertyName
                + " order by " + orderProperty + " " + orderDir;
    }

    @Override
    public List<T> findByProperty(final String propertyName,
                                  final Object propertyValue) {
        return (List<T>) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        String queryString = createFindByPropertyQuery(propertyName);
                        Query query = session.createQuery(queryString);
                        if (propertyValue instanceof String) {
                            query.setParameter(propertyName, "%" + propertyValue + "%");
                        } else {
                            query.setParameter(propertyName, propertyValue);
                        }
                        return query.list();
                    }
                });
    }

    @Override
    public List<T> findByProperty(final String propertyName, final Object propertyValue,
                                  final String orderProperty, final OrderDir orderDir) {
        return (List<T>) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        String queryString = createFindByPropertyQueryOrderBy(propertyName, orderProperty, orderDir);
                        Query query = session.createQuery(queryString);

                        if (propertyValue instanceof String) {
                            query.setParameter(propertyName, "%"
                                    + propertyValue + "%");
                        } else {
                            query.setParameter(propertyName, propertyValue);
                        }
                        return query.list();
                    }
                });
    }

    /**
     * Find by property for page number.
     */
    @Override
    public List<T> findByProperty(final String propertyName, final Object propertyValue, final int start, final int count) {
        return (List<T>) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session) {
                        String hql = createFindByPropertyQuery(propertyName);

                        Query query = session.createQuery(hql);
                        if (propertyValue instanceof String) {
                            query.setParameter(propertyName, "%" + propertyValue + "%");
                        } else {
                            query.setParameter(propertyName, propertyValue);
                        }

                        query.setFirstResult(start);
                        query.setMaxResults(count);
                        return query.list();
                    }
                });
    }

    @Override
    public T findUniqueByProperty(final String propertyName, final Object propertyValue) {
        return (T) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session) {
                        String queryString = String.format("FROM %s WHERE %s = :value", getPersistentClass().getCanonicalName(), propertyName);

                        Query query = session.createQuery(queryString);
                        query.setParameter("value", propertyValue);
                        query.setMaxResults(1);
                        return query.uniqueResult();
                    }
                });
    }

    /**
     * Find by property and order by and page.
     */
    @Override
    public List<T> findByProperty(final String propertyName,
                                  final Object propertyValue, final String orderProperty,
                                  final OrderDir orderDir, final int start, final int count) {
        return (List<T>) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session) {
                        String hql = createFindByPropertyQueryOrderBy(propertyName, orderProperty, orderDir);

                        Query query = session.createQuery(hql);
                        if (propertyValue instanceof String) {
                            query.setParameter(propertyName, "%" + propertyValue + "%");
                        } else {
                            query.setParameter(propertyName, propertyValue);
                        }

                        query.setFirstResult(start);
                        query.setMaxResults(count);
                        return query.list();
                    }
                });
    }

    @Override
    public T loadByID(final TId ID) {
        return (T) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        return session.load(getPersistentClass(),
                                (Serializable) ID);
                    }
                });
    }

    @Override
    public T save(final T obj) {
        return (T) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session session)
                            throws HibernateException, SQLException {
                        session.saveOrUpdate(obj);
                        return obj;
                    }
                });
    }

    @Override
    public void remove(final T obj) {
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                session.delete(obj);
                return null;
            }
        });
    }

    public void removeById(final TId id) {
        getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                T o = (T) session.get(getPersistentClass(), (Serializable) id);
                session.delete(o);
                return null;
            }
        });
    }

    @Override
    public void removeByFilter(final Filter filter) {
        getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = String.format("DELETE FROM %1$s WHERE %2$s", getPersistentClass().getCanonicalName(), filter.getExpression());
                Query query = session.createQuery(queryString);

                setQueryParameterValues(query, filter);
                query.executeUpdate();
                return null;
            }
        });
    }

    @Override
    public T findByIndex(final Long index, final String orderProperty, final OrderDir orderDir) {
        return (T) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = String.format("FROM %1$s ORDER BY %2$s %3$s",
                        getPersistentClass().getCanonicalName(),
                        orderProperty,
                        orderDir);
                Query query = session.createQuery(queryString);
                query.setFirstResult(index.intValue());
                query.setMaxResults(1);
                return query.uniqueResult();
            }
        });
    }

    @Override
    public T findByIndex(final Filter filter, final Long index, final String orderProperty, final OrderDir orderDir) {
        return (T) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = String.format("FROM %1$s WHERE %2$s ORDER BY %3$s %4$s",
                        getPersistentClass().getCanonicalName(),
                        filter.getExpression(),
                        orderProperty,
                        orderDir);
                Query query = session.createQuery(queryString);
                setQueryParameterValues(query, filter);

                query.setFirstResult(index.intValue());
                query.setMaxResults(1);
                return query.uniqueResult();
            }
        });
    }
}
