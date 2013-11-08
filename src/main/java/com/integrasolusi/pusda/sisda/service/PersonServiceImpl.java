package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.PersonDao;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.Person;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/22/11
 * Time         : 4:36 PM
 */
public class PersonServiceImpl implements PersonService {
    private PersonDao personDao;
    private BlobRepository blobRepository;

    public void setPersonDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public Person findById(Long id) {
        return personDao.findById(id);
    }

    @Override
    public void getPhoto(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.PERSON, id, os);
    }

    @Override
    public Boolean photoExist(Long id) {
        return blobRepository.isExist(BlobDataType.PERSON, id);
    }

    @Override
    public Long countAlls() {
        return personDao.countAlls();
    }

    @Override
    public List<Person> get(Long start, Long count) {
        return personDao.findAlls(start, count);
    }

    @Override
    public void removeById(Long id) {
        personDao.removeById(id);
        blobRepository.remove(BlobDataType.PERSON, id);
    }

    @Override
    public void save(Person person, InputStream is) throws IOException {
        personDao.save(person);
        blobRepository.store(BlobDataType.PERSON, person.getId(), is);

    }

    @Override
    public void save(Person person) {
        personDao.save(person);

    }

    @Override
    public List<Person> findAlls() {
        return personDao.findAlls("name", OrderDir.ASC);
    }

    private ValueFilter createKeywordFilter(String keyword) {
        return new ValueFilter("name", QueryOperator.LIKE, keyword);
    }

    @Override
    public Long countByKeyword(String keyword) {
        return personDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public List<Person> getByKeyword(String keyword, Long start, Long count) {
        return personDao.findByFilter(createKeywordFilter(keyword), start, count);
    }
}
