package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.CommentGuestDao;
import com.integrasolusi.pusda.sisda.dao.CommentGuestResponseDao;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.CommentGuest;
import com.integrasolusi.pusda.sisda.persistence.CommentGuestResponse;
import com.integrasolusi.utils.EmailForwarder;

import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/19/11
 * Time         : 6:36 PM
 */
public class CommentGuestServiceImpl implements CommentGuestService {
    private CommentGuestDao commentGuestDao;
    private CommentGuestResponseDao commentGuestResponseDao;
    private EmailForwarder emailForwarder;

    public void setCommentGuestDao(CommentGuestDao commentGuestDao) {
        this.commentGuestDao = commentGuestDao;
    }

    public void setCommentGuestResponseDao(CommentGuestResponseDao commentGuestResponseDao) {
        this.commentGuestResponseDao = commentGuestResponseDao;
    }

    public void setEmailForwarder(EmailForwarder emailForwarder) {
        this.emailForwarder = emailForwarder;
    }

    @Override
    public CommentGuest findById(Long id) {
        return commentGuestDao.findById(id);
    }

    @Override
    public Long countAlls() {
        return commentGuestDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return commentGuestDao.countByProperty("message", keyword);
    }

    @Override
    public java.util.List<CommentGuest> get(Long start, Long count) {
        return commentGuestDao.findAlls(start, count, "id", OrderDir.DESC);
    }

    @Override
    public List<CommentGuest> get(String keyword, Long start, Long count) {
        return commentGuestDao.findByProperty("message", keyword, "id", OrderDir.DESC, start.intValue(), count.intValue());
    }

    @Override
    public void save(CommentGuest message) {
        commentGuestDao.save(message);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            CommentGuest comment = commentGuestDao.findById(id);
            commentGuestResponseDao.removeByFilter(new ValueFilter("commentGuest", QueryOperator.EQUALS, comment, "commentGuest"));
            commentGuestDao.remove(comment);
        }
    }

    @Override
    public List<CommentGuestResponse> findResponse(CommentGuest comment) {
        ValueFilter filter = new ValueFilter("commentGuest", QueryOperator.EQUALS, comment, "commentGuest");
        return commentGuestResponseDao.findByFilter(filter, "postDate", OrderDir.ASC);
    }

    @Override
    public void postComment(CommentGuest comment) {
        commentGuestDao.save(comment);
        emailForwarder.forwardCommentGuest(comment);
    }
}
