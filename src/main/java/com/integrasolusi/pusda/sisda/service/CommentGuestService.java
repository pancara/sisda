package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.CommentGuest;
import com.integrasolusi.pusda.sisda.persistence.CommentGuestResponse;

import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 6/19/11
 * Time         : 6:35 PM
 */
public interface CommentGuestService {
    CommentGuest findById(Long id);

    Long countAlls();

    Long countByKeyword(String keyword);

    java.util.List<CommentGuest> get(Long start, Long count);

    java.util.List<CommentGuest> get(String keyword, Long start, Long count);

    void save(CommentGuest message);

    void removeByIds(Long[] ids);

    List<CommentGuestResponse> findResponse(CommentGuest comment);

    void postComment(CommentGuest comment);
}
