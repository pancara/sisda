package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.Kekeringan;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * User: pancara
 * Date: 9/5/12
 * Time: 11:59 AM
 */
public interface KekeringanService {
    List<Year> findYearByWilayahSungai(WilayahSungai ws);

    List<Kekeringan> findByWilayahSungaiAndYear(WilayahSungai ws, Year year);

    Kekeringan findById(Long id);

    void getBlob(Long id, OutputStream outputStream) throws IOException;

    void save(Kekeringan kekeringan, InputStream is) throws IOException;
    
    void save(Kekeringan kekeringan);

    List<Kekeringan> findAlls(Long start, Long count);

    List<Kekeringan> findByKeyword(String keywordString, Long start, Long count);

    List<Kekeringan> findByYear(Long year, Long start, Long count);

    List<Kekeringan> findByYearAndKeyword(Long year, String keyword, Long start, Long count);

    Long countAlls();

    Long countByKeyword(String keyword);

    Long countByYear(Long year);

    Long countByYearAndKeyword(Long year, String keyword);

    void removeByIds(Long[] ids);
}
