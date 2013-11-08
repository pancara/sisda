package com.integrasolusi.pusda.sisda.service;

import com.integrasolusi.pusda.sisda.dao.sda.ProgressPekerjaanDao;
import com.integrasolusi.query.filter.CompositeFilter;
import com.integrasolusi.query.filter.QueryOperator;
import com.integrasolusi.query.filter.ValueFilter;
import com.integrasolusi.query.util.OrderDir;
import com.integrasolusi.pusda.sisda.persistence.ProgressPekerjaan;
import com.integrasolusi.pusda.sisda.persistence.SatuanKerja;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.repository.BlobDataType;
import com.integrasolusi.pusda.sisda.repository.BlobRepository;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Programmer : pancara
 * Date       : 10/16/12
 * Time       : 1:37 PM
 */
public class ProgressPekerjaanServiceImpl implements ProgressPekerjaanService {
    private ProgressPekerjaanDao progressPekerjaanDao;
    private BlobRepository blobRepository;

    public void setProgressPekerjaanDao(ProgressPekerjaanDao progressPekerjaanDao) {
        this.progressPekerjaanDao = progressPekerjaanDao;
    }


    public void setBlobRepository(BlobRepository blobRepository) {
        this.blobRepository = blobRepository;
    }

    @Override
    public ProgressPekerjaan findById(Long id) {
        return progressPekerjaanDao.findById(id);
    }

    @Override
    public List<ProgressPekerjaan> findByYearAndSatuanKerja(Year year, SatuanKerja satuanKerja) {
        CompositeFilter filter = new CompositeFilter(QueryOperator.AND);
        filter.add(new ValueFilter("year", QueryOperator.EQUALS, year, "year"));
        filter.add(new ValueFilter("satuanKerja", QueryOperator.EQUALS, satuanKerja, "satuanKerja"));
        return progressPekerjaanDao.findByFilter(filter, "reportingDate", OrderDir.ASC);
    }

    @Override
    public Long countAlls() {
        return progressPekerjaanDao.countAlls();
    }

    @Override
    public Long countByYear(Long year) {
        return progressPekerjaanDao.countByFilter(createYearFilter(year));
    }

    private ValueFilter createYearFilter(Long year) {
        return new ValueFilter("year.id", QueryOperator.EQUALS, year, "year");
    }

    @Override
    public List<ProgressPekerjaan> findAlls(Long start, Long count) {
        return progressPekerjaanDao.findAlls(start, count, "reportingDate", OrderDir.ASC);
    }

    @Override
    public List<ProgressPekerjaan> findByYear(Long year, Long start, Long count) {
        return progressPekerjaanDao.findByFilter(createYearFilter(year), start, count, "reportingDate", OrderDir.ASC);
    }

    @Override
    public void save(ProgressPekerjaan progressPekerjaan) {
        progressPekerjaanDao.save(progressPekerjaan);
    }

    @Override
    public void save(ProgressPekerjaan pp, InputStream inputStream) throws IOException {
        progressPekerjaanDao.save(pp);
        blobRepository.store(BlobDataType.PROGRESS_PEKERJAAN, pp.getId(), inputStream);
    }

    @Override
    public void removeByIds(Long[] ids) {
        for (Long id : ids) {
            progressPekerjaanDao.removeById(id);
        }
    }

    @Override
    public void getDocument(Long id, OutputStream os) throws IOException {
        blobRepository.copyContent(BlobDataType.PROGRESS_PEKERJAAN, id, os);
    }
}
