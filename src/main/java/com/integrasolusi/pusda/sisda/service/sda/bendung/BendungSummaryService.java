package com.integrasolusi.pusda.sisda.service.sda.bendung;

import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.bendung.BendungSummary;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: pancara
 * Date: 7/31/12
 * Time: 12:37 PM
 */
public interface BendungSummaryService {

    BendungSummary findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    void save(BendungSummary bendungSummary);

    void save(BendungSummary bendungSummary, InputStream inputStream) throws IOException;

    void removeByIds(Long[] ids);

    BendungSummary findByWilayahSungai(WilayahSungai ws);
}
