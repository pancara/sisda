package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.LinkTypeDao;
import com.integrasolusi.pusda.sisda.persistence.LinkType;

/**
 * Programmer : pancara
 * Date       : 11/28/12
 * Time       : 11:15 PM
 */
public class LinkTypeServiceImpl implements LinkTypeService {
    private LinkTypeDao linkTypeDao;

    public void setLinkTypeDao(LinkTypeDao linkTypeDao) {
        this.linkTypeDao = linkTypeDao;
    }

    @Override
    public LinkType findById(Long id) {
        return linkTypeDao.findById(id);
    }
}
