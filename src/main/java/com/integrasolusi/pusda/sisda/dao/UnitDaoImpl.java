package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.query.generic.HibernateGenericDao;
import com.integrasolusi.pusda.sisda.persistence.Unit;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;

/**
 * Programmer   : pancara
 * Date         : 7/20/11
 * Time         : 1:27 PM
 */
public class UnitDaoImpl extends HibernateGenericDao<Unit, Long> implements UnitDao {

    @Override
    public boolean existSameIndex(final Unit parent) {
        return getHibernateTemplate().execute(new HibernateCallback<Boolean>() {
            @Override
            public Boolean doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "SELECT COUNT(index) AS count_index FROM Unit u WHERE u.parent = :parent GROUP BY u.index HAVING COUNT(index) > 1";
                Query query = session.createQuery(queryString);
                query.setParameter("parent", parent);
                query.setMaxResults(1);
                Object result = query.uniqueResult();
                if (result != null) {
                    Long count_index = (Long) result;
                    return count_index > 0;
                }
                return false;
            }
        });
    }
}
