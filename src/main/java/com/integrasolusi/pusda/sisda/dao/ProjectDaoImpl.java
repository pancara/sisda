package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.pusda.sisda.persistence.Project;
import com.integrasolusi.query.generic.HibernateGenericDao;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 6:19 PM
 */
public class ProjectDaoImpl extends HibernateGenericDao<Project, Long> implements ProjectDao {
    
    @Override
    public Long getLastIndex() {
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            @Override
            public Long doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "SELECT MAX(p.index) FROM Project p";
                Query query = session.createQuery(queryString);
                return (Long) query.uniqueResult();
            }
        });
    }
}
