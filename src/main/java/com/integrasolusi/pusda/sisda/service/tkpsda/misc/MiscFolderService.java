package com.integrasolusi.pusda.sisda.service.tkpsda.misc;

import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.persistence.region.WilayahSungai;
import com.integrasolusi.pusda.sisda.persistence.tkpsda.misc.MiscFolder;

import java.util.List;

/**
 * Programmer : pancara
 * Date       : 7/28/13
 * Time       : 6:29 PM
 */
public interface MiscFolderService {

    List<MiscFolder> findByWilayahSungaiAndYear(WilayahSungai ws, Year year);

    MiscFolder findById(Long id);

    void save(MiscFolder folder);

    void removeByIds(Long[] ids);
}
