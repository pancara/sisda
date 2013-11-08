package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.CommentGuest;
import com.integrasolusi.pusda.sisda.persistence.CommentGuestResponse;

/**
 * Programmer   : pancara
 * Date         : 6/19/11
 * Time         : 6:35 PM
 */
public interface CommentGuestResponseService {
    Long countAlls();

    Long countByKeyword(String keyword);

    java.util.List<CommentGuestResponse> get(Long start, Long count);

    java.util.List<CommentGuestResponse> get(String keyword, Long start, Long count);

    void save(CommentGuestResponse response);

    void removeByIds(Long[] ids);

    CommentGuestResponse findById(Long id);

    CommentGuest findResponseFor(CommentGuestResponse response);

    void removeById(Long id);
}
