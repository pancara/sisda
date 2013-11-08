package com.integrasolusi.pusda.sisda.dao.sda.hidrologi;

import com.integrasolusi.query.generic.HibernateGenericDao;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosHidrologi;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;

/**
 * User: pancara
 * Date: 8/4/12
 * Time: 7:16 PM
 */
public class PosHidrologiDaoImpl extends HibernateGenericDao<PosHidrologi, Long> implements PosHidrologiDao {

    @Override
    public void deactivateOthers(final PosHidrologi posHidrologi) {
        getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "UPDATE PosHidrologi ph SET active = FALSE WHERE ph.id <> :id";
                Query query = session.createQuery(queryString);
                query.setParameter("id", posHidrologi.getId());
                query.executeUpdate();
                return null;
            }
        });
    }
}
