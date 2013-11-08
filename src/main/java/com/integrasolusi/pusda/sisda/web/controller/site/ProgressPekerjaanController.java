package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.ProgressPekerjaan;
import com.integrasolusi.pusda.sisda.persistence.SatuanKerja;
import com.integrasolusi.pusda.sisda.persistence.SystemConfig;
import com.integrasolusi.pusda.sisda.persistence.Year;
import com.integrasolusi.pusda.sisda.service.ProgressPekerjaanService;
import com.integrasolusi.pusda.sisda.service.SatuanKerjaService;
import com.integrasolusi.pusda.sisda.service.SystemConfigService;
import com.integrasolusi.pusda.sisda.service.sda.YearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * User: pancara
 * Date: 10/4/12
 * Time: 4:57 PM
 */
@Controller
public class ProgressPekerjaanController {
    private static final Logger logger = LoggerFactory.getLogger(ProgressPekerjaanController.class);

    @Autowired
    private SatuanKerjaService satuanKerjaService;

    @Autowired
    private YearService yearService;

    @Autowired
    private ProgressPekerjaanService progressPekerjaanService;

    @Autowired
    private SystemConfigService systemConfigService;

    @RequestMapping("/progress_pekerjaan.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("site/progress_pekerjaan/intro");

        SystemConfig config = systemConfigService.findById(SystemConfig.YEAR_PROGRESS_PEKERJAAN);
        Year year = yearService.findByValue(config.getIntValue());
        mav.addObject("year", year);

        Map progressPekerjaanData = new LinkedHashMap();
        for (SatuanKerja satuanKerja : satuanKerjaService.findAlls()) {
            List<ProgressPekerjaan> progressPekerjaanList = progressPekerjaanService.findByYearAndSatuanKerja(year, satuanKerja);
            progressPekerjaanData.put(satuanKerja, progressPekerjaanList);
        }


        mav.addObject("progressPekerjaanData", progressPekerjaanData);

        return mav;
    }

    @RequestMapping("/progress_pekerjaan/{id}/**")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) {
        try {
            progressPekerjaanService.getDocument(id, response.getOutputStream());
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
        }
    }
}
