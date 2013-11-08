package com.integrasolusi.pusda.sisda.service.sda;

import com.integrasolusi.pusda.sisda.persistence.Year;

import java.util.List;

/**
 * User: pancara
 * Date: 8/3/12
 * Time: 8:50 PM
 */
public interface YearService {
    Year findByValue(Integer yearValue);

    List<Year> findAlls();

    Year findById(Long id);
}
