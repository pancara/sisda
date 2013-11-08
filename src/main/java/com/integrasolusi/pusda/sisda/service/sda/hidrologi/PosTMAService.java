package com.integrasolusi.pusda.sisda.service.sda.hidrologi;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosTMA;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 8:49 PM
 */
public interface PosTMAService {

    PosTMA findById(Long id);

    void getBlob(Long id, OutputStream os) throws IOException;

    void removeByIds(Long[] ids);

    void save(PosTMA posTMA);

    void save(PosTMA posTMA, InputStream inputStream) throws IOException;

    List<PosTMA> findByDas(Das das);

}
