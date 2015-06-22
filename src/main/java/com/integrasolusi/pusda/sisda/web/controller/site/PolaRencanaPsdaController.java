package com.integrasolusi.pusda.sisda.web.controller.site;

import com.integrasolusi.pusda.sisda.persistence.patternplanning.PolaRencanaPsdaFile;
import com.integrasolusi.pusda.sisda.persistence.patternplanning.PolaRencanaPsdaFolder;
import com.integrasolusi.pusda.sisda.service.patternplanning.PolaRencanaPsdaFileService;
import com.integrasolusi.pusda.sisda.service.patternplanning.PolaRencanaPsdaFolderService;
import com.integrasolusi.pusda.sisda.web.dto.patternplansda.PatternPlanSdaFileDto;
import com.integrasolusi.pusda.sisda.web.dto.patternplansda.PatternPlanSdaFolderDto;
import com.integrasolusi.utils.ContentTypeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Programmer   : pancara
 * Date         : 7/12/11
 * Time         : 11:48 PM
 */
@Controller("polaRencanaPsdaFileController")
public class PolaRencanaPsdaController {

    private static Logger logger = Logger.getLogger(PolaRencanaPsdaController.class);

    @Autowired
    private PolaRencanaPsdaFileService polaRencanaPsdaFileService;

    @Autowired
    private PolaRencanaPsdaFolderService polaRencanaPsdaFolderService;

    @Autowired
    private ContentTypeUtils contentTypeUtils;


    @RequestMapping("/pola_rencana_sda.html")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("site/pola_rencana_sda/list");

        List<PatternPlanSdaFolderDto> folderDtos = new LinkedList<>();
        List<PolaRencanaPsdaFolder> folders = polaRencanaPsdaFolderService.findByParent(null);
        for (PolaRencanaPsdaFolder folder : folders) {
            PatternPlanSdaFolderDto dto = new PatternPlanSdaFolderDto();

            dto.setId(folder.getId());
            dto.setName(folder.getName());
            dto.setIndex(folder.getIndex());

            folderDtos.add(dto);
            populateChildFolder(dto, folder);

            populateItems(dto, folder);
        }

        mav.addObject("folders", folderDtos);

        return mav;
    }

    private void populateChildFolder(PatternPlanSdaFolderDto parentDto, PolaRencanaPsdaFolder folder) {
        List<PolaRencanaPsdaFolder> children = polaRencanaPsdaFolderService.findByParent(folder);
        for (PolaRencanaPsdaFolder child : children) {
            PatternPlanSdaFolderDto dto = new PatternPlanSdaFolderDto();

            dto.setId(folder.getId());
            dto.setName(folder.getName());
            dto.setIndex(folder.getIndex());

            parentDto.getChildren().add(dto);

            populateChildFolder(dto, child);
            populateItems(dto, child);
        }
    }

    private void populateItems(PatternPlanSdaFolderDto parentDto, PolaRencanaPsdaFolder folder) {
        List<PolaRencanaPsdaFile> files = polaRencanaPsdaFileService.findByFolder(folder);

        for (PolaRencanaPsdaFile file : files) {
            PatternPlanSdaFileDto fileDto = new PatternPlanSdaFileDto();
            fileDto.setId(file.getId());
            fileDto.setFilename(file.getFilename());
            fileDto.setTitle(file.getTitle());
            fileDto.setDescription(file.getDescription());
            fileDto.setFilename(file.getFilename());

            parentDto.getFiles().add(fileDto);
        }
    }

    @RequestMapping("/pola_rencana_sda/file/{id}/**")
    public void download(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
        PolaRencanaPsdaFile file = polaRencanaPsdaFileService.findById(id);
        if (file != null) {
            String contentType = contentTypeUtils.getContentType(file.getFilename());
            response.setContentType(contentType);

            polaRencanaPsdaFileService.copyContent(id, response.getOutputStream());
        }
    }


}
