package com.integrasolusi.pusda.sisda.dao.sda.hidrologi;

import com.integrasolusi.query.generic.GenericDao;
import com.integrasolusi.pusda.sisda.persistence.sda.hidrologi.PosHidrologi;

/**
 * User: pancara
 * Date: 8/4/12
 * Time: 7:16 PM
 */
public interface PosHidrologiDao extends GenericDao<PosHidrologi, Long> {
    void deactivateOthers(PosHidrologi posHidrologi);

}
