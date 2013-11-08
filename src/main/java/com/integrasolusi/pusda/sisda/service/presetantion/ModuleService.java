package com.integrasolusi.pusda.sisda.service.presetantion;

import com.integrasolusi.pusda.sisda.persistence.presentation.Meeting;
import com.integrasolusi.pusda.sisda.persistence.presentation.Module;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 7/23/13
 * Time       : 11:17 AM
 */
public interface ModuleService {

    List<Module> getLatest(Long presentationModuleCount);

    void getDocument(Long id, OutputStream outputStream) throws IOException;

    Module findById(Long id);

    Long countAlls();

    List<Module> findAlls(Long start, Long count);

    Long countByKeyword(String keyword);

    List<Module> findByKeyword(String keywordString, Long start, Long count);

    void save(Module module);

    void save(Module module, InputStream inputStream) throws IOException;

    void removeByIds(Long[] ids);

    List<Module> findByMeeting(Meeting meeting);

    List<Module> findByMeeting(Meeting meeting, Long start, Long count);

    Long countByMeeting(Meeting meeting);
}
