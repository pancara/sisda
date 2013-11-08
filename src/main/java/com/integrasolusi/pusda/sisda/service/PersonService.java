package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Person;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/22/11
 * Time         : 4:36 PM
 */
public interface PersonService {
    Person findById(Long id);

    void getPhoto(Long id, OutputStream os) throws IOException;

    Boolean photoExist(Long id);

    Long countAlls();

    List<Person> get(Long start, Long count);

    void removeById(Long id);

    void save(Person person, InputStream is) throws IOException;

    void save(Person person);

    List<Person> findAlls();

    Long countByKeyword(String keyword);

    List<Person> getByKeyword(String keyword, Long start, Long count);
}
