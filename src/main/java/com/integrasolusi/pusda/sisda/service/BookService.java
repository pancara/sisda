package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Book;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/12/11
 * Time         : 9:07 AM
 */
public interface BookService {

    Long countAlls();

    Long countByKeyword(String keyword);

    Long countPublished();

    Long countPublishedByKeyword(String keyword);

    List<Book> get(Long start, Long count);

    List<Book> getPublished(Long start, Long count);

    List<Book> getPublishedByKeyword(String keyword, Long start, Long count);

    List<Book> getByKeyword(String keyword, Long start, Long count);

    void removeById(Long id);

    void getAttachment(Long id, OutputStream os) throws IOException;

    Book findById(Long id);

    void save(Book book);

    void save(Book book, InputStream is) throws IOException;
}
