package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.BookDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.Book;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/12/11
 * Time         : 9:08 AM
 */
public class BookServiceImpl implements BookService {
    private BookDao bookDao;
    private BlobRepository blobRepository;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public Long countPublished() {
        return bookDao.countByFilter(createPublishedFilter());
    }


    @Override
    public Long countPublishedByKeyword(String keyword) {
        CompositeFilter filter = createPublishedAndKeywordFilter(keyword);
        return bookDao.countByFilter(filter);
    }

    private ValueFilter createPublishedFilter() {
        return new ValueFilter("published", QueryOperator.EQUALS, true);
    }

    private ValueFilter createKeywordFilter(String keyword) {
        return new ValueFilter("title", QueryOperator.LIKE, keyword);
    }

    private CompositeFilter createPublishedAndKeywordFilter(String keyword) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(createKeywordFilter(keyword));
        filter.add(createPublishedFilter());
        return filter;
    }

    @Override
    public List<Book> getPublished(Long start, Long count) {
        ValueFilter filter = createPublishedFilter();
        return bookDao.findByFilter(filter, start, count);
    }

    @Override
    public List<Book> getPublishedByKeyword(String keyword, Long start, Long count) {
        CompositeFilter filter = createPublishedAndKeywordFilter(keyword);
        return bookDao.findByFilter(filter, start, count);
    }

    @Override
    public List<Book> getByKeyword(String keyword, Long start, Long count) {
        ValueFilter filter = createKeywordFilter(keyword);
        return bookDao.findByFilter(filter, start, count, "id", OrderDir.ASC);
    }

    @Override
    public void removeById(Long id) {
        bookDao.removeById(id);
        blobRepository.remove(BlobDataType.BOOK, id);
    }

    @Override
    public void getAttachment(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.BOOK, id, os);
    }

    @Override
    public Book findById(Long id) {
        return bookDao.findById(id);
    }


    @Override
    public Long countAlls() {
        return bookDao.countAlls();
    }

    @Override
    public Long countByKeyword(String keyword) {
        return bookDao.countByFilter(createKeywordFilter(keyword));
    }

    @Override
    public List<Book> get(Long start, Long count) {
        return bookDao.findAlls(start, count);
    }

    @Override
    public void save(Book book) {
        bookDao.save(book);
    }

    @Override
    public void save(Book book, InputStream is) throws IOException {
        bookDao.save(book);
        blobRepository.store(BlobDataType.BOOK, book.getId(), is);
    }
}
