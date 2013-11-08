package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.CommentPublicationDao;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.CommentPublication;

import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/19/11
 * Time         : 6:36 PM
 */
public class CommentPublicationServiceImpl implements CommentPublicationService {
    private CommentPublicationDao commentPublicationDao;

    public void setCommentPublicationDao(CommentPublicationDao commentPublicationDao) {
        this.commentPublicationDao = commentPublicationDao;
    }

    @Override
    public Long countAlls() {
        return commentPublicationDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return commentPublicationDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public List<CommentPublication> get(Long start, Long count) {
        return commentPublicationDao.findAlls(start, count, "id", OrderDir.DESC);
    }

    @Override
    public List<CommentPublication> get(String keyword, Long start, Long count) {
        Filter filter = createKeywordFilter(keyword);
        return commentPublicationDao.findByFilter(filter, start, count, "id", OrderDir.DESC);
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("message", QueryOperator.LIKE, keyword);
    }


    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids)
            commentPublicationDao.removeById(id);
    }


}
