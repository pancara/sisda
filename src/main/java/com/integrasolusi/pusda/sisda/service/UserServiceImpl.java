package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.UserDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.Filter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.User;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/24/11
 * Time         : 11:47 PM
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean authenticate(String userId, String password) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("userId", QueryOperator.EQUALS, userId));
        filter.add(new ValueFilter("active", QueryOperator.EQUALS, true));
        User user = userDao.findUniqueByFilter(filter);
        if (user != null) {
            return StringUtils.equals(user.getPassword(), password);
        }
        return false;
    }

    @Override
    public User findUserByUserId(String userId) {
        Filter filter = new ValueFilter("userId", QueryOperator.EQUALS, userId);
        return userDao.findUniqueByFilter(filter);
    }

    @Override
    public Long countAlls() {
        return userDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return userDao.countByFilter(createFilter(keyword));
    }

    private CompositeFilter createFilter(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.OR);
        filter.add(new ValueFilter("userId", QueryOperator.LIKE, keyword));
        filter.add(new ValueFilter("name", QueryOperator.LIKE, keyword));
        return filter;
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public void removeById(Long id) {
        userDao.removeById(id);
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public List<User> findAlls(Long start, Long count) {
        return userDao.findAlls(start, count, "id", OrderDir.ASC);
    }

    @Override
    public List<User> findByKeyword(String keyword, Long start, Long count) {
        return userDao.findByFilter(createFilter(keyword), start, count, "id", OrderDir.ASC);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            userDao.removeById(id);
        }
    }

    @Override
    public void activateUser(Long[] ids) {
        for (Long id : ids) {
            User user = userDao.findById(id);
            user.setActive(true);
            userDao.save(user);
        }
    }

    @Override
    public void deactivateUser(Long[] ids) {
        for (Long id : ids) {
            User user = userDao.findById(id);
            user.setActive(false);
            userDao.save(user);
        }
    }
}
