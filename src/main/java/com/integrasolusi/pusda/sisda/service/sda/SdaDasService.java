package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.region.Das;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.SdaDas;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 8/5/12
 * Time: 11:17 AM
 */
public interface SdaDasService {
    List<SdaDas> findByWilayahSungai(WilayahSungai ws);

    SdaDas findById(Long id);

    void getBlob(Long id, OutputStream outputStream) throws IOException;
}
