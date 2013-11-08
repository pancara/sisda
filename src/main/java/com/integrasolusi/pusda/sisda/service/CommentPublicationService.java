package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.CommentPublication;

/**
 * Programmer   : pancara
 * Date         : 6/19/11
 * Time         : 6:35 PM
 */
public interface CommentPublicationService {
    Long countAlls();

    Long countByKeyword(String keyword);

    java.util.List<CommentPublication> get(Long start, Long count);

    java.util.List<CommentPublication> get(String keyword, Long start, Long count);

    void removeByIds(Long[] ids);
}
