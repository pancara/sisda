package com.integrasolusi.pusda.sisda.service.presetantion;

import com.integrasolusi.pusda.sisda.dao.presentation.ModuleDao;
import com.integrasolusi.pusda.sisda.persistence.presentation.Meeting;
import com.integrasolusi.pusda.sisda.persistence.presentation.Module;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 7/23/13
 * Time       : 11:17 AM
 */
public class ModuleServiceImpl implements ModuleService {
    private ModuleDao moduleDao;
    private BlobRepository blobRepository;

    public void setModuleDao(ModuleDao moduleDao) {
        this.moduleDao = moduleDao;
    }

    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public List<Module> getLatest(Long presentationModuleCount) {
        return moduleDao.findAlls(0L, presentationModuleCount, "id", OrderDir.DESC);
    }

    @Override
    public void getDocument(Long id, OutputStream outputStream) throws IOException {
        blobRepository.copyContent(BlobDataType.PRESENTATION_MODULE, id, outputStream);
    }

    @Override
    public Module findById(Long id) {
        return moduleDao.findById(id);
    }

    @Override
    public Long countAlls() {
        return moduleDao.countAlls();
    }

    @Override
    public List<Module> findAlls(Long start, Long count) {
        return moduleDao.findAlls(start, count, "id", OrderDir.DESC);
    }

    @Override
    public Long countByKeyword(String keyword) {
        return moduleDao.countByFilter(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"));
    }

    @Override
    public List<Module> findByKeyword(String keyword, Long start, Long count) {
        return moduleDao.findByFilter(new ValueFilter("name", QueryOperator.LIKE, keyword, "name"), start, count, "id", OrderDir.DESC);
    }

    @Override
    public void save(Module module) {
        moduleDao.save(module);
    }

    @Override
    public void save(Module module, InputStream inputStream) throws IOException {
        moduleDao.save(module);
        blobRepository.store(BlobDataType.PRESENTATION_MODULE, module.getId(), inputStream);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            moduleDao.removeById(id);
        }
        for (Long id : ids) {
            blobRepository.remove(BlobDataType.PRESENTATION_MODULE, id);
        }

    }

    @Override
    public List<Module> findByMeeting(Meeting meeting) {
        return moduleDao.findByFilter(new ValueFilter("meeting", QueryOperator.EQUALS, meeting, "meeting"), "name", OrderDir.ASC);
    }

    @Override
    public List<Module> findByMeeting(Meeting meeting, Long start, Long count) {
        return moduleDao.findByFilter(new ValueFilter("meeting", QueryOperator.EQUALS, meeting, "meeting"), start, count, "name", OrderDir.ASC);
    }

    @Override
    public Long countByMeeting(Meeting meeting) {
        return moduleDao.countByFilter(new ValueFilter("meeting", QueryOperator.EQUALS, meeting, "meeting"));
    }
}
