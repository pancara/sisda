package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.User;

import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/24/11
 * Time         : 11:47 PM
 */
public interface UserService {

    boolean authenticate(String userId, String password);

    User findUserByUserId(String userId);

    Long countAlls();

    Long countByKeyword(String keyword);

    void removeById(Long id);

    User findById(Long id);

    void save(User user);

    List<User> findAlls(Long start, Long count);

    List<User> findByKeyword(String keyword, Long start, Long count);

    void removeByIds(Long[] ids);

    void activateUser(Long[] ids);

    void deactivateUser(Long[] ids);
}
