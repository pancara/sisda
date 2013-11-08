package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.CommentNews;

/**
 * Programmer   : pancara
 * Date         : 6/19/11
 * Time         : 6:35 PM
 */
public interface CommentNewsService {
    Long countAlls();

    Long countByKeyword(String keyword);

    java.util.List<CommentNews> get(Long start, Long count);

    java.util.List<CommentNews> get(String keyword, Long start, Long count);

    void removeByIds(Long[] ids);
}
