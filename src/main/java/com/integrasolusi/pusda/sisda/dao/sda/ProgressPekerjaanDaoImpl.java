package com.integrasolusi.pusda.sisda.dao.sda;

import com.integrasolusi.query.generic.HibernateGenericDao;
import com.integrasolusi.pusda.sisda.persistence.ProgressPekerjaan;
import com.integrasolusi.pusda.sisda.persistence.SatuanKerja;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.Kabupaten;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 10/16/12
 * Time       : 1:35 PM
 */
public class ProgressPekerjaanDaoImpl extends HibernateGenericDao<ProgressPekerjaan, Long> implements ProgressPekerjaanDao {
    @Override
    public List<Kabupaten> findKabupatenByYearAndSatuanKerja(final Year year, final SatuanKerja satuanKerja) {
        return getHibernateTemplate().execute(new HibernateCallback<List<Kabupaten>>() {
            @Override
            public List<Kabupaten> doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "SELECT DISTINCT p.kabupaten FROM ProgressPekerjaan p WHERE (p.satuanKerja = :satuanKerja) AND (p.year = :year)";
                Query query = session.createQuery(queryString);
                query.setParameter("satuanKerja", satuanKerja);
                query.setParameter("year", year);
                return query.list();
            }
        });
    }
}
