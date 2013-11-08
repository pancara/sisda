package com.integrasolusi.pusda.sisda.dao.sda;

import com.integrasolusi.query.generic.GenericDao;
import com.integrasolusi.pusda.sisda.persistence.ProgressPekerjaan;
import com.integrasolusi.pusda.sisda.persistence.SatuanKerja;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.Kabupaten;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 10/16/12
 * Time       : 1:35 PM
 */
public interface ProgressPekerjaanDao extends GenericDao<ProgressPekerjaan, Long> {
    List<Kabupaten> findKabupatenByYearAndSatuanKerja(Year year, SatuanKerja satuanKerja);
}
