package com.integrasolusi.pusda.sisda.dao.tkpsda.meeting;

import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.Activity;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.meeting.ActivityDocument;
import com.integrasolusi.query.generic.HibernateGenericDao;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;

/**
 * Programmer : pancara
 * Date       : 7/28/13
 * Time       : 6:28 PM
 */
public class ActivityDocumentDaoImpl extends HibernateGenericDao<ActivityDocument, Long> implements ActivityDocumentDao {
    @Override
    public Integer getMaxIndex(final Activity activity) {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "SELECT MAX(ad.index) FROM ActivityDocument ad WHERE ad.activity = :activity";
                Query query = session.createQuery(queryString);
                query.setParameter("activity", activity);
                Object result = query.uniqueResult();
                return result == null ? 0 : (Integer) result;
            }
        });
    }
}
