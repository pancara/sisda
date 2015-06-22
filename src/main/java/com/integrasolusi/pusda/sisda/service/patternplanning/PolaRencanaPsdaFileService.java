package com.integrasolusi.pusda.sisda.service.patternplanning;

import com.integrasolusi.pusda.sisda.persistence.patternplanning.PolaRencanaPsdaFile;
import com.integrasolusi.pusda.sisda.persistence.patternplanning.PolaRencanaPsdaFolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/19/11
 * Time         : 6:26 PM
 */
public interface PolaRencanaPsdaFileService {

    Long countByKeyword(String keyword);

    void copyContent(Long id, OutputStream os) throws IOException;

    Long countAlls();

    List<PolaRencanaPsdaFile> findAlls(Long start, Long count);

    List<PolaRencanaPsdaFile> findByFolder(PolaRencanaPsdaFolder folder);

    List<PolaRencanaPsdaFile> findByKeyword(String keyword, Long start, Long count);

    PolaRencanaPsdaFile findById(Long id);

    void removeById(Long id);

    void save(PolaRencanaPsdaFile polaRencanaPsdaFile, InputStream is) throws IOException;

    void save(PolaRencanaPsdaFile polaRencanaPsdaFile);

    void removeByIds(Long[] ids);
}
