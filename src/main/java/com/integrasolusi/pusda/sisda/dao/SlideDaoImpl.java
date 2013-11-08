package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.query.generic.HibernateGenericDao;
import com.integrasolusi.pusda.sisda.persistence.Slide;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;

/**
 * Programmer   : pancara
 * Date         : 7/10/11
 * Time         : 2:45 PM
 */
public class SlideDaoImpl extends HibernateGenericDao<Slide, Long> implements SlideDao {
    @Override
    public Long getMaxIndex() {
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            @Override
            public Long doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "SELECT MAX(index) FROM Slide";
                Query query = session.createQuery(queryString);
                Long maxIndex = (Long) query.uniqueResult();
                return (maxIndex != null ? maxIndex : 0L);
            }
        });
    }
}
