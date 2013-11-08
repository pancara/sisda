package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.CommentGuestResponseDao;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.CommentGuest;
import com.integrasolusi.pusda.sisda.persistence.CommentGuestResponse;

import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/19/11
 * Time         : 6:36 PM
 */
public class CommentGuestResponseServiceImpl implements CommentGuestResponseService {
    private CommentGuestResponseDao commentGuestResponseDao;

    public void setCommentGuestResponseDao(CommentGuestResponseDao commentGuestResponseDao) {
        this.commentGuestResponseDao = commentGuestResponseDao;
    }

    @Override
    public Long countAlls() {
        return commentGuestResponseDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return commentGuestResponseDao.countByProperty("message", keyword);
    }

    @Override
    public List<CommentGuestResponse> get(Long start, Long count) {
        return commentGuestResponseDao.findAlls(start, count, "id", OrderDir.DESC);
    }

    @Override
    public List<CommentGuestResponse> get(String keyword, Long start, Long count) {
        return commentGuestResponseDao.findByProperty("text", keyword, "id", OrderDir.DESC, start.intValue(), count.intValue());
    }

    @Override
    public void save(CommentGuestResponse response) {
        commentGuestResponseDao.save(response);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids)
            commentGuestResponseDao.removeById(id);
    }

    @Override
    public CommentGuestResponse findById(Long id) {
        return commentGuestResponseDao.findById(id);
    }

    @Override
    public CommentGuest findResponseFor(CommentGuestResponse response) {
        return commentGuestResponseDao.getComment(response);
    }

    @Override
    public void removeById(Long id) {
        commentGuestResponseDao.removeById(id);
    }
}
