package com.integrasolusi.pusda.sisda.dao.sda;

import com.integrasolusi.query.generic.GenericDao;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.sda.Kekeringan;

import java.util.List;

/**
 * User: pancara
 * Date: 9/5/12
 * Time: 12:00 PM
 */
public interface KekeringanDao extends GenericDao<Kekeringan, Long> {
    List<Year> getYearByWilayahSungai(WilayahSungai ws);
}
