package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.ProjectDao;
import com.integrasolusi.pusda.sisda.persistence.Project;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.query.util.OrderDir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 7/30/12
 * Time: 6:38 PM
 */
public class ProjectServiceImpl implements ProjectService {
    private ProjectDao projectDao;
    private BlobRepository blobRepository;

    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public Project findById(Long id) {
        return projectDao.findById(id);
    }

    @Override
    public List<Project> findAlls() {
        return projectDao.findAlls("index", OrderDir.ASC);
    }

    @Override
    public void getPicture(Long id, OutputStream outputStream) throws IOException {
        blobRepository.copyContent(BlobDataType.PROJECT, id, outputStream);
    }

    @Override
    public void removeById(Long id) {
        projectDao.removeById(id);
        blobRepository.remove(BlobDataType.PROJECT, id);
    }

    @Override
    public void save(Project bendungan) {
        projectDao.save(bendungan);
    }

    @Override
    public void save(Project bendungan, InputStream is) throws IOException {
        projectDao.save(bendungan);
        blobRepository.store(BlobDataType.PROJECT, bendungan.getId(), is);
    }

    @Override
    public Long getNextIndex() {
        Long last = projectDao.getLastIndex();
        return last == null ? 1L : last++;
    }


}
