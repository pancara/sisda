package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.Project;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 6:38 PM
 */
public interface ProjectService {
    Project findById(Long id);

    List<Project> findAlls();

    void getPicture(Long id, OutputStream outputStream) throws IOException;

    void removeById(Long id);

    void save(Project project);

    void save(Project project, InputStream is) throws IOException;

    Long getNextIndex();
}
