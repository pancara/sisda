package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.persistence.ProgressPekerjaan;
import com.integrasolusi.pusda.sisda.persistence.SatuanKerja;
import com.integrasolusi.pusda.sisda.persistence.Year;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 10/16/12
 * Time       : 1:36 PM
 */
public interface ProgressPekerjaanService {

    ProgressPekerjaan findById(Long id);

    List<ProgressPekerjaan> findByYearAndSatuanKerja(Year year, SatuanKerja satuanKerja);

    Long countAlls();

    Long countByYear(Long year);

    List<ProgressPekerjaan> findAlls(Long start, Long count);

    List<ProgressPekerjaan> findByYear(Long year, Long start, Long count);

    void save(ProgressPekerjaan progressPekerjaan);

    void save(ProgressPekerjaan pp, InputStream inputStream) throws IOException;

    void removeByIds(Long[] ids);

    void getDocument(Long id, OutputStream os) throws IOException;
}
