package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.pusda.sisda.persistence.VisitorCounter;
import com.integrasolusi.query.generic.HibernateGenericDao;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 7/22/13
 * Time       : 1:17 PM
 */
public class VisitorCounterDaoImpl extends HibernateGenericDao<VisitorCounter, Long> implements VisitorCounterDao {

    @Override
    public Long getTotal() {
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            @Override
            public Long doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "SELECT SUM(v.count) FROM VisitorCounter v";
                Query query = session.createQuery(queryString);
                return (Long) query.uniqueResult();
            }
        });
    }

    @Override
    public Long getTotal(final Date start, final Date end) {
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            @Override
            public Long doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "SELECT SUM(v.count) FROM VisitorCounter v WHERE v.date >= :start AND v.date <= :end";
                Query query = session.createQuery(queryString);
                query.setParameter("start", start);
                query.setParameter("end", end);
                return (Long) query.uniqueResult();
            }
        });
    }

    @Override
    public Long getTotal(final Integer month, final Integer year) {
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            @Override
            public Long doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "SELECT SUM(v.count) FROM VisitorCounter v WHERE MONTH(v.date) = :month AND YEAR(v.date) = :year";
                Query query = session.createQuery(queryString);
                query.setParameter("month", month);
                query.setParameter("year", year);
                return (Long) query.uniqueResult();
            }
        });
    }


}
