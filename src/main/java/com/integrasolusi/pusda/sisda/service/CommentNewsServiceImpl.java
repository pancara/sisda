package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.CommentNewsDao;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.CommentNews;

import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/19/11
 * Time         : 6:36 PM
 */
public class CommentNewsServiceImpl implements CommentNewsService {
    private CommentNewsDao commentNewsDao;

    public void setCommentNewsDao(CommentNewsDao commentNewsDao) {
        this.commentNewsDao = commentNewsDao;
    }

    @Override
    public Long countAlls() {
        return commentNewsDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        Filter filter = createKeywordFilter(keyword);
        return commentNewsDao.countByFilter(filter);
    }

    @Override
    public List<CommentNews> get(Long start, Long count) {
        return commentNewsDao.findAlls(start, count, "id", OrderDir.DESC);
    }

    @Override
    public List<CommentNews> get(String keyword, Long start, Long count) {
        Filter filter = createKeywordFilter(keyword);
        return commentNewsDao.findByFilter(filter, start, count, "id", OrderDir.DESC);
    }

    private Filter createKeywordFilter(String keyword) {
        return new ValueFilter("message", QueryOperator.LIKE, keyword);
    }


    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids)
            commentNewsDao.removeById(id);
    }


}
