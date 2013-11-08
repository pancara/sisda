package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.region.SungaiSaboDam;
import com.integrasolusi.pusda.sisda.persistence.sda.SaboDam;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/1/12
 * Time: 11:55 AM
 */
public interface SaboDamService {
    List<SaboDam> findAlls();

    SaboDam findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    List<SaboDam> findBySungai(SungaiSaboDam sungai);

    Long countAlls();

    Long countByKeyword(String keywordString);

    List<SaboDam> findByKeyword(String keywordString, Long start, Long count);

    List<SaboDam> findAlls(Long start, Long count);

    Long countBySungai(Long sungai);

    Long countBySungaiAndKeyword(Long sungai, String keyword);

    List<SaboDam> findBySungai(Long sungai, Long start, Long count);

    List<SaboDam> findBySungaiAndKeyword(Long sungai, String keyword, Long start, Long count);

    void save(SaboDam sabodam);

    void save(SaboDam sabodam, InputStream inputStream) throws IOException;

    void removeByIds(Long[] ids);

    boolean hasDocumentBlob(Long id);
}
