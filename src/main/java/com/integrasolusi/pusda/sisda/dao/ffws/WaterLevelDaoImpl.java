package com.integrasolusi.pusda.sisda.dao.ffws;

import com.integrasolusi.pusda.sisda.persistence.ffws.Station;
import com.integrasolusi.pusda.sisda.persistence.ffws.WaterLevel;
import com.integrasolusi.query.generic.HibernateGenericDao;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.Date;

/**
 * Programmer : pancara
 * Date       : 6/12/13
 * Time       : 12:46 PM
 */
public class WaterLevelDaoImpl extends HibernateGenericDao<WaterLevel, Long> implements WaterLevelDao {

    @Override
    public Date getLastSamplingAt(final Station station) {
        return getHibernateTemplate().execute(new HibernateCallback<Date>() {
            @Override
            public Date doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "SELECT MAX(samplingAt) FROM WaterLevel WHERE station = :station";
                Query query = session.createQuery(queryString);
                query.setParameter("station", station);
                Date result = (Date) query.uniqueResult();
                return result == null ? new Date(0) : result;
            }
        });
    }

    @Override
    public WaterLevel getLatest(final Station station) {
        return getHibernateTemplate().execute(new HibernateCallback<WaterLevel>() {
            @Override
            public WaterLevel doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "FROM WaterLevel WHERE station = :station ORDER BY samplingAt DESC";
                Query query = session.createQuery(queryString);
                query.setParameter("station", station);
                query.setMaxResults(1);
                return (WaterLevel) query.uniqueResult();
            }
        });
    }

}
