package com.integrasolusi.pusda.sisda.service.sda.sungai;

import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.sungai.SungaiSummary;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 12:37 PM
 */
public interface SungaiSummaryService {

    SungaiSummary findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    void save(SungaiSummary sungaiSummary);

    void save(SungaiSummary sungaiSummary, InputStream inputStream) throws IOException;

    void removeByIds(Long[] ids);

    SungaiSummary findByWilayahSungai(WilayahSungai ws);
}
