package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.query.generic.HibernateGenericDao;
import com.integrasolusi.pusda.sisda.persistence.CommentGuest;
import com.integrasolusi.pusda.sisda.persistence.CommentGuestResponse;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;

/**
 * Programmer   : pancara
 * Date         : 6/12/11
 * Time         : 3:02 PM
 */
public class CommentGuestResponseDaoImpl extends HibernateGenericDao<CommentGuestResponse, Long> implements CommentGuestResponseDao {
    @Override
    public CommentGuest getComment(final CommentGuestResponse response) {
        return getHibernateTemplate().execute(new HibernateCallback<CommentGuest>() {
            @Override
            public CommentGuest doInHibernate(Session session) throws HibernateException, SQLException {
                String hql = "SELECT r.commentGuest FROM CommentGuestResponse r WHERE r = :response";
                Query query = session.createQuery(hql);
                query.setParameter("response", response);
                return (CommentGuest) query.uniqueResult();
            }
        });
    }
}
