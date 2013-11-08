package com.integrasolusi.pusda.sisda.dao.sda;

import com.integrasolusi.query.generic.HibernateGenericDao;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.Kekeringan;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.List;

/**
 * User: pancara
 * Date: 9/5/12
 * Time: 12:00 PM
 */
public class KekeringanDaoImpl extends HibernateGenericDao<Kekeringan, Long> implements KekeringanDao {
    @Override
    public List<Year> getYearByWilayahSungai(final WilayahSungai wilayahSungai) {
        return getHibernateTemplate().execute(new HibernateCallback<List<Year>>() {
            @Override
            public List<Year> doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "SELECT DISTINCT k.year FROM Kekeringan k WHERE k.wilayahSungai = :wilayahSungai ORDER BY k.year.value";
                Query query = session.createQuery(queryString);
                query.setParameter("wilayahSungai", wilayahSungai);
                return query.list();
            }
        });
    }
}
