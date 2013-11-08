package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.query.generic.HibernateGenericDao;
import com.integrasolusi.pusda.sisda.persistence.Publication;
import com.integrasolusi.pusda.sisda.persistence.PublicationAttachment;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;

/**
 * Programmer   : pancara
 * Date         : 6/25/11
 * Time         : 1:03 PM
 */
public class PublicationAttachmentDaoImpl extends HibernateGenericDao<PublicationAttachment, Long> implements PublicationAttachmentDao {
    @Override
    public Integer getMaxIndexByPublication(final Publication publication) {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "SELECT MAX(index) FROM PublicationAttachment pa WHERE pa.publication = :publication";
                Query query = session.createQuery(queryString);
                query.setParameter("publication", publication);
                return (Integer) query.uniqueResult();
            }
        });
    }
}
