package com.integrasolusi.pusda.sisda.dao;

import com.integrasolusi.pusda.sisda.persistence.Project;
import com.integrasolusi.query.generic.GenericDao;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 6:19 PM
 */
public interface ProjectDao extends GenericDao<Project, Long> {
    
    Long getLastIndex();
    
}
