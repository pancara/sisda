package com.integrasolusi.pusda.sisda.dao.tkpsda.misc;

import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscDocument;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscFolder;
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
public class MiscDocumentDaoImpl extends HibernateGenericDao<MiscDocument, Long> implements MiscDocumentDao {
    @Override
    public Integer getMaxIndex(final MiscFolder folder) {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "SELECT MAX(md.index) FROM MiscDocument md WHERE md.folder = :folder";
                Query query = session.createQuery(queryString);
                query.setParameter("folder", folder);
                Object result = query.uniqueResult();
                return result == null ? 0 : (Integer) result;
            }
        });
    }
}
